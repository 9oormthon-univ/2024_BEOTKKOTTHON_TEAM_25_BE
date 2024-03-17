package com.goormthonuniv.ownearth.dto.response;

import com.goormthonuniv.ownearth.domain.member.Password;

import lombok.*;

public class MemberResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class SignUpMemberResponse {
    String email;
    Password password;
    String name;
    String earthName;
  }
}
