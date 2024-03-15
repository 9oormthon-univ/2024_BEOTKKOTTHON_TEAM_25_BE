package com.goormthonuniv.ownearth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/")
@Tag(name = "🌐 Root", description = "루트 API")
public class RootController {
  @GetMapping("/health")
  @Operation(summary = "배포 서버 생존 확인 API")
  public String healthCheck() {
    return "Hello Own Earth!";
  }
}
