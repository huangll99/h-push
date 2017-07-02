package com.hll.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: huangll
 * Written on 17/6/28.
 */
@SpringBootApplication
public class Bootstrap {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Bootstrap.class);
    app.run();
  }
}
