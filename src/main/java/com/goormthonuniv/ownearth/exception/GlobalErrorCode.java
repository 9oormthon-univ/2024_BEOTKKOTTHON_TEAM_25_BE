package com.goormthonuniv.ownearth.exception;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {
  //  Member
  // 400 BAD_REQUEST - 잘못된 요청
  NOT_VALID_EMAIL(BAD_REQUEST, "유효하지 않은 이메일 입니다."),
  PASSWORD_MISMATCH(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
  DUPLICATE_PASSWORD(BAD_REQUEST, "비밀번호가 동일합니다."),
  NOT_VALID_PASSWORD(BAD_REQUEST, "비밀번호는 영문, 숫자, 특수문자를 포함한 9~16글자여야 합니다."),
  WRONG_EMAIL_FORM(BAD_REQUEST, "잘못된 이메일 형식입니다."),
  NOT_MATCH_CODE(BAD_REQUEST, "코드가 일치하지 않습니다"),
  NOT_VALID_KEYWORD(BAD_REQUEST, "유효하지 않은 검색어 입니다."),

  // 401 Unauthorized - 권한 없음
  TOKEN_EXPIRED(UNAUTHORIZED, "토큰의 유효기간이 지났습니다."),
  INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 토큰입니다."),
  LOGIN_REQUIRED(UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
  INVALID_CODE(UNAUTHORIZED, "유효하지 않은 코드입니다."),
  AUTHENTICATION_REQUIRED(UNAUTHORIZED, "인증 정보가 유효하지 않습니다."),
  MEMBER_NOT_AUTHORIZED(UNAUTHORIZED, "접근 권한이 있는 회원이 아닙니다."),
  // 404 Not Found - 찾을 수 없음
  EMAIL_NOT_FOUND(NOT_FOUND, "존재하지 않는 이메일 입니다."),
  NEED_AGREE_REQUIRE_TERMS(NOT_FOUND, "필수 약관에 동의해 주세요."),
  MEMBER_NOT_FOUND(NOT_FOUND, "등록된 사용자가 없습니다."),
  MEMBER_INFO_NOT_FOUND(NOT_FOUND, "등록된 사용자 정보가 없습니다."),
  BOOKMARK_NOT_FOUND(NOT_FOUND, "존재하는 북마크가 없습니다."),
  // 409 CONFLICT : Resource 를 찾을 수 없음
  DUPLICATE_EMAIL(CONFLICT, "중복된 이메일이 존재합니다."),
  EXPIRED_REFRESH_TOKEN(BAD_REQUEST, "유효기간이 지난 토큰입니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
