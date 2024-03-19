package com.goormthonuniv.ownearth.service;

import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.*;

public interface MemberQueryService {
  Member findMemberById(Long memberId);

  Integer getMonthlyMissionStatus(Member member);

  GetMyEarthResponse getMyEarthStatus(Member member);
}
