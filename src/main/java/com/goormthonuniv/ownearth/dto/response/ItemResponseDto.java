package com.goormthonuniv.ownearth.dto.response;

import com.goormthonuniv.ownearth.domain.enums.ItemCategory;

import lombok.*;

public class ItemResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PUBLIC)
  public static class ItemIdCategory {
    Long id;
    ItemCategory itemCategory;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ItemResponse {
    Long id;
    String name;
    Integer price;
    ItemCategory itemCategory;
    Boolean isPurchased;
  }
}
