package com.example.consumingrest;

import com.example.consumingrest.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumingRestApplication {

	private static final Logger logger = LoggerFactory.getLogger(ConsumingRestApplication.class);
	private static final String URL_TO_CONNECT = "https://quoters.apps.pcfone.io/api/random";

	@Autowired
	RestTemplate restTemplate;


	public static void main(String[] args) {
		SpringApplication.run(ConsumingRestApplication.class, args);
	}


	@Bean
	public CommandLineRunner runInit() throws NullPointerException{

		return args -> {

			Quote quote =  getQuote();

			try {
				quote = restTemplate.getForObject(URL_TO_CONNECT, Quote.class);

			} catch (RestClientException e) {
				logger.error("Error connecting to " + URL_TO_CONNECT);
			}

			logger.info("Quote: " + quote.toString());
		};
	}


	private Quote getQuote() {
		return new Quote();
	}

}
