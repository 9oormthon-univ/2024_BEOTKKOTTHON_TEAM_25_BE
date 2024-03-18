package com.goormthonuniv.ownearth.security.utils;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberUtils {
  private final MemberRepository memberRepository;

  public Long getCurrentMemberId() {
    return SecurityUtils.getCurrentMemberId();
  }

  public Optional<Member> getCurrentMember() {
    return memberRepository.findById(getCurrentMemberId());
  }
}
