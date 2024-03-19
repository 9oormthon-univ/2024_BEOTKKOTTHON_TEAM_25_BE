package com.goormthonuniv.ownearth.dto.response;

import com.goormthonuniv.ownearth.domain.enums.MissionCategory;

import lombok.*;

public class MissionResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class GetOrAssignMemberMissionResponse {
    String content;
  }

  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @Getter
  @Builder
  public static class MissionResultDto {
    private Boolean isCompleted;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class MissionResponse {
    String content;
    MissionCategory missionCategory;
  }
}
