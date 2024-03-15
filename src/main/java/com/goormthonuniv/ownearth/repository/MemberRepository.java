package com.goormthonuniv.ownearth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthonuniv.ownearth.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {}
