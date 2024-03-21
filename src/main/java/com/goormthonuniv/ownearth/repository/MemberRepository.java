package com.goormthonuniv.ownearth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthonuniv.ownearth.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  List<Member> findByEarthNameContainsAndIdNot(String earthName, Long id);
}
