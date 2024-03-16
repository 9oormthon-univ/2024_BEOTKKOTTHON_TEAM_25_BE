package com.goormthonuniv.ownearth.aws.s3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum S3Directory {
  MISSION("mission");

  private final String directory;
}
