package com.goormthonuniv.ownearth.domain.member;

import java.util.regex.Pattern;

import jakarta.persistence.Embeddable;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.MemberException;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Embeddable
public class Password {
  private static final String PASSWORD_REGEX =
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+-=\\[\\]{}|;':\",./<>?~`\\\\])[A-Za-z\\d!@#%^&*()_+\\-=\\[\\]{}|;':\",./<>?~`\\\\]{9,16}";

  private Password(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  private String encryptedPassword;

  public static Password encrypt(String plainPassword, BCryptPasswordEncoder encoder) {
    if (!isPasswordValid(plainPassword)) {
      throw new MemberException(GlobalErrorCode.NOT_VALID_PASSWORD);
    }
    return new Password(encoder.encode(plainPassword));
  }

  public static Boolean isPasswordValid(String plainPassword) {
    return Pattern.matches(PASSWORD_REGEX, plainPassword);
  }

  public Boolean isSamePassword(String plainPassword, BCryptPasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(plainPassword, encryptedPassword);
  }
}
