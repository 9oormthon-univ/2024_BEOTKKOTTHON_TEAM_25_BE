package com.goormthonuniv.ownearth.service;

import com.goormthonuniv.ownearth.domain.member.Member;

public interface MemberQueryService {
  Member findMemberById(Long memberId);

  Integer getMonthlyMissionStatus(Member member);
}
