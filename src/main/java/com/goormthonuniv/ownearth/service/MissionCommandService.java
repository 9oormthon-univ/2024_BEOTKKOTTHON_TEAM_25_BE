package com.goormthonuniv.ownearth.service;

import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;

public interface MissionCommandService {
  MemberMission getOrAssignMission(Member member);
}
