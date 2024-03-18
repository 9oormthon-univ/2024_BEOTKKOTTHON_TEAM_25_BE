package com.goormthonuniv.ownearth.feign.openai.dto;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MissionImageAnalysisRequestDto {
  private String model = "gpt-4-vision-preview";
  private List<Message> messages;

  public MissionImageAnalysisRequestDto(List<Message> messages) {
    this.messages = messages;
  }

  @Getter
  public static class Message {
    private String role = "user";
    private List<Content> content;

    public Message(List<Content> contentList) {
      this.content = contentList;
    }
  }

  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  @Getter
  @JsonInclude(Include.NON_NULL)
  public static class Content {
    private String type;
    private String text;

    @JsonProperty("image_url")
    private ImageUrl imageUrl;
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
    List<Content> contentList =
        List.of(
            new Content(
                "text",
                "이 이미지가 \"" + mission + "\"라는 미션을 수행하는 이미지입니까? 부가적인 설명 없이 오직 true 혹은 false로만 답하시오.",
                null),
            new Content("image_url", null, new ImageUrl(imageUrl)));

    return new MissionImageAnalysisRequestDto(List.of(new Message(contentList)));
  }
}
