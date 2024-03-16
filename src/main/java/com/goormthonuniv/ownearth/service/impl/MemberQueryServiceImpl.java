package com.goormthonuniv.ownearth.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.MemberException;
import com.goormthonuniv.ownearth.repository.MemberRepository;
import com.goormthonuniv.ownearth.service.MemberQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {
  private final MemberRepository memberRepository;

  @Override
  public Member findMemberById(Long memberId) {
    return memberRepository
        .findById(memberId)
        .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));
  }
}
