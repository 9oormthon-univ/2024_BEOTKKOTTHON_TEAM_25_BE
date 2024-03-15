package com.goormthonuniv.ownearth.exception;

public class AuthException extends GlobalException {

  public AuthException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
