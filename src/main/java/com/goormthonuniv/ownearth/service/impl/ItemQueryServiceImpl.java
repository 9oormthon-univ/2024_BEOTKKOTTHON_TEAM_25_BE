package com.goormthonuniv.ownearth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.converter.ItemConverter;
import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.enums.ItemCategory;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.ItemResponse;
import com.goormthonuniv.ownearth.repository.ItemRepository;
import com.goormthonuniv.ownearth.service.ItemQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemQueryServiceImpl implements ItemQueryService {

  private final ItemRepository itemRepository;

  @Override
  public List<ItemResponse> getItemsByItemCategory(Member member, String itemCategory) {
    List<Item> items = itemRepository.findItemsByItemCategory(ItemCategory.valueOf(itemCategory));
    return items.stream()
        .map(item -> ItemConverter.toItemResponse(item, member))
        .collect(Collectors.toList());
  }
}
