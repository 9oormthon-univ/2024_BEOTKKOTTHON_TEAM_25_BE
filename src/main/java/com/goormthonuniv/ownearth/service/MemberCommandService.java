package com.goormthonuniv.ownearth.service;

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

public interface MemberCommandService {

  Member signUpMember(SignUpMemberRequest request);

  EarthNameResponse setEarthName(Member member, SetEarthNameRequest request);

  TokenResponse login(LoginMemberRequest request);

  Friend requestFriend(Member member, Long targetMemberId);

  Friend acceptFriendRequest(Member member, FriendAcceptRequest request);

  void refuseFriendRequest(Member member, Long requestId);

  TokenResponse reissue(Member member, ReissueRequest request);

  void deleteFriend(Member member, Long targetMemberId);

  MemberItem toggleItemUsing(Member member, Long itemId);
}
