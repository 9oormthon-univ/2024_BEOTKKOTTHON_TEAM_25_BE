package com.goormthonuniv.ownearth.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.goormthonuniv.ownearth.annotation.auth.AuthMember;
import com.goormthonuniv.ownearth.common.BaseResponse;
import com.goormthonuniv.ownearth.converter.ItemConverter;
import com.goormthonuniv.ownearth.domain.enums.ItemCategory;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.ItemResponse;
import com.goormthonuniv.ownearth.service.ItemCommandService;
import com.goormthonuniv.ownearth.service.ItemQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/items")
@Tag(name = "Item", description = "아이템 관련 API")
public class ItemController {

  private final ItemQueryService itemQueryService;
  private final ItemCommandService itemCommandService;

  @Operation(summary = "아이템 목록 조회 API", description = "아이템을 카테고리별로 조회합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @GetMapping("/")
  public BaseResponse<List<ItemResponse>> getItemsByItemCategory(
      @Parameter(hidden = true) @AuthMember Member member,
      @RequestParam("category") ItemCategory itemCategory) {
    return BaseResponse.onSuccess(
        ItemConverter.toItemResponseList(
            itemQueryService.getItemsByItemCategory(itemCategory), member));
  }
}
