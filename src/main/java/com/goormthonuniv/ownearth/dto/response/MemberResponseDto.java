package com.goormthonuniv.ownearth.dto.response;

import lombok.*;

public class MemberResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class SignUpMemberResponse {
    Long memberId;
    String email;
    String name;
    String earthName;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class LoginMemberResponse {
    Long memberId;
    String accessToken;
    String refreshToken;
  }
}
