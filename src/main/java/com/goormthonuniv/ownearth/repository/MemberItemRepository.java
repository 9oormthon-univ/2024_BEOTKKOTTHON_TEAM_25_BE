package com.goormthonuniv.ownearth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goormthonuniv.ownearth.domain.mapping.MemberItem;

public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {

  @Query("SELECT mi FROM MemberItem mi WHERE mi.member.id = :memberId AND mi.isUsing = true")
  List<MemberItem> findUsingItem(Long memberId);
}
