package com.goormthonuniv.ownearth.converter;

import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.member.Member;
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
}
