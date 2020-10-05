package com.jeremy.antdlib;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableScheduling
@EnableWebSocket
@ComponentScan(basePackages={"com"})
public class AntdlibApplication {

  @Value("${spring.profiles.active}")
  private static String profiles;

  public static void main(String[] args) {
//    System.out.println(profiles);
    SpringApplication.run(AntdlibApplication.class, args);
  }

}
