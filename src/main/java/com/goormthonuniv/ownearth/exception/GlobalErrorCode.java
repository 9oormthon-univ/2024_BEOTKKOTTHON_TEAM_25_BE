package com.goormthonuniv.ownearth.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {
  CREATED(HttpStatus.CREATED, "요청 성공 및 리소스 생성됨"),
  UPDATED(HttpStatus.ACCEPTED, "요청 성공 및 리소스 수정됨"),
  DELETED(HttpStatus.NO_CONTENT, "요청 성공 및 리소스 삭제됨"),

  //  Member
  // 400 BAD_REQUEST - 잘못된 요청
  NOT_VALID_EMAIL(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 입니다."),
  PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
  DUPLICATE_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 동일합니다."),
  NOT_VALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 영문, 숫자, 특수문자를 포함한 9~16글자여야 합니다."),
  WRONG_EMAIL_FORM(HttpStatus.BAD_REQUEST, "잘못된 이메일 형식입니다."),
  NOT_MATCH_CODE(HttpStatus.BAD_REQUEST, "코드가 일치하지 않습니다"),
  NOT_VALID_KEYWORD(HttpStatus.BAD_REQUEST, "유효하지 않은 검색어 입니다."),
  NOT_ENOUGH_POINTS(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."),

  // 401 Unauthorized - 미인증
  TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰의 유효기간이 지났습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
  LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
  INVALID_CODE(HttpStatus.UNAUTHORIZED, "유효하지 않은 코드입니다."),
  AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),

  // 403 Forbidden - 권한 없음
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),

  // 404 Not Found - 찾을 수 없음
  EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이메일 입니다."),
  NEED_AGREE_REQUIRE_TERMS(HttpStatus.NOT_FOUND, "필수 약관에 동의해 주세요."),
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 사용자가 없습니다."),
  MEMBER_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 사용자 정보가 없습니다."),
  BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하는 북마크가 없습니다."),

  // 409 CONFLICT : Resource 를 찾을 수 없음
  DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일이 존재합니다."),

  // S3
  S3_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3 명령 수행에 실패했습니다."),
  S3_FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "S3에 해당 파일이 없습니다."),
  INVALID_FILE(HttpStatus.UNPROCESSABLE_ENTITY, "처리할 수 없는 파일입니다."),

  // Mission
  MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 미션이 존재하지 않습니다."),
  MISSION_IMAGE_ANALYSIS_FAILED(HttpStatus.BAD_REQUEST, "미션 이미지 분석에 실패했습니다. 이미지가 유효하지 않은 것 같습니다."),
  MISSION_ALREADY_ACCOMPLISHED(HttpStatus.BAD_REQUEST, "이미 완료된 미션입니다."),

  // Member
  MISSION_QUERY_CONDITION_INCORRECT(HttpStatus.BAD_REQUEST, "조회 조건이 올바르지 않습니다."),
  ALREADY_FRIEND(HttpStatus.BAD_REQUEST, "이미 요청했거나 친구 상태입니다."),
  REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 요청입니다."),
  NOT_FRIEND(HttpStatus.BAD_REQUEST, "친구가 아닌 회원입니다."),
  INVALID_SEARCH_KEYWORD(HttpStatus.BAD_REQUEST, "검색 키워드가 유효하지 않습니다."),
  ITEM_NOT_PURCHASED(HttpStatus.NOT_FOUND, "구매한 아이템이 아닙니다."),

  // Item
  ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아이템입니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
