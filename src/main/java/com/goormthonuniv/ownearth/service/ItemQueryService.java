package com.goormthonuniv.ownearth.service;

import java.util.List;

import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.enums.ItemCategory;

public interface ItemQueryService {

  List<Item> getItemsByItemCategory(ItemCategory itemCategory);
}
