package com.goormthonuniv.ownearth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.member.Member;

public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {

  List<MemberItem> findMemberItemsByMemberIdAndIsUsingTrue(Long memberId);

  Optional<MemberItem> findByMemberAndItem(Member member, Item item);

  List<MemberItem> findMemberItemsByMemberId(Long memberId);
}
