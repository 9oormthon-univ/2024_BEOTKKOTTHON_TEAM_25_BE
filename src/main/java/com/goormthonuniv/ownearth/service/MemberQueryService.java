package com.goormthonuniv.ownearth.service;

import java.util.List;

import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.MonthlyMissionStatusResponse;

public interface MemberQueryService {
  Member findMemberById(Long memberId);

  Integer getMonthlyMissionStatus(Member member);

  List<MonthlyMissionStatusResponse> getFriendsMonthlyMissionStatus(Member member);
}
