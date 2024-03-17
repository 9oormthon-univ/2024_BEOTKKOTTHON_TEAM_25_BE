package com.goormthonuniv.ownearth.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.domain.member.Password;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.*;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.GlobalException;
import com.goormthonuniv.ownearth.repository.MemberRepository;
import com.goormthonuniv.ownearth.service.MemberCommandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberCommandServiceImpl implements MemberCommandService {

  private final MemberRepository memberRepository;

  @Override
  public Member signUpMember(SignUpMemberRequest request) {

    memberRepository
        .findByEmail(request.getEmail())
        .ifPresent(
            message -> {
              throw new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL);
            });

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    log.info(request.getPassword());
    log.info(Password.isPasswordValid(request.getPassword()).toString());
    log.info(Password.encrypt(request.getPassword(), encoder).toString());

    Member member =
        Member.builder()
            .email(request.getEmail())
            .password(Password.encrypt(request.getPassword(), encoder))
            .name(request.getName())
            .earthName(request.getEarthName())
            .build();

    return memberRepository.save(member);
  }
}
