package com.goormthonuniv.ownearth.converter;

import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.dto.response.MissionResponseDto.MissionResponse;
import com.goormthonuniv.ownearth.dto.response.MissionResponseDto.MissionResultDto;

@Component
public class MissionConverter {

  public static MissionResultDto toMissionResultDto(MemberMission memberMission) {
    return MissionResultDto.builder().isCompleted(memberMission.getIsCompleted()).build();
  }

  public static MissionResponse toMissionResponse(MemberMission memberMission) {
    return MissionResponse.builder()
        .content(memberMission.getMission().getContent())
        .missionCategory(memberMission.getMission().getMissionCategory())
        .build();
  }
}
