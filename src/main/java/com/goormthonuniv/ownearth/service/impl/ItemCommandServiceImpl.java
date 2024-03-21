package com.goormthonuniv.ownearth.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.converter.ItemConverter;
import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.ItemException;
import com.goormthonuniv.ownearth.repository.ItemRepository;
import com.goormthonuniv.ownearth.repository.MemberItemRepository;
import com.goormthonuniv.ownearth.service.ItemCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemCommandServiceImpl implements ItemCommandService {

  private final ItemRepository itemRepository;
  private final MemberItemRepository memberItemRepository;

  @Override
  public MemberItem createMemberItem(Long itemId, Member member) {

    Item item =
        itemRepository
            .findById(itemId)
            .orElseThrow(() -> new ItemException(GlobalErrorCode.ITEM_NOT_FOUND));

    Boolean exist = memberItemRepository.findByMemberAndItem(member, item).isPresent();

    if (exist) {
      throw new ItemException(GlobalErrorCode.ALREADY_PURCHASED);
    } else {

      if (member.getPoint() < item.getPrice())
        throw new ItemException(GlobalErrorCode.NOT_ENOUGH_POINTS);

      member.setPoint(member.getPoint() - item.getPrice());

      return memberItemRepository.save(ItemConverter.toMemberItem(member, item));
    }
  }
}
