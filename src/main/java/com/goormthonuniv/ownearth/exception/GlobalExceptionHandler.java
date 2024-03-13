package com.goormthonuniv.ownearth.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.goormthonuniv.ownearth.common.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {GlobalException.class})
  protected ApiResponse handleCustomException(GlobalException e) {
    log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
    return ApiResponse.onFailure(e.getErrorCode(), "");
  }
}
