package com.goormthonuniv.ownearth.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.service.MemberItemCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberItemCommandServiceImpl implements MemberItemCommandService {

  @Override
  public void createMemberItem(Member member) {}
}
