package com.goormthonuniv.ownearth.converter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.Mission;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.domain.member.Password;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.GetEarthItemResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.AcceptFriendResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.CompletedMissionResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.EarthNameResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.FriendRequestResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.GetEarthResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.GetPointResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.MonthlyMissionStatusResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.RequestFriendSuccessResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.SearchMemberResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.SignUpMemberResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.ToggleItemUsingResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.TokenResponse;

@Component
public class MemberConverter {

  public static SignUpMemberResponse toSignUpMemberResponse(Member member) {
    return SignUpMemberResponse.builder()
        .memberId(member.getId())
        .email(member.getEmail())
        .name(member.getName())
        .build();
  }

  public static Member toMember(SignUpMemberRequest request) {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    return Member.builder()
        .email(request.getEmail())
        .password(Password.encrypt(request.getPassword(), encoder))
        .name(request.getName())
        .build();
  }

  public static EarthNameResponse toEarthNameResponse(String earthName) {
    return EarthNameResponse.builder().earthName(earthName).build();
  }

  public static TokenResponse toLoginMemberResponse(
      Long memberId, String accessToken, String refreshToken) {
    return TokenResponse.builder()
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
      List<GetEarthItemResponse> usingItems,
      String earthName,
      Long createdAt,
      List<LocalDate> completedTimes,
      Integer inventoryCount,
      Integer point) {
    return GetEarthResponse.builder()
        .usingItems(usingItems)
        .earthName(earthName)
        .withDays(createdAt)
        .completedTimes(completedTimes)
        .inventoryCount(inventoryCount)
        .accumulatedPoint(point)
        .build();
  }

  public static GetPointResponse toGetPointResponse(Member member) {
    return GetPointResponse.builder().point(member.getPoint()).build();
  }

  private static FriendRequestResponse toFriendRequestResponse(Friend request) {
    return FriendRequestResponse.builder()
        .requestId(request.getId())
        .memberId(request.getFromMember().getId())
        .name(request.getFromMember().getName())
        .build();
  }

  public static List<SearchMemberResponse> toSearchMemberResponseList(
      Member member, List<Member> searchMembers) {
    return searchMembers.stream()
        .map(searchMember -> toSearchMemberResponse(member, searchMember))
        .toList();
  }

  private static SearchMemberResponse toSearchMemberResponse(Member member, Member searchMember) {
    Friend friend =
        searchMember.getToFriends().stream()
            .filter(toFriend -> toFriend.getFromMember().equals(member))
            .findFirst()
            .orElse(null);

    return SearchMemberResponse.builder()
        .memberId(searchMember.getId())
        .name(searchMember.getName())
        .earthName(searchMember.getEarthName())
        .isFriend(friend != null ? friend.getIsFriend() : false)
        .build();
  }

  public static ToggleItemUsingResponse toToggleItemUsingResponse(MemberItem memberItem) {
    return ToggleItemUsingResponse.builder()
        .itemId(memberItem.getItem().getId())
        .isUsing(memberItem.getIsUsing())
        .build();
  }

  public static TokenResponse toReissueResponse(
      Long memberId, String newAccessToken, String newRefreshToken) {
    return TokenResponse.builder()
        .memberId(memberId)
        .accessToken(newAccessToken)
        .refreshToken(newRefreshToken)
        .build();
  }
}
