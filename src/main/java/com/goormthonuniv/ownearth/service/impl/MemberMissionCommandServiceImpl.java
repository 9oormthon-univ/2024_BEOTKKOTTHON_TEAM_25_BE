package com.goormthonuniv.ownearth.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.domain.Mission;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.GlobalException;
import com.goormthonuniv.ownearth.repository.MemberMissionRepository;
import com.goormthonuniv.ownearth.repository.MemberRepository;
import com.goormthonuniv.ownearth.repository.MissionRepository;
import com.goormthonuniv.ownearth.security.utils.MemberUtils;
import com.goormthonuniv.ownearth.service.MemberMissionCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberMissionCommandServiceImpl implements MemberMissionCommandService {

  private final MemberUtils memberUtils;
  private final MemberRepository memberRepository;
  private final MemberMissionRepository memberMissionRepository;
  private final MissionRepository missionRepository;

  @Override
  public Mission selectRandomMission(List<Mission> missions) {
    Random random = new Random();
    return missions.get(random.nextInt(missions.size()));
  }

  @Override
  public Mission getOrAssignMission() {

    Long memberId = memberUtils.getCurrentMemberId();

    List<Mission> allMission = missionRepository.findAll();

    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

    LocalDate today = LocalDate.now();
    LocalDateTime startOfDay = today.atStartOfDay();
    LocalDateTime endOfDay = today.atTime(23, 59, 59);

    return memberMissionRepository
        .findMemberMissionByMemberAndCreatedAtBetween(member, startOfDay, endOfDay)
        .map(MemberMission::getMission)
        .orElseGet(
            () -> {
              MemberMission newMission =
                  MemberMission.builder()
                      .member(member)
                      .mission(selectRandomMission(allMission))
                      .build();
              return memberMissionRepository.save(newMission).getMission();
            });
  }
}
