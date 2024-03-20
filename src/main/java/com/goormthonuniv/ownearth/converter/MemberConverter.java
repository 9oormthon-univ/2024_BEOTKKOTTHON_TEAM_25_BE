package com.goormthonuniv.ownearth.converter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.Mission;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.domain.member.Password;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.ItemIdCategory;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.AcceptFriendResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.CompletedMissionResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.FriendRequestResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.GetEarthResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.LoginMemberResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.MonthlyMissionStatusResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.RequestFriendSuccessResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.SignUpMemberResponse;

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

  public static List<CompletedMissionResponse> toCompletedMissionResponseList(
      List<MemberMission> memberMissions) {
    return memberMissions.stream().map(MemberConverter::toCompletedMissionResponse).toList();
  }

  private static CompletedMissionResponse toCompletedMissionResponse(MemberMission memberMission) {
    Mission mission = memberMission.getMission();
    return CompletedMissionResponse.builder()
        .completedAt(memberMission.getCompletedAt())
        .memberMissionId(memberMission.getId())
        .missionContent(mission.getContent())
        .missionCategory(mission.getMissionCategory())
        .imageUrl(memberMission.getImageUrl())
        .build();
  }

  public static RequestFriendSuccessResponse toRequestFriendSuccessResponse(Friend friend) {
    return RequestFriendSuccessResponse.builder().requestId(friend.getId()).build();
  }

  public static Friend toFriend(Member fromMember, Member targetMember, Boolean isFriend) {
    return Friend.builder()
        .fromMember(fromMember)
        .toMember(targetMember)
        .isFriend(isFriend)
        .build();
  }

  public static AcceptFriendResponse toAcceptFriendResponse(Friend friend) {
    return AcceptFriendResponse.builder()
        .isFriend(friend.getIsFriend())
        .memberId(friend.getFromMember().getId())
        .build();
  }

  public static GetEarthResponse toGetEarthResponse(
      List<ItemIdCategory> usingItems, String earthName, Long createdAt) {
    return GetEarthResponse.builder()
        .usingItems(usingItems)
        .earthName(earthName)
        .createdAt(createdAt)
        .build();
  }

  public static List<FriendRequestResponse> toFriendRequestResponseList(List<Friend> requests) {
    return requests.stream().map(MemberConverter::toFriendRequestResponse).toList();
  }

  public static Friend toFriend(Member fromMember, Member targetMember, Boolean isFriend) {
    return Friend.builder()
        .fromMember(fromMember)
        .toMember(targetMember)
        .isFriend(isFriend)
        .build();
  }

  public static AcceptFriendResponse toAcceptFriendResponse(Friend friend) {
    return AcceptFriendResponse.builder()
        .isFriend(friend.getIsFriend())
        .memberId(friend.getFromMember().getId())
        .build();
  }

  public static GetEarthResponse toGetEarthResponse(
      List<ItemIdCategory> usingItems, String earthName, Long createdAt) {
    return GetEarthResponse.builder()
        .usingItems(usingItems)
        .earthName(earthName)
        .createdAt(createdAt)
        .build();
  private static FriendRequestResponse toFriendRequestResponse(Friend request) {
    return FriendRequestResponse.builder()
        .requestId(request.getId())
        .memberId(request.getFromMember().getId())
        .name(request.getFromMember().getName())
        .build();
  }
}
