package com.goormthonuniv.ownearth.converter;

import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.ItemResponse;

@Component
public class ItemConverter {
  public static ItemResponse toItemResponse(Item item) {
    return ItemResponse.builder()
        .id(item.getId())
        .name(item.getName())
        .price(item.getPrice())
        .itemCategory(item.getItemCategory())
        .build();
  }
}
