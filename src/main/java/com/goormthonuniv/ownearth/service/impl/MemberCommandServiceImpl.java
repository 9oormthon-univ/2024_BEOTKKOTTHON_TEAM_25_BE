package com.goormthonuniv.ownearth.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goormthonuniv.ownearth.converter.MemberConverter;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.*;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.*;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.GlobalException;
import com.goormthonuniv.ownearth.repository.MemberRepository;
import com.goormthonuniv.ownearth.security.provider.JwtAuthProvider;
import com.goormthonuniv.ownearth.service.MemberCommandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberCommandServiceImpl implements MemberCommandService {

  private final MemberRepository memberRepository;
  private final JwtAuthProvider jwtAuthProvider;
  private final MemberConverter memberConverter;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public Member signUpMember(SignUpMemberRequest request) {

    memberRepository
        .findByEmail(request.getEmail())
        .ifPresent(
            message -> {
              throw new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL);
            });

    return memberRepository.save(memberConverter.toMember(request));
  }

  @Override
  public LoginMemberResponse login(LoginMemberRequest request) {

    String email = request.getEmail();

    Member member =
        memberRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

    if (!(member.getPassword().isSamePassword(request.getPassword(), bCryptPasswordEncoder))) {
      throw new GlobalException(GlobalErrorCode.PASSWORD_MISMATCH);
    }

    String accessToken = jwtAuthProvider.generateAccessToken(member.getId());
    String refreshToken = jwtAuthProvider.generateRefreshToken(member.getId());

    return memberConverter.toLoginMemberResponse(member.getId(), accessToken, refreshToken);
  }
}
