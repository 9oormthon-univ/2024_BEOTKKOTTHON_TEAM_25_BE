package com.goormthonuniv.ownearth.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.goormthonuniv.ownearth.annotation.auth.AuthMember;
import com.goormthonuniv.ownearth.common.BaseResponse;
import com.goormthonuniv.ownearth.converter.ItemConverter;
import com.goormthonuniv.ownearth.converter.MemberConverter;
import com.goormthonuniv.ownearth.domain.enums.MissionCategory;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.FriendAcceptRequest;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.LoginMemberRequest;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.AcceptFriendResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.CompletedMissionResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.FriendEarthStatusResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.FriendRequestResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.GetEarthResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.GetPointResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.LoginMemberResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.MonthlyMissionStatusResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.RequestFriendSuccessResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.SearchMemberResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.SignUpMemberResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.ToggleItemUsingResponse;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.service.ItemCommandService;
import com.goormthonuniv.ownearth.service.MemberCommandService;
import com.goormthonuniv.ownearth.service.MemberQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
@Tag(name = "😎 Member", description = "사용자 관련 API")
public class MemberController {

  private final MemberCommandService memberCommandService;
  private final MemberQueryService memberQueryService;
  private final ItemCommandService itemCommandService;

  @Operation(summary = "회원가입 API", description = "이메일, 비밀번호를 사용해 회원가입을 진행합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<SignUpMemberResponse> signUpMember(@RequestBody SignUpMemberRequest request) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED,
        MemberConverter.toSignUpMemberResponse(memberCommandService.signUpMember(request)));
  }

  @Operation(summary = "로그인 API", description = "이메일, 비밀번호를 사용한 로그인을 진행합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<LoginMemberResponse> loginMember(@RequestBody LoginMemberRequest request) {
    return BaseResponse.onSuccess(GlobalErrorCode.CREATED, memberCommandService.login(request));
  }

  @Operation(summary = "내 월간 미션 달성률 조회 API", description = "현재 로그인한 사용자의 월간 달성률을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "성공")
  @GetMapping("/me/missions/monthly")
  public BaseResponse<MonthlyMissionStatusResponse> getMonthlyMissionStatus(
      @Parameter(hidden = true) @AuthMember Member member) {
    Integer completedMissionCount = memberQueryService.getMonthlyMissionStatus(member);
    return BaseResponse.onSuccess(
        MemberConverter.toMonthlyMissionStatusResponse(member, completedMissionCount));
  }

  @Operation(summary = "내 친구들 월간 미션 달성률 조회 API", description = "현재 로그인한 사용자의 친구의 월간 달성률을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "성공")
  @GetMapping("/me/friends/missions/monthly")
  public BaseResponse<List<MonthlyMissionStatusResponse>> getFriendsMonthlyMissionStatus(
      @Parameter(hidden = true) @AuthMember Member member) {
    List<MonthlyMissionStatusResponse> response =
        memberQueryService.getFriendsMonthlyMissionStatus(member);
    return BaseResponse.onSuccess(response);
  }

  @Operation(summary = "완료한 미션 목록 조회 API", description = "날짜별, 종류별로 완료한 미션을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "성공")
  @GetMapping("/me/missions/completed")
  public BaseResponse<List<CompletedMissionResponse>> getCompletedMissions(
      @Parameter(hidden = true) @AuthMember Member member,
      @Parameter(description = "날짜별 조회, 형식 yyyy-mm")
          @RequestParam(value = "yearMonth", required = false)
          YearMonth yearMonth,
      @RequestParam(value = "category", required = false) MissionCategory category) {
    List<MemberMission> memberMissions =
        memberQueryService.getMonthlyCompletedMissions(member, yearMonth, category);
    return BaseResponse.onSuccess(MemberConverter.toCompletedMissionResponseList(memberMissions));
  }

  @Operation(summary = "친구 요청 API", description = "친구 요청을 보냅니다.")
  @ApiResponse(responseCode = "201", description = "성공")
  @PostMapping("/{id}/friends/requests")
  public BaseResponse<RequestFriendSuccessResponse> requestFriend(
      @Parameter(hidden = true) @AuthMember Member member,
      @PathVariable(name = "id") Long targetMemberId) {
    Friend friend = memberCommandService.requestFriend(member, targetMemberId);
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED, MemberConverter.toRequestFriendSuccessResponse(friend));
  }

  @Operation(summary = "친구 요청 목록 조회 API", description = "친구 요청 목록을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "성공")
  @GetMapping("/me/friends/requests")
  public BaseResponse<List<FriendRequestResponse>> getFriendRequests(
      @Parameter(hidden = true) @AuthMember Member member) {
    List<Friend> requests = memberQueryService.getFriendRequests(member);

    return BaseResponse.onSuccess(MemberConverter.toFriendRequestResponseList(requests));
  }

  @Operation(summary = "내 지구 상태 조회 API", description = "지구 이름, 사용 아이템, 가입한 기간, 할당된 미션을 조회합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @GetMapping("/earth")
  public BaseResponse<GetEarthResponse> getMyEarthStatus(
      @Parameter(hidden = true) @AuthMember Member member) {
    return BaseResponse.onSuccess(memberQueryService.getEarthStatus(member));
  }

  @Operation(summary = "친구 요청 수락 API", description = "친구 요청을 수락합니다.")
  @ApiResponse(responseCode = "201", description = "성공")
  @PostMapping("/me/friends")
  public BaseResponse<AcceptFriendResponse> acceptFriend(
      @Parameter(hidden = true) @AuthMember Member member,
      @RequestBody FriendAcceptRequest request) {
    Friend friend = memberCommandService.acceptFriendRequest(member, request);
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED, MemberConverter.toAcceptFriendResponse(friend));
  }

  @Operation(summary = "친구 요청 거절 API", description = "친구 요청을 거절합니다.")
  @ApiResponse(responseCode = "204")
  @DeleteMapping("/me/friends/requests/{id}")
  public BaseResponse<Void> refuseRequest(
      @Parameter(hidden = true) @AuthMember Member member, @PathVariable("id") Long requestId) {
    memberCommandService.refuseFriendRequest(member, requestId);
    return BaseResponse.onSuccess(GlobalErrorCode.DELETED, null);
  }

  @Operation(summary = "포인트 조회 API", description = "회원의 포인트를 조회합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @GetMapping("/me/point")
  public BaseResponse<GetPointResponse> getPoint(
      @Parameter(hidden = true) @AuthMember Member member) {
    return BaseResponse.onSuccess(MemberConverter.toGetPointResponse(member));
  }

  @Operation(summary = "친구 삭제 API", description = "친구를 삭제합니다.")
  @ApiResponse(responseCode = "204", description = "성공")
  @DeleteMapping("/me/friends/{id}")
  public BaseResponse<Void> deleteFriend(
      @Parameter(hidden = true) @AuthMember Member member,
      @PathVariable("id") Long targetMemberId) {
    memberCommandService.deleteFriend(member, targetMemberId);
    return BaseResponse.onSuccess(GlobalErrorCode.DELETED, null);
  }

  @Operation(summary = "아이템 구매 API", description = "아이템을 구매합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/me/items/{itemId}")
  public BaseResponse<ItemResponseDto.ItemPurchasedResponse> createMemberItem(
      @Parameter(hidden = true) @AuthMember Member member,
      @PathVariable(name = "itemId") Long itemId) {
    return BaseResponse.onSuccess(
        ItemConverter.toItemPurchasedResponse(itemCommandService.createMemberItem(itemId, member)));
  }

  @Operation(summary = "회원 검색 API", description = "회원을 검색합니다.")
  @ApiResponse(responseCode = "200", description = "성공")
  @GetMapping
  public BaseResponse<List<SearchMemberResponse>> searchMembers(
      @Parameter(hidden = true) @AuthMember Member member,
      @RequestParam("keyword") String keyword) {
    List<Member> searchResults = memberQueryService.searchMembers(member, keyword);
    return BaseResponse.onSuccess(
        MemberConverter.toSearchMemberResponseList(member, searchResults));
  }

  @Operation(
      summary = "아이템 사용/해제 API",
      description = "아이템을 사용 중이라면 사용 해제, 사용 중이지 않다면 사용 중으로 변경합니다.")
  @ApiResponse(responseCode = "202", description = "성공")
  @PatchMapping("/me/items/{id}/using")
  public BaseResponse<ToggleItemUsingResponse> toggleItemUsing(
      @Parameter(hidden = true) @AuthMember Member member, @PathVariable("id") Long itemId) {
    MemberItem memberItem = memberCommandService.toggleItemUsing(member, itemId);

    return BaseResponse.onSuccess(
        GlobalErrorCode.UPDATED, MemberConverter.toToggleItemUsingResponse(memberItem));
  }

  @Operation(
      summary = "친구 정보 조회 API",
      description = "친구의 지구 이름, 사용 아이템, 포인트, 보유 아이템 수, 지난 7일간 달성한 미션을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "성공")
  @GetMapping("/me/friend/earth/{friendId}")
  public BaseResponse<FriendEarthStatusResponse> getFriendEarthStatus(
      @Parameter(hidden = true) @AuthMember Member member,
      @PathVariable("friendId") Long friendId) {
    return BaseResponse.onSuccess(memberQueryService.getFriendEarthStatus(member, friendId));
  }
}
