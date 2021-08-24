package com.jeremy.antdlib;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableScheduling
@EnableWebSocket
@ComponentScan(basePackages={"com.jeremy"})
public class AntdlibApplication {

  @Value("${spring.profiles.active}")
  private static String profiles;

  public static void main(String[] args) {
    SpringApplication.run(AntdlibApplication.class, args);
  }

  @Bean
  public TaskScheduler taskScheduler() {
    TaskScheduler scheduler = new ThreadPoolTaskScheduler();
    return scheduler;
  }

}
