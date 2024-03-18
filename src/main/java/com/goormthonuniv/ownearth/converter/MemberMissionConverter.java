package com.goormthonuniv.ownearth.converter;

import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.Mission;
import com.goormthonuniv.ownearth.dto.response.MemberMissionResponseDto.*;

@Component
public class MemberMissionConverter {
  public static GetOrAssignMemberMissionResponse toGetOrAssignMemberMission(Mission mission) {
    return GetOrAssignMemberMissionResponse.builder().content(mission.getContent()).build();
  }
}
