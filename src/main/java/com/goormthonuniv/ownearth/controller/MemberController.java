package com.goormthonuniv.ownearth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goormthonuniv.ownearth.common.ApiResponse;
import com.goormthonuniv.ownearth.converter.MemberConverter;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.*;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.*;
import com.goormthonuniv.ownearth.exception.GlobalException;
import com.goormthonuniv.ownearth.service.MemberCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
public class MemberController {

  private final MemberCommandService memberService;

  @Operation(summary = "회원가입 API", description = "이메일, 비밀번호를 사용해 회원가입을 진행합니다")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "COMMOM200",
        description = "성공"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "CONFLICT_REQUEST409",
        description = "중복된 이메일이 있습니다"),
  })
  @PostMapping("/signup")
  public ApiResponse<SignUpMemberResponse> signUpMember(@RequestBody SignUpMemberRequest request) {
    try {
      return ApiResponse.onSuccess(
          MemberConverter.toSignUpMember(memberService.signUpMember(request)));
    } catch (GlobalException e) {
      return ApiResponse.onFailure(
          e.getErrorCode(), MemberConverter.toSignUpMember(memberService.signUpMember(request)));
    }
  }

  @Operation(summary = "로그인 API", description = "이메일, 비밀번호를 사용한 로그인을 진행합니다")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "COMMMON200",
        description = "성공"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "NotFound404",
        description = "존재하니 않는 이메일입니다"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "BAD_REQUEST400",
        description = "비밀번호가 일치하지 않습니다"),
  })
  @PostMapping("/login")
  public ApiResponse<LoginMemberResponse> loginMember(@RequestBody LoginMemberRequest request) {
    return ApiResponse.onSuccess(memberService.login(request));
  }
}
