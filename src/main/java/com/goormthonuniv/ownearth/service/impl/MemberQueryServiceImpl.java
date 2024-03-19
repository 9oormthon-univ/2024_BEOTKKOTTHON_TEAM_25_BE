package com.goormthonuniv.ownearth.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.converter.MemberConverter;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.MonthlyMissionStatusResponse;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.MemberException;
import com.goormthonuniv.ownearth.repository.FriendRepository;
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
}
