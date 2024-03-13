package com.fastcampus.crash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CrashApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrashApplication.class, args);
  }
}
