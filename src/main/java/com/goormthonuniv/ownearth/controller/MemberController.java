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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberCommandService memberService;

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
}
