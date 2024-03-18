package com.goormthonuniv.ownearth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goormthonuniv.ownearth.common.BaseResponse;
import com.goormthonuniv.ownearth.converter.MemberMissionConverter;
import com.goormthonuniv.ownearth.dto.response.MemberMissionResponseDto.*;
import com.goormthonuniv.ownearth.service.MemberMissionCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/membermissions")
public class MemberMissionController {

  private final MemberMissionCommandService memberMissionCommandService;
  private final MemberMissionConverter memberMissionConverter;

  @Operation(summary = "오늘의 미션 할당/조회 API", description = "오늘의 미션이 없으면 미션을 할당하고 조회, 있다면 미션을 조회합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @PostMapping("/todaymission")
  public BaseResponse<GetOrAssignMemberMissionResponse> getOrAssignMemberMission() {
    return BaseResponse.onSuccess(
        memberMissionConverter.toGetOrAssignMemberMission(
            memberMissionCommandService.getOrAssignMission()));
  }
}
