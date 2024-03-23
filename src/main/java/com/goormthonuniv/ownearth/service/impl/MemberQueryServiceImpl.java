package com.goormthonuniv.ownearth.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.converter.ItemConverter;
import com.goormthonuniv.ownearth.converter.MemberConverter;
import com.goormthonuniv.ownearth.domain.enums.ItemCategory;
import com.goormthonuniv.ownearth.domain.enums.MissionCategory;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.ItemResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.GetEarthResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.GetMyFriendResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.MonthlyMissionStatusResponse;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.MemberException;
import com.goormthonuniv.ownearth.repository.FriendRepository;
import com.goormthonuniv.ownearth.repository.MemberItemRepository;
import com.goormthonuniv.ownearth.repository.MemberMissionRepository;
import com.goormthonuniv.ownearth.repository.MemberRepository;
import com.goormthonuniv.ownearth.service.MemberQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

  private final MemberRepository memberRepository;
  private final MemberMissionRepository memberMissionRepository;
  private final FriendRepository friendRepository;
  private final MemberItemRepository memberItemRepository;

  @Override
  public Member findMemberById(Long memberId) {
    return memberRepository
        .findById(memberId)
        .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));
  }

  @Override
  public Integer getMonthlyMissionStatus(Member member) {
    LocalDate today = LocalDate.now();
    LocalDateTime firstDayOfMonth = today.withDayOfMonth(1).atStartOfDay();
    LocalDateTime lastDayOfMonth =
        today.withDayOfMonth(today.lengthOfMonth()).atTime(23, 59, 59, 999999999);

    return memberMissionRepository.countByMemberAndCompletedAtBetween(
        member, firstDayOfMonth, lastDayOfMonth);
  }

  @Override
  public List<MonthlyMissionStatusResponse> getFriendsMonthlyMissionStatus(Member member) {
    List<Friend> friends = friendRepository.findAllByFromMemberAndIsFriendTrue(member);
    LocalDate today = LocalDate.now();
    LocalDateTime firstDayOfMonth = today.withDayOfMonth(1).atStartOfDay();
    LocalDateTime lastDayOfMonth =
        today.withDayOfMonth(today.lengthOfMonth()).atTime(23, 59, 59, 999999999);

    return friends.stream()
        .map(Friend::getToMember)
        .map(
            friend -> {
              Integer completedMissions =
                  memberMissionRepository.countByMemberAndCompletedAtBetween(
                      friend, firstDayOfMonth, lastDayOfMonth);

              return MemberConverter.toMonthlyMissionStatusResponse(friend, completedMissions);
            })
        .toList();
  }

  @Override
  public List<MemberMission> getMonthlyCompletedMissions(
      Member member, YearMonth queryYearMonth, MissionCategory category) {
    boolean isDateQuery = queryYearMonth != null && category == null;
    boolean isCategoryQuery = queryYearMonth == null && category != null;

    if (isDateQuery) {
      LocalDate queryDate = queryYearMonth.atDay(1);
      LocalDateTime firstDayOfMonth = queryDate.atStartOfDay();
      LocalDateTime lastDayOfMonth =
          queryDate.withDayOfMonth(queryDate.lengthOfMonth()).atTime(23, 59, 59, 999999999);
      return memberMissionRepository.findAllByMemberAndCompletedAtBetween(
          member, firstDayOfMonth, lastDayOfMonth);
    } else if (isCategoryQuery) {
      return memberMissionRepository.findAllByMemberAndMission_MissionCategoryAndIsCompletedTrue(
          member, category);
    } else {
      throw new MemberException(GlobalErrorCode.MISSION_QUERY_CONDITION_INCORRECT);
    }
  }

  @Override
  public GetEarthResponse getEarthStatus(Member member, Long memberId) {

    if (!(member.getId().equals(memberId))) {
      friendRepository
          .findByToMemberIdAndFromMemberAndIsFriendTrue(memberId, member)
          .orElseThrow(() -> new MemberException(GlobalErrorCode.FRIEND_NOT_FOUND));
    }

    Member who =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));

    List<MemberItem> usedMemberItems =
        memberItemRepository.findMemberItemsByMemberIdAndIsUsingTrue(memberId);

    List<ItemResponse> usingItems =
        usedMemberItems.stream()
            .map(MemberItem::getItem)
            .map(item -> ItemConverter.toItemResponse(item, member, true))
            .toList();

    LocalDateTime now = LocalDateTime.now();

    Long createdAt =
        ChronoUnit.DAYS.between(who.getCreatedAt().toLocalDate(), now.toLocalDate()) + 1;

    LocalDateTime lastSevenDayAgo = now.minusDays(7).truncatedTo(ChronoUnit.DAYS);

    List<String> completedDay =
        memberMissionRepository
            .findByMemberIdAndCompletedAtBetweenAndIsCompletedTrue(
                memberId, lastSevenDayAgo, LocalDateTime.now())
            .stream()
            .map(MemberMission::getCompletedAt)
            .map(LocalDateTime::getDayOfWeek)
            .map(DayOfWeek -> DayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN))
            .toList();

    Integer inventoryCount = memberItemRepository.findMemberItemsByMemberId(memberId).size();

    return MemberConverter.toGetEarthResponse(
        usingItems,
        who.getName(),
        who.getEarthName(),
        createdAt,
        completedDay,
        inventoryCount,
        who.getMonthlyPoint());
  }

  @Override
  public List<Friend> getFriendRequests(Member member) {
    return friendRepository.findAllByToMemberAndIsFriendFalse(member);
  }

  @Override
  public List<Member> searchMembers(Member member, String keyword) {
    if (keyword.isEmpty()) {
      throw new MemberException(GlobalErrorCode.INVALID_SEARCH_KEYWORD);
    }
    return memberRepository.findByEarthNameContainsAndIdNot(keyword, member.getId());
  }

  @Override
  public List<MemberItem> getInventoryItem(
      Member member, ItemCategory itemCategory, Long memberId) {

    if (itemCategory == null) {
      friendRepository
          .findByToMemberIdAndFromMemberAndIsFriendTrue(memberId, member)
          .orElseThrow(() -> new MemberException(GlobalErrorCode.FRIEND_NOT_FOUND));

      return memberItemRepository.findMemberItemsByMemberId(memberId);
    }

    return memberItemRepository.findMemberItemsByMemberAndItemItemCategory(member, itemCategory);
  }

  @Override
  public List<GetMyFriendResponse> getMyFriend(Member member) {
    return friendRepository.findAllByFromMemberAndIsFriendTrue(member).stream()
        .map(
            friend ->
                MemberConverter.toGetMyFriendResponse(
                    friend.getToMember().getId(), friend.getToMember().getName()))
        .toList();
  }
}
