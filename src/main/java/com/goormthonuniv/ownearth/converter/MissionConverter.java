package com.goormthonuniv.ownearth.converter;

import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.Mission;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.MissionResponseDto.MissionResponse;
import com.goormthonuniv.ownearth.dto.response.MissionResponseDto.MissionResultDto;

@Component
public class MissionConverter {

  public static MissionResultDto toMissionResultDto(MemberMission memberMission) {
    return MissionResultDto.builder().isCompleted(memberMission.getIsCompleted()).build();
  }

  public static MissionResponse toMissionResponse(MemberMission memberMission) {
    return MissionResponse.builder()
        .missionId(memberMission.getId())
        .content(memberMission.getMission().getContent())
        .missionCategory(memberMission.getMission().getMissionCategory())
        .isCompleted(memberMission.getIsCompleted())
        .build();
  }

  public static MemberMission toMemberMission(Member member, Mission mission) {
    return MemberMission.builder().mission(mission).member(member).build();
  }
}
