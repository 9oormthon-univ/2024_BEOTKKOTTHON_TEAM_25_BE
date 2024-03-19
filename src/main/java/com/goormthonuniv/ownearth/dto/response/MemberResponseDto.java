package com.goormthonuniv.ownearth.dto.response;

import java.util.List;
import java.util.Optional;

import com.goormthonuniv.ownearth.domain.mapping.MemberMission;

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

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MonthlyMissionStatusResponse {
    Long memberId;
    String name;
    Integer completedMissionCount;
    Integer accumulatedPoint;
    Integer completionRate;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class GetMyEarthResponse {
    List<Long> myUsingItems;
    String earthName;
    Long daySinceCreation;
    Optional<MemberMission> memberMission;
  }
}
