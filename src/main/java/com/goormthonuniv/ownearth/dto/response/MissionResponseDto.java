package com.goormthonuniv.ownearth.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
