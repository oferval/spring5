package com.example.consumingrest;

import com.example.consumingrest.domain.Quote;
import com.example.consumingrest.handler.ConsumingRestApplicationHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static com.example.consumingrest.handler.ConsumingRestApplicationHandler.URL_TO_CONNECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**		https://spring.io/guides/gs/consuming-rest/
			https://www.programcreek.com/java-api-examples/?api=org.springframework.test.web.client.MockRestServiceServer
			https://www.baeldung.com/spring-mock-rest-template
			https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/client/MockRestServiceServer.html
*/
@SpringBootTest
class ConsumingRestApplicationHandlerTest {

	@Autowired
	ConsumingRestApplicationHandler consumingRestApplicationHandler;

	@Autowired
	RestTemplate restTemplateMock;

	private ObjectMapper mapper = new ObjectMapper();


	@Test
	void callResTemplateOK_getQuoteObjectFilled () throws URISyntaxException, JsonProcessingException {

		Quote quote = new Quote();

		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplateMock).build();

		mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL_TO_CONNECT)))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess("{\n" +
								"  \"type\": \"success\",\n" +
								"  \"value\": {\n" +
								"    \"id\": 10,\n" +
								"    \"quote\": \"Really loving Spring Boot, makes stand alone Spring apps easy.\" \n" +
							"  }\n" +
								"}", MediaType.APPLICATION_JSON));
		//WHEN
		quote = consumingRestApplicationHandler.run();

		mockServer.verify();
		assertThat(quote).isNotNull();
		assertThat(quote.getType()).isEqualTo("success");
		assertThat(quote.getValue().getId()).isEqualTo(10L);

	}

	/*@Test
	void callResTemplateKO_getQuoteObjectFilledNull () {

	}*/

}
