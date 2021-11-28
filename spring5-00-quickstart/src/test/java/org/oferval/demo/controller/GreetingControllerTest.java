package org.oferval.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

  @Autowired
  private MockMvc mockMvc;


  @Test
  void greetingWithoutAnyParamReturnDefaultMsg() throws Exception {

    mockMvc.perform(get("/greeting"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").value("Hello World!"));
  }


  @Test
  void greetingWithParamName() throws Exception {

    mockMvc.perform(get("/greeting?name={name}","Oscar"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").value("Hello Oscar!"));
  }


  @Test
  void notFoundBecauseErrorOnURL() throws Exception {

    mockMvc.perform(get("/greetin?name={name}","Oscar"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}