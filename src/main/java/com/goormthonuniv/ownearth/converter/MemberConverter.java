package com.goormthonuniv.ownearth.converter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.domain.member.Password;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.*;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.*;

@Component
public class MemberConverter {

  public static SignUpMemberResponse toSignUpMemberResponse(Member member) {
    return SignUpMemberResponse.builder()
        .email(member.getEmail())
        .name(member.getName())
        .earthName(member.getEarthName())
        .build();
  }

  public static Member toMember(SignUpMemberRequest request) {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    return Member.builder()
        .email(request.getEmail())
        .password(Password.encrypt(request.getPassword(), encoder))
        .name(request.getName())
        .earthName(request.getEarthName())
        .build();
  }

  public static LoginMemberResponse toLoginMemberResponse(
      Long memberId, String accessToken, String refreshToken) {
    return LoginMemberResponse.builder()
        .memberId(memberId)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  public static MonthlyMissionStatusResponse toMonthlyMissionStatusResponse(
      Member member, Integer completedMissionCount) {
    return MonthlyMissionStatusResponse.builder()
        .memberId(member.getId())
        .name(member.getName())
        .completedMissionCount(completedMissionCount)
        .accumulatedPoint(member.getMonthlyPoint())
        .completionRate(completedMissionCount * 100 / LocalDate.now().lengthOfMonth())
        .build();
  }

  public static GetMyEarthResponse toGetMyEarthResponse(
      List<Long> myUsingItems,
      String earthName,
      Long daySinceCreation,
      Optional<MemberMission> memberMission) {
    return GetMyEarthResponse.builder()
        .myUsingItems(myUsingItems)
        .earthName(earthName)
        .daySinceCreation(daySinceCreation)
        .memberMission(memberMission)
        .build();
  }
}
