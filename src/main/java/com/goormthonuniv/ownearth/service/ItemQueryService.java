package com.goormthonuniv.ownearth.service;

import java.util.List;

import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.response.ItemResponseDto.ItemResponse;

public interface ItemQueryService {

  List<ItemResponse> getItemsByItemCategory(Member member, String itemCategory);
}
