package com.goormthonuniv.ownearth.service;

import java.util.List;

import com.goormthonuniv.ownearth.domain.Mission;

public interface MemberMissionCommandService {
  Mission selectRandomMission(List<Mission> missions);

  Mission getOrAssignMission();
}
