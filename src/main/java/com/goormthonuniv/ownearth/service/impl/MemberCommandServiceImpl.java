package com.goormthonuniv.ownearth.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.converter.MemberConverter;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.FriendAcceptRequest;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.LoginMemberRequest;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.LoginMemberResponse;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.MemberException;
import com.goormthonuniv.ownearth.repository.FriendRepository;
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
  public LoginMemberResponse login(LoginMemberRequest request) {
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

    Friend friend = MemberConverter.toFriend(member, friendRequest.getToMember(), true);
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
}
