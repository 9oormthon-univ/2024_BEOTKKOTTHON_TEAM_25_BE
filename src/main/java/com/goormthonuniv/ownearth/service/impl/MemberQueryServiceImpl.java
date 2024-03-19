package com.goormthonuniv.ownearth.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.converter.MemberConverter;
import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.*;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.MemberException;
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
  private final MemberItemRepository memberItemRepository;
  private final MemberConverter memberConverter;

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

  public GetMyEarthResponse getMyEarthStatus(Member member) {

    List<MemberItem> usedMemberItems = memberItemRepository.findUsingItem(member.getId());

    List<Long> myUsingItems =
        usedMemberItems.stream()
            .map(MemberItem::getItem)
            .map(Item::getId)
            .collect(Collectors.toList());

    LocalDateTime now = LocalDateTime.now();

    LocalDateTime start = now.withHour(0).withMinute(0).withSecond(0).withNano(0);

    Optional<MemberMission> memberMission =
        memberMissionRepository.findMemberMissionByMemberIdAndDate(member.getId(), start, now);

    Long daySinceCreation =
        ChronoUnit.DAYS.between(member.getCreatedAt().toLocalDate(), now.toLocalDate());

    return memberConverter.toGetMyEarthResponse(
        myUsingItems, member.getEarthName(), daySinceCreation, memberMission);
  }
}
