package com.goormthonuniv.ownearth.dto.response;

import lombok.*;

public class MissionResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class GetOrAssignMemberMissionResponse {
    String content;
  }
}
