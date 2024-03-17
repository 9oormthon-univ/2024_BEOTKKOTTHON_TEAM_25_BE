package com.goormthonuniv.ownearth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OwnEarthApplication {

  public static void main(String[] args) {
    SpringApplication.run(OwnEarthApplication.class, args);
  }
}
