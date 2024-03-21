package com.goormthonuniv.ownearth.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.enums.ItemCategory;
import com.goormthonuniv.ownearth.repository.ItemRepository;
import com.goormthonuniv.ownearth.service.ItemQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemQueryServiceImpl implements ItemQueryService {

  private final ItemRepository itemRepository;

  @Override
  public List<Item> getItemsByItemCategory(ItemCategory itemCategory) {
    return itemRepository.findItemsByItemCategory(itemCategory);
  }
}
