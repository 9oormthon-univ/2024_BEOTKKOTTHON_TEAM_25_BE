package com.goormthonuniv.ownearth.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.GetEarthItemResponse;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.InventoryItemResponse;
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
        .itemUrl(item.getImageUrl())
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
    return ItemPurchasedResponse.builder()
        .purchasedId(memberItem.getId())
        .itemId(memberItem.getItem().getId())
        .build();
  }

  public static MemberItem toMemberItem(Member member, Item item) {
    return MemberItem.builder().member(member).item(item).build();
  }

  public static InventoryItemResponse toInventoryItemResponse(MemberItem memberItem) {
    return InventoryItemResponse.builder()
        .itemId(memberItem.getItem().getId())
        .itemName(memberItem.getItem().getName())
        .isUsing(memberItem.getIsUsing())
        .imageUrl(memberItem.getItem().getImageUrl())
        .build();
  }

  public static List<InventoryItemResponse> toInventoryItemResponseList(
      List<MemberItem> memberItems) {
    return memberItems.stream()
        .map(memberItem -> ItemConverter.toInventoryItemResponse(memberItem))
        .collect(Collectors.toList());
  }

  public static GetEarthItemResponse toGetEarthItemResponse(MemberItem memberItem) {
    return GetEarthItemResponse.builder()
        .id(memberItem.getItem().getId())
        .name(memberItem.getItem().getName())
        .itemCategory(memberItem.getItem().getItemCategory())
        .itemUrl(memberItem.getItem().getImageUrl())
        .build();
  }
}
