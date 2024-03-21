package com.goormthonuniv.ownearth.service;

import java.util.List;

import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.member.Member;

public interface ItemQueryService {

  List<Item> getItemsByItemCategory(Member member, String itemCategory);
}
