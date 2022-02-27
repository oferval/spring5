package com.example.consumingrest.web;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return new DefaultResponseErrorHandler().hasError(response);
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    System.out.println("Application Error: " + response.getStatusCode());
    new DefaultResponseErrorHandler().handleError(response);
  }

}
