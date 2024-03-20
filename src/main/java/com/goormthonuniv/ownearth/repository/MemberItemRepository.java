package com.goormthonuniv.ownearth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;

public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {

  List<MemberItem> findMemberItemsByMemberIdAndIsUsingTrue(Long memberId);

}
