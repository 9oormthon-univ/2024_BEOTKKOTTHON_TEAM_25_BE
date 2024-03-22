package com.goormthonuniv.ownearth.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthonuniv.ownearth.domain.enums.MissionCategory;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

  Optional<MemberMission> findMemberMissionByMemberAndCreatedAtBetween(
      Member member, LocalDateTime start, LocalDateTime end);

  Integer countByMemberAndCompletedAtBetween(Member member, LocalDateTime start, LocalDateTime end);

  List<MemberMission> findAllByMemberAndCompletedAtBetween(
      Member member, LocalDateTime start, LocalDateTime end);

  List<MemberMission> findAllByMemberAndMission_MissionCategoryAndIsCompletedTrue(
      Member member, MissionCategory missionCategory);

  List<MemberMission> findByMemberIdAndCompletedAtBetweenAndIsCompletedTrue(
      Long memberId, LocalDateTime startDate, LocalDateTime endDate);
}
