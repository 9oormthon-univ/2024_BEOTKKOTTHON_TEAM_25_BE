package com.goormthonuniv.ownearth.feign.openai;

public class Prompt {

  private static final String SYSTEM_PROMPT =
      "You are a helpful assistant designed to analyze images. You need to output result exactly “true” or “false”.";
  private static final String USER_PROMPT =
      """
        First, it is necessary to determine if this image was taken by a person.

        1. If it’s not a photo but a graphic image
        2. If it is a photo taken by a person but contains a company logo or watermark
        3. If the photo is unclear or blurred
        4. If the image is harassing to others or is sexually

        If the image meets any of these criteria, respond with "false".

        Second, if it is confirmed that the image was taken by a person, then it must be determined if the photo aligns with the mission.
        The mission is described in Korean as "{mission}".

        1. If the process of performing the mission is captured, +1 point
        2. If the mission involves a specific object and that object is in the photo, +1 point
        3. If a part of the body of the person performing the mission is included, +1 point
        4. If the entire scene of performing the mission is captured, +1 point
        5. If the photo was taken at a location that matches the mission, +1 point
        6. If the photo is not mixing several missions but clearly performing only the given mission, +1 point

        Check the list and if the score is less than 4 points, respond with "false".

        If the score is 4 points or more, respond with "true".
        """;

  public static String getSystemPrompt() {
    return SYSTEM_PROMPT;
  }

  public static String getUserPrompt(String mission) {
    return USER_PROMPT.replace("{mission}", mission);
  }
}
