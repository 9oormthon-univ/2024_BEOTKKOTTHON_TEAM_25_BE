package com.goormthonuniv.ownearth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.member.Member;

public interface FriendRepository extends JpaRepository<Friend, Long> {
  List<Friend> findAllByFromMemberAndIsFriendTrue(Member fromMember);
}
