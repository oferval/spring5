package com.example.consumingrest.handler;

import com.example.consumingrest.web.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ConfigurationFactory {


  @Bean
  public RestTemplate getRestTemplate (RestTemplateBuilder restTemplateBuilder){

    return restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler())
        .setConnectTimeout(Duration.ofSeconds(2))
        .basicAuthentication("","")
        .defaultHeader("","")
        .build();
  }
}
