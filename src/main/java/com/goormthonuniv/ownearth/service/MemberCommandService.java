package com.goormthonuniv.ownearth.service;

import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.LoginMemberRequest;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.LoginMemberResponse;

public interface MemberCommandService {

  Member signUpMember(SignUpMemberRequest request);

  LoginMemberResponse login(LoginMemberRequest request);

  Friend requestFriend(Member member, Long targetMemberId);

  Friend acceptFriendRequest(Member member, Long requestId);

  void refuseFriendRequest(Member member, Long requestId);
}
