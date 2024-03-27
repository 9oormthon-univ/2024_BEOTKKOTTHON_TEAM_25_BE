package com.goormthonuniv.ownearth.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.converter.MemberConverter;
import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.FriendAcceptRequest;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.LoginMemberRequest;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.ReissueRequest;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.SetEarthNameRequest;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.EarthNameResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.TokenResponse;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.ItemException;
import com.goormthonuniv.ownearth.exception.MemberException;
import com.goormthonuniv.ownearth.repository.FriendRepository;
import com.goormthonuniv.ownearth.repository.ItemRepository;
import com.goormthonuniv.ownearth.repository.MemberItemRepository;
import com.goormthonuniv.ownearth.repository.MemberRepository;
import com.goormthonuniv.ownearth.security.provider.JwtAuthProvider;
import com.goormthonuniv.ownearth.service.MemberCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

  private final MemberRepository memberRepository;
  private final JwtAuthProvider jwtAuthProvider;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final FriendRepository friendRepository;
  private final ItemRepository itemRepository;
  private final MemberItemRepository memberItemRepository;
  private final RedisTemplate<String, String> redisTemplate;

  @Value("${jwt.refresh-token-validity}")
  private Long refreshTokenValidityMilliseconds;

  @Override
  public Member signUpMember(SignUpMemberRequest request) {

    memberRepository
        .findByEmail(request.getEmail())
        .ifPresent(
            member -> {
              throw new MemberException(GlobalErrorCode.DUPLICATE_EMAIL);
            });

    return memberRepository.save(MemberConverter.toMember(request));
  }

  @Override
  public EarthNameResponse setEarthName(Member member, SetEarthNameRequest request) {

    if (member.getEarthName() == null) {
      member.setEarthName(request.getEarthName());
      return MemberConverter.toEarthNameResponse(request.getEarthName());
    }
    throw new MemberException(GlobalErrorCode.ALREADY_SET_EARTHNAME);
  }

  @Override
  public TokenResponse login(LoginMemberRequest request) {
    Member member =
        memberRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));

    if (!(member.getPassword().isSamePassword(request.getPassword(), bCryptPasswordEncoder))) {
      throw new MemberException(GlobalErrorCode.PASSWORD_MISMATCH);
    }

    String accessToken = jwtAuthProvider.generateAccessToken(member.getId());
    String refreshToken = jwtAuthProvider.generateRefreshToken(member.getId());

    member.updateRefreshToken(refreshToken);

    return MemberConverter.toLoginMemberResponse(member.getId(), accessToken, refreshToken);
  }

  @Override
  public Friend requestFriend(Member member, Long targetMemberId) {
    Member targetMember =
        memberRepository
            .findById(targetMemberId)
            .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));
    Friend friend = friendRepository.findByFromMemberAndToMember(member, targetMember).orElse(null);

    if (friend != null) {
      throw new MemberException(GlobalErrorCode.ALREADY_FRIEND);
    }

    friend = MemberConverter.toFriend(member, targetMember, false);
    return friendRepository.save(friend);
  }

  @Override
  public Friend acceptFriendRequest(Member member, FriendAcceptRequest request) {
    Friend friendRequest =
        friendRepository
            .findById(request.getRequestId())
            .orElseThrow(() -> new MemberException(GlobalErrorCode.REQUEST_NOT_FOUND));

    if (friendRequest.getFromMember() == member) {
      throw new MemberException(GlobalErrorCode.REQUEST_NOT_FOUND);
    }

    if (friendRequest.getIsFriend()) {
      throw new MemberException(GlobalErrorCode.ALREADY_FRIEND);
    }

    Friend friend = MemberConverter.toFriend(member, friendRequest.getFromMember(), true);
    friendRequest.setIsFriend(true);

    return friendRepository.save(friend);
  }

  @Override
  public void refuseFriendRequest(Member member, Long requestId) {
    Friend request =
        friendRepository
            .findById(requestId)
            .orElseThrow(() -> new MemberException(GlobalErrorCode.REQUEST_NOT_FOUND));

    if (request.getFromMember() == member || request.getIsFriend()) {
      throw new MemberException(GlobalErrorCode.REQUEST_NOT_FOUND);
    }

    friendRepository.delete(request);
  }

  @Override
  public void deleteFriend(Member member, Long targetMemberId) {
    Member targetMember =
        memberRepository
            .findById(targetMemberId)
            .orElseThrow(() -> new MemberException((GlobalErrorCode.MEMBER_NOT_FOUND)));
    Friend toFriend =
        friendRepository
            .findByFromMemberAndToMemberAndIsFriendTrue(member, targetMember)
            .orElseThrow(() -> new MemberException(GlobalErrorCode.NOT_FRIEND));
    Friend fromFriend =
        friendRepository
            .findByFromMemberAndToMemberAndIsFriendTrue(targetMember, member)
            .orElseThrow(() -> new MemberException(GlobalErrorCode.NOT_FRIEND));

    friendRepository.delete(toFriend);
    friendRepository.delete(fromFriend);
  }

  @Override
  public MemberItem toggleItemUsing(Member member, Long itemId) {
    Item item =
        itemRepository
            .findById(itemId)
            .orElseThrow(() -> new ItemException(GlobalErrorCode.ITEM_NOT_FOUND));
    MemberItem memberItem =
        memberItemRepository
            .findByMemberAndItem(member, item)
            .orElseThrow(() -> new MemberException(GlobalErrorCode.ITEM_NOT_PURCHASED));

    memberItem.toggleIsUsing();

    return memberItem;
  }

  @Override
  public TokenResponse reissue(Member member, ReissueRequest request) {
    String refreshToken = request.getRefreshToken();

    if (redisTemplate.opsForValue().get(member.getId().toString()).equals(refreshToken)) {
      Long memberId = jwtAuthProvider.parseRefreshToken(refreshToken);

      String newAccessToken = jwtAuthProvider.generateAccessToken(member.getId());
      String newRefreshToken = jwtAuthProvider.generateRefreshToken(member.getId());

      redisTemplate
          .opsForValue()
          .set(
              member.getId().toString(),
              newAccessToken,
              refreshTokenValidityMilliseconds,
              TimeUnit.MILLISECONDS);

      return MemberConverter.toReissueResponse(memberId, newAccessToken, newRefreshToken);
    } else throw new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND);
  }
}
