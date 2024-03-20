package com.goormthonuniv.ownearth.service;

import org.springframework.web.multipart.MultipartFile;

import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;

public interface MissionCommandService {

  MemberMission getOrAssignMission(Member member);

  MemberMission accomplishMission(Member member, Long missionId, MultipartFile missionImage);

  MemberMission changeMission(Member member);
}
