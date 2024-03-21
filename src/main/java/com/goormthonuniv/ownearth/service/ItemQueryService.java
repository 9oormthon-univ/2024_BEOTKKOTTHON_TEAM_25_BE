package com.goormthonuniv.ownearth.service;

import java.util.List;

import com.goormthonuniv.ownearth.domain.Item;

public interface ItemQueryService {

  List<Item> getItemsByItemCategory(String itemCategory);
}
