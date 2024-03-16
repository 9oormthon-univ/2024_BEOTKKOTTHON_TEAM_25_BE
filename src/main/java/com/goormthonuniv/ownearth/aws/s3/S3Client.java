package com.goormthonuniv.ownearth.aws.s3;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.goormthonuniv.ownearth.config.AmazonConfig;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.S3Exception;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Client {
  private final AmazonS3 amazonS3;
  private final AmazonConfig amazonConfig;
  private String S3_URL_PATTERN;

  @PostConstruct
  private void init() {
    S3_URL_PATTERN =
        "https://"
            + amazonConfig.getBucket()
            + "\\.s3\\."
            + amazonConfig.getRegion()
            + "\\.amazonaws\\.com(.*)";
  }

  public String uploadMissionImage(MultipartFile file) {
    return uploadFile(generateKeyName(S3Directory.MISSION, file.getOriginalFilename()), file);
  }

  public void deleteFileByUrl(String url) {
    deleteFile(getKeyName(url));
  }

  private String uploadFile(String keyName, MultipartFile file) throws S3Exception {
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(file.getSize());
      amazonS3.putObject(
          new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));

      return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    } catch (SdkClientException e) {
      throw new S3Exception(GlobalErrorCode.S3_ERROR);
    } catch (IOException e) {
      throw new S3Exception(GlobalErrorCode.INVALID_FILE);
    }
  }

  private void deleteFile(String keyName) throws S3Exception {
    try {
      if (amazonS3.doesObjectExist(amazonConfig.getBucket(), keyName)) {
        amazonS3.deleteObject(amazonConfig.getBucket(), keyName);
      } else {
        throw new S3Exception(GlobalErrorCode.S3_FILE_NOT_FOUND);
      }
    } catch (Exception e) {
      throw new S3Exception(GlobalErrorCode.S3_ERROR);
    }
  }

  private String getKeyName(String fileUrl) {
    Pattern regex = Pattern.compile(S3_URL_PATTERN);
    Matcher matcher = regex.matcher(fileUrl);

    String keyName = null;
    if (matcher.find()) {
      keyName = matcher.group(1).substring(1);
    }

    return keyName;
  }

  private String generateKeyName(S3Directory dir, String filename) {
    return dir.getDirectory() + "/" + UUID.randomUUID() + "_" + filename;
  }
}
