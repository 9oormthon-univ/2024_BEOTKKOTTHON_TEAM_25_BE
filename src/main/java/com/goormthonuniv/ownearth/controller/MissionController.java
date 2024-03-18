package com.goormthonuniv.ownearth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goormthonuniv.ownearth.annotation.auth.AuthMember;
import com.goormthonuniv.ownearth.common.BaseResponse;
import com.goormthonuniv.ownearth.converter.MissionConverter;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.MissionResponseDto.GetOrAssignMemberMissionResponse;
import com.goormthonuniv.ownearth.dto.response.MissionResponseDto.MissionResultDto;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.service.MissionCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "💬 Mission", description = "미션 관련 API")
@RequestMapping("/api/v1/missions")
public class MissionController {
  private final MissionCommandService missionCommandService;

  @Operation(summary = "오늘의 미션 할당/조회 API", description = "오늘의 미션이 없으면 미션을 할당하고 조회, 있다면 미션을 조회합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @PostMapping("/today")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<GetOrAssignMemberMissionResponse> getOrAssignMemberMission(
      @Parameter(hidden = true) @AuthMember Member member) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED,
        MissionConverter.toGetOrAssignMemberMission(
            missionCommandService.getOrAssignMission(member)));
  }

  @Operation(summary = "미션 수행 API", description = "미션 수행 사진을 받아 수행 여부를 결정합니다.")
  @ApiResponse(description = "성공", responseCode = "201")
  @PostMapping(
      value = "/{missionId}/completed",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<MissionResultDto> accomplishMission(
      @Parameter(hidden = true) @AuthMember Member member,
      @PathVariable("missionId") Long missionId,
      @RequestPart("image") MultipartFile missionImage) {
    MemberMission memberMission =
        missionCommandService.accomplishMission(member, missionId, missionImage);
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED, MissionConverter.toMissionResultDto(memberMission));
  }
}
