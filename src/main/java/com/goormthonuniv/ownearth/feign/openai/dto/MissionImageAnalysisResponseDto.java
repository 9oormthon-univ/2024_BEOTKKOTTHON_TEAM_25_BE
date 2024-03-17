package com.goormthonuniv.ownearth.feign.openai.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class MissionImageAnalysisResponseDto {
  private List<Choice> choices;

  @Getter
  public static class Choice {
    private Message message;
    private Integer index;
  }

  @Getter
  public static class Message {
    String role;
    String content;
  }
}
