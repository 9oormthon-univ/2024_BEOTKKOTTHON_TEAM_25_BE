package com.goormthonuniv.ownearth.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.repository.MemberMissionRepository;
import com.goormthonuniv.ownearth.repository.MissionRepository;
import com.goormthonuniv.ownearth.service.MissionCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionCommandServiceImpl implements MissionCommandService {

  private final MemberMissionRepository memberMissionRepository;
  private final MissionRepository missionRepository;

  @Override
  public MemberMission getOrAssignMission(Member member) {

    LocalDate today = LocalDate.now();
    LocalDateTime startOfDay = today.atStartOfDay();
    LocalDateTime endOfDay = today.atTime(23, 59, 59);

    return memberMissionRepository
        .findMemberMissionByMemberAndCreatedAtBetween(member, startOfDay, endOfDay)
        .orElseGet(
            () -> {
              MemberMission newMission =
                  MemberMission.builder()
                      .member(member)
                      .mission(missionRepository.findRandomMission())
                      .build();
              return memberMissionRepository.save(newMission);
            });
  }
}
