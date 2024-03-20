package com.goormthonuniv.ownearth.service;

import java.time.YearMonth;
import java.util.List;

import com.goormthonuniv.ownearth.domain.enums.MissionCategory;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.*;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.MonthlyMissionStatusResponse;

public interface MemberQueryService {

  Member findMemberById(Long memberId);

  Integer getMonthlyMissionStatus(Member member);

  List<MonthlyMissionStatusResponse> getFriendsMonthlyMissionStatus(Member member);

  List<MemberMission> getMonthlyCompletedMissions(
      Member member, YearMonth queryYearMonth, MissionCategory category);

  GetEarthResponse getEarthStatus(Member member);
}
