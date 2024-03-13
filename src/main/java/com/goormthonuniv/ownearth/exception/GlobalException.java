package com.goormthonuniv.ownearth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException {

  private final GlobalErrorCode errorCode;
}
