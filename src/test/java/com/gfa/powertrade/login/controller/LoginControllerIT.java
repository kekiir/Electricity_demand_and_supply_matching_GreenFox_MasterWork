package com.gfa.powertrade.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.powertrade.login.models.LoginDTO;
import com.gfa.powertrade.registration.models.RegistrationRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerIT {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void login_should_returnHttp200_when_login_is_successful() throws Exception {
    LoginDTO loginDTO = new LoginDTO("defaultPlayer", "password", "supplier");
    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDTO)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("ok")))
        .andExpect(jsonPath("$.token").isString())
        .andExpect(jsonPath("$.token").isNotEmpty());
  }

  @Test
  void login_should_returnHttp400_when_only_password_sent() throws Exception  {
    LoginDTO loginDTO = new LoginDTO("", "password", "supplier");
    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message[0]", is("Username is required.")));
  }

  @Test
  void login_should_returnHttp400_when_only_username_sent() throws Exception  {
    LoginDTO loginDTO = new LoginDTO("Username", "","supplier");
    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDTO)))
        .andExpect(status().is(400))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message[0]", is("Password is required.")));
  }

  @Test
  public void login_should_returnHttp400_when_usertypeIsMissing() throws Exception {
    String username = "newPlayer8";

    RegistrationRequestDTO reg = new RegistrationRequestDTO(username, "pasword", "");

    mockMvc.perform(post("/login")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(400))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message[0]", is("Usertype is required.")));
  }

  @Test
  public void login_should_returnHttp400_when_usernameAndPasswordAreMissing() throws Exception {
    RegistrationRequestDTO reg = new RegistrationRequestDTO(null, null, "supplier");

    mockMvc.perform(post("/login")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(400))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message",
            containsInAnyOrder("Password is required.", "Username is required.")));
  }


  @Test
  public void login_should_returnHttp400_when_usernamePasswordAndUsertypeAreMissing() throws Exception {
    RegistrationRequestDTO reg = new RegistrationRequestDTO("", "", "");

    mockMvc.perform(post("/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(400))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message",
            containsInAnyOrder("Password is required.", "Usertype is required.", "Username is required.")));
  }


  @Test
  void login_should_returnHttp401_when_password_is_incorrect() throws Exception  {
    LoginDTO loginDTO = new LoginDTO("defaultPlayer", "password2", "supplier");
    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDTO)))
        .andExpect(status().is(401))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Username or password is incorrect.")));
  }

  @Test
  void login_should_returnHttp401_when_username_is_incorrect() throws Exception  {
    LoginDTO loginDTO = new LoginDTO("defaultPlayer2", "password", "supplier");
    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDTO)))
        .andExpect(status().is(401))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Username or password is incorrect.")));
  }


  @Test
  public void login_should_returnHttp400_when_emptyRequestWasSent() throws Exception {
    RegistrationRequestDTO reg = null;

    mockMvc.perform(post("/login")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(400))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("JSON is not readable.")));
  }


}