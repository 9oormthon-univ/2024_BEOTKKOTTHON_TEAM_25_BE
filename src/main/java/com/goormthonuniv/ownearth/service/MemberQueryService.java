package com.goormthonuniv.ownearth.service;

import java.time.YearMonth;
import java.util.List;

import com.goormthonuniv.ownearth.domain.enums.ItemCategory;
import com.goormthonuniv.ownearth.domain.enums.MissionCategory;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.InventoryItemResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.FriendEarthStatusResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.GetEarthResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.MonthlyMissionStatusResponse;

public interface MemberQueryService {

  Member findMemberById(Long memberId);

  Integer getMonthlyMissionStatus(Member member);

  List<MonthlyMissionStatusResponse> getFriendsMonthlyMissionStatus(Member member);

  List<MemberMission> getMonthlyCompletedMissions(
      Member member, YearMonth queryYearMonth, MissionCategory category);

  List<Friend> getFriendRequests(Member member);

  GetEarthResponse getEarthStatus(Member member);

  List<Member> searchMembers(Member member, String keyword);

  FriendEarthStatusResponse getFriendEarthStatus(Member member, Long friendId);

  List<InventoryItemResponse> getMyInventoryItem(Member member, ItemCategory itemCategory);
}
