package com.goormthonuniv.ownearth.exception;

public class S3Exception extends GlobalException {

  public S3Exception(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
