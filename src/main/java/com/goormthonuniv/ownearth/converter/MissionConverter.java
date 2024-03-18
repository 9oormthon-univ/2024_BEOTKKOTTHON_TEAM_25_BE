package com.goormthonuniv.ownearth.converter;

import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.dto.response.MissionResponseDto.*;

@Component
public class MissionConverter {
  public static GetOrAssignMemberMissionResponse toGetOrAssignMemberMission(
      MemberMission memberMission) {
    return GetOrAssignMemberMissionResponse.builder()
        .content(memberMission.getMission().getContent())
        .build();
  }
}
