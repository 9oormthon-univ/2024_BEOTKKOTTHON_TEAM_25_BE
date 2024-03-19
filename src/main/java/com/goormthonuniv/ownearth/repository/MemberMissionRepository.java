package com.goormthonuniv.ownearth.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
  Optional<MemberMission> findMemberMissionByMemberAndCreatedAtBetween(
      Member member, LocalDateTime start, LocalDateTime end);

  Optional<MemberMission> findByMemberAndMission_Id(Member member, Long missionId);

  Integer countByMemberAndCompletedAtBetween(Member member, LocalDateTime start, LocalDateTime end);

  @Query(
      "SELECT mm FROM MemberMission mm WHERE mm.member.id = :memberId AND mm.createdAt >= :start AND mm.createdAt <= :end")
  Optional<MemberMission> findMemberMissionByMemberIdAndDate(
      Long memberId, LocalDateTime start, LocalDateTime end);
}
