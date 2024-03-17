package com.goormthonuniv.ownearth.feign.openai.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.goormthonuniv.ownearth.feign.openai.OpenAiClientConfig;
import com.goormthonuniv.ownearth.feign.openai.dto.MissionImageAnalysisRequestDto;
import com.goormthonuniv.ownearth.feign.openai.dto.MissionImageAnalysisResponseDto;

@FeignClient(
    name = "open-ai-client",
    url = "https://api.openai.com/v1/chat/completions",
    configuration = OpenAiClientConfig.class)
public interface OpenAiClient {

  @PostMapping
  MissionImageAnalysisResponseDto requestImageAnalysis(
      @RequestBody MissionImageAnalysisRequestDto request);
}
