package com.goormthonuniv.ownearth.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.goormthonuniv.ownearth.common.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {GlobalException.class})
  protected BaseResponse handleCustomException(GlobalException e) {
    log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
    return BaseResponse.onFailure(e.getErrorCode(), "");
  }
}
