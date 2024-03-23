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
  public static ItemResponse toItemResponse(Item item, Member member, Boolean urlCheck) {
    boolean isPurchased =
        member.getMemberItems().stream()
            .anyMatch(memberItem -> memberItem.getItem().getId().equals(item.getId()));

    // urlCheck가 true면 EarthUrl을 false면 storeUrl 반환
    if (urlCheck) {
      return ItemResponse.builder()
          .itemId(item.getId())
          .name(item.getName())
          .price(item.getPrice())
          .imageUrl(item.getEarthImageUrl())
          .itemCategory(item.getItemCategory())
          .isPurchased(isPurchased)
          .build();
    }

    return ItemResponse.builder()
        .itemId(item.getId())
        .name(item.getName())
        .price(item.getPrice())
        .imageUrl(item.getStoreImageUrl())
        .itemCategory(item.getItemCategory())
        .isPurchased(isPurchased)
        .build();
  }

  public static List<ItemResponse> toItemResponseList(List<Item> items, Member member) {
    return items.stream()
        .map(item -> ItemConverter.toItemResponse(item, member, false))
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

  public static List<ItemResponse> toInventoryItemResponseList(List<MemberItem> memberItems) {
    return memberItems.stream()
        .map(
            memberItem ->
                ItemConverter.toItemResponse(memberItem.getItem(), memberItem.getMember(), true))
        .collect(Collectors.toList());
  }
}
