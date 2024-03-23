package com.goormthonuniv.ownearth.dto.response;

import com.goormthonuniv.ownearth.domain.enums.ItemCategory;

import lombok.*;

public class ItemResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ItemResponse {
    Long itemId;
    String name;
    Integer price;
    ItemCategory itemCategory;
    Boolean isPurchased;
    Boolean isUsing;
    String imageUrl;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ItemPurchasedResponse {
    Long purchasedId;
    Long itemId;
  }
}
