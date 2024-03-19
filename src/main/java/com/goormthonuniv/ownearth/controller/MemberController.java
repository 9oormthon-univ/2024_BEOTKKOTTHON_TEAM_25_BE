package com.goormthonuniv.ownearth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goormthonuniv.ownearth.annotation.auth.AuthMember;
import com.goormthonuniv.ownearth.common.BaseResponse;
import com.goormthonuniv.ownearth.converter.MemberConverter;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.*;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.*;
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

  @Operation(summary = "회원가입 API", description = "이메일, 비밀번호를 사용해 회원가입을 진행합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @PostMapping("/signup")
  public BaseResponse<SignUpMemberResponse> signUpMember(@RequestBody SignUpMemberRequest request) {
    return BaseResponse.onSuccess(
        MemberConverter.toSignUpMemberResponse(memberCommandService.signUpMember(request)));
  }

  @Operation(summary = "로그인 API", description = "이메일, 비밀번호를 사용한 로그인을 진행합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @PostMapping("/login")
  public BaseResponse<LoginMemberResponse> loginMember(@RequestBody LoginMemberRequest request) {
    return BaseResponse.onSuccess(memberCommandService.login(request));
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
}
