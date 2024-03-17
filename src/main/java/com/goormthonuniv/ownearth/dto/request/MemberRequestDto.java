package com.goormthonuniv.ownearth.dto.request;

import lombok.Getter;

public class MemberRequestDto {

  @Getter
  public static class SignUpMemberRequest {
    String email;
    String password;
    String name;
    String earthName;
  }
}
