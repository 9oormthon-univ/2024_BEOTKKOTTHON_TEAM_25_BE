package com.goormthonuniv.ownearth.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goormthonuniv.ownearth.domain.enums.MissionCategory;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

  Optional<MemberMission> findMemberMissionByMemberAndCreatedAtBetween(
      Member member, LocalDateTime start, LocalDateTime end);

  Optional<MemberMission> findByMemberAndMission_Id(Member member, Long missionId);

  Integer countByMemberAndCompletedAtBetween(Member member, LocalDateTime start, LocalDateTime end);

<<<<<<< HEAD
  List<MemberMission> findAllByMemberAndCompletedAtBetween(
      Member member, LocalDateTime start, LocalDateTime end);

  List<MemberMission> findAllByMemberAndMission_MissionCategoryAndIsCompletedTrue(
      Member member, MissionCategory missionCategory);
=======
  @Query(
      "SELECT mm FROM MemberMission mm WHERE mm.member.id = :memberId AND mm.createdAt >= :start AND mm.createdAt <= :end")
  Optional<MemberMission> findMemberMissionByMemberIdAndDate(
      Long memberId, LocalDateTime start, LocalDateTime end);
>>>>>>> 0475893bfbf301a9f0d6cb8b543fe7ffb7203324
}
