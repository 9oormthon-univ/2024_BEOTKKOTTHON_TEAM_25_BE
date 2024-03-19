package com.goormthonuniv.ownearth.converter;

import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.Mission;
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

  public static MissionResultDto toMissionResultDto(MemberMission memberMission) {
    return MissionResultDto.builder().isCompleted(memberMission.getIsCompleted()).build();
  }

  public static MissionResponse toMissionResponse(Mission mission) {
    return MissionResponse.builder()
        .content(mission.getContent())
        .missionCategory(mission.getMissionCategory())
        .build();
  }
}
