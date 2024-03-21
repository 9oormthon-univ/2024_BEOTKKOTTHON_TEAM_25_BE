package com.goormthonuniv.ownearth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.enums.ItemCategory;

public interface ItemRepository extends JpaRepository<Item, Long> {

  List<Item> findItemsByItemCategory(ItemCategory itemCategory);
}
