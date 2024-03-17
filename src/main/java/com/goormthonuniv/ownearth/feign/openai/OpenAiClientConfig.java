package com.goormthonuniv.ownearth.feign.openai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.Logger.Level;
import feign.RequestInterceptor;

public class OpenAiClientConfig {
  @Bean
  public RequestInterceptor requestInterceptor(@Value("${openai.api-key}") String apiKey) {
    return template -> template.header("Authorization", "Bearer " + apiKey);
  }

  @Bean
  public Logger.Level loggerLevel() {
    return Level.FULL;
  }
}
