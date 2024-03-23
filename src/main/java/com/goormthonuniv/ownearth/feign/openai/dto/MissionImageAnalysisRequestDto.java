package com.goormthonuniv.ownearth.feign.openai.dto;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.goormthonuniv.ownearth.feign.openai.Prompt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class MissionImageAnalysisRequestDto {

  private String model = "gpt-4-vision-preview";
  private List<Message> messages;

  public MissionImageAnalysisRequestDto(List<Message> messages) {
    this.messages = messages;
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Message {

    private Role role;
    private List<Content> content;
  }

  @RequiredArgsConstructor
  @Getter
  public enum Role {
    USER("user"),
    SYSTEM("system");

    @JsonValue private final String value;
  }

  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  @Getter
  @JsonInclude(Include.NON_NULL)
  public static class Content {

    private Type type;
    private String text;

    @JsonProperty("image_url")
    private ImageUrl imageUrl;

    public static Content textContent(String text) {
      return new Content(Type.TEXT, text, null);
    }

    public static Content imageContent(String imageUrl) {
      return new Content(Type.IMAGE_URL, null, new ImageUrl(imageUrl));
    }
  }

  @RequiredArgsConstructor
  @Getter
  public enum Type {
    TEXT("text"),
    IMAGE_URL("image_url");

    @JsonValue private final String value;
  }

  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  @Getter
  public static class ImageUrl {

    private String url;
  }

  public static MissionImageAnalysisRequestDto from(String mission, MultipartFile image)
      throws IOException {
    String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
    String imageUrl = String.format("data:%s;base64,%s", image.getContentType(), base64Image);
    return new MissionImageAnalysisRequestDto(createMessages(mission, imageUrl));
  }

  private static List<Message> createMessages(String mission, String imageUrl) {
    return List.of(
        new Message(Role.SYSTEM, createSystemContents()),
        new Message(Role.USER, createUserContents(mission, imageUrl)));
  }

  private static List<Content> createSystemContents() {
    return List.of(Content.textContent(Prompt.getSystemPrompt()));
  }

  private static List<Content> createUserContents(String mission, String imageUrl) {
    return List.of(
        Content.textContent(Prompt.getUserPrompt(mission)), Content.imageContent(imageUrl));
  }
}
