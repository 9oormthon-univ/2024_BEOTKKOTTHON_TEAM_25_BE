package com.goormthonuniv.ownearth.service;

import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.member.Member;

public interface ItemCommandService {

  MemberItem createMemberItem(Long itemId, Member member);
}
