package com.example.consumingrest.handler;

import com.example.consumingrest.domain.Quote;
import com.example.consumingrest.web.RestTemplateResponseErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
@Slf4j
public class ConsumingRestApplicationHandler {

  public static final String URL_TO_CONNECT = "https://quoters.apps.pcfone.io/api/random";

  @Autowired
  RestTemplate restTemplate;


  public Quote run() throws NullPointerException{
    Quote quote = quote = getQuote();

    try {
      quote = restTemplate.getForObject(URL_TO_CONNECT, Quote.class);

    } catch (RestClientException e) {
      log.error("Error connecting to " + URL_TO_CONNECT);
    }

    log.info("Quote: " + quote.toString());

    return quote;
  }


  private Quote getQuote() {
    return new Quote();
  }

}

