package com.goormthonuniv.ownearth.service;

import java.time.YearMonth;
import java.util.List;

import com.goormthonuniv.ownearth.domain.enums.ItemCategory;
import com.goormthonuniv.ownearth.domain.enums.MissionCategory;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.GetEarthResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.MonthlyMissionStatusResponse;

public interface MemberQueryService {

  Member findMemberById(Long memberId);

  Integer getMonthlyMissionStatus(Member member);

  List<MonthlyMissionStatusResponse> getFriendsMonthlyMissionStatus(Member member);

  List<MemberMission> getMonthlyCompletedMissions(
      Member member, YearMonth queryYearMonth, MissionCategory category);

  List<Friend> getFriendRequests(Member member);

  GetEarthResponse getEarthStatus(Member member, Long memberId);

  List<Member> searchMembers(Member member, String keyword);

  List<MemberItem> getInventoryItem(Member member, ItemCategory itemCategory, Long memberId);
}
