package com.goormthonuniv.ownearth.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.ItemPurchasedResponse;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.ItemResponse;

@Component
public class ItemConverter {
  private static ItemResponse toItemResponse(Item item, Member member) {
    boolean isPurchased =
        member.getMemberItems().stream()
            .anyMatch(memberItem -> memberItem.getItem().getId().equals(item.getId()));

    return ItemResponse.builder()
        .id(item.getId())
        .name(item.getName())
        .price(item.getPrice())
        .itemCategory(item.getItemCategory())
        .isPurchased(isPurchased)
        .build();
  }

  public static List<ItemResponse> toItemResponseList(List<Item> items, Member member) {
    return items.stream()
        .map(item -> ItemConverter.toItemResponse(item, member))
        .collect(Collectors.toList());
  }

  public static ItemPurchasedResponse toItemPurchasedResponse(MemberItem memberItem) {
    memberItem
        .getMember()
        .setPoint(memberItem.getMember().getPoint() - memberItem.getItem().getPrice());
    return ItemPurchasedResponse.builder()
        .purchasedId(memberItem.getId())
        .itemId(memberItem.getItem().getId())
        .build();
  }
}
