package com.example.consumingrest;

import com.example.consumingrest.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumingRestApplication {

	private static final Logger logger = LoggerFactory.getLogger(ConsumingRestApplication.class);

	@Autowired
	RestTemplate restTemplate;

	public static void main(String[] args) {

		SpringApplication.run(ConsumingRestApplication.class, args);
	}


	@Bean
	public RestTemplate getRestTemplate (RestTemplateBuilder restTemplateBuilder){

		return restTemplateBuilder.basicAuthentication("","").defaultHeader("","").build();
	}


	@Bean
	public CommandLineRunner runInit() throws NullPointerException{

		return args -> {
			Quote quote = restTemplate.getForObject("https://quoters.apps.pcfone.io/api/random", Quote.class);
			logger.info("Quote: " + quote.toString());
		};
	}

}
