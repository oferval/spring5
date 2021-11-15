package org.oferval.demo.controller;

import org.oferval.demo.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class GreetingController {

  private static final String template = "Hello %s!";
  private final AtomicInteger counter = new AtomicInteger();


  @GetMapping ("/greeting")
  public Greeting greeting ( @RequestParam(name = "name", defaultValue = "World") String name ){

    return new Greeting( counter.incrementAndGet(), String.format(template, name ));
  }

}
