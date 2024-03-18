package com.goormthonuniv.ownearth.converter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.domain.member.Password;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.*;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.*;

@Component
public class MemberConverter {

  public static SignUpMemberResponse toSignUpMember(Member member) {
    return SignUpMemberResponse.builder()
        .email(member.getEmail())
        .password(member.getPassword())
        .name(member.getName())
        .earthName(member.getEarthName())
        .build();
  }

  public static Member toMember(SignUpMemberRequest request) {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    return Member.builder()
        .email(request.getEmail())
        .password(Password.encrypt(request.getPassword(), encoder))
        .name(request.getName())
        .earthName(request.getEarthName())
        .build();
  }

  public static LoginMemberResponse toLoginMemberResponse(
      Long memberId, String accessToken, String refreshToken) {
    return LoginMemberResponse.builder()
        .memberId(memberId)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }
}
