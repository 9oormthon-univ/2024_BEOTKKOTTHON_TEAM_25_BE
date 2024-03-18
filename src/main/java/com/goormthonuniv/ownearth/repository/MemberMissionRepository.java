package com.goormthonuniv.ownearth.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
  Optional<MemberMission> findMemberMissionByMemberAndCreatedAtBetween(
      Member member, LocalDateTime start, LocalDateTime end);
}
