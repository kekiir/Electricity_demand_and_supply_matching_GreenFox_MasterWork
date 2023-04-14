package com.gfa.powertrade.registration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.powertrade.registration.models.RegistrationRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class RegistrationControllerIT {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void register_should_returnHttp201_when_SupplierregistrationIsSuccessful() throws Exception {
    String username = "newPlayer2";
    RegistrationRequestDTO reg = new RegistrationRequestDTO(username, "password", "supplier");

    mockMvc.perform(post("/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(201))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.username", is(username)))
        .andExpect(jsonPath("$.password").doesNotExist())
        .andExpect(jsonPath("$.userType", is("SUPPLIER")));
  }

  @Test
  public void register_should_returnHttp201_when_ConsumerregistrationIsSuccessful() throws Exception {
    String username = "newPlayer4";
    RegistrationRequestDTO reg = new RegistrationRequestDTO(username, "password", "consumer");

    mockMvc.perform(post("/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(201))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.username", is(username)))
        .andExpect(jsonPath("$.password").doesNotExist())
        .andExpect(jsonPath("$.userType", is("CONSUMER")));
  }

  @Test
  public void register_should_returnHttp409_when_usernameIsAlreadyTaken() throws Exception {
    RegistrationRequestDTO reg = new RegistrationRequestDTO("defaultPlayer", "password", "supplier");

    mockMvc.perform(post("/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(409))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Username is already taken.")));
  }

  @Test
  public void register_should_returnHttp406_when_passwordIsLess_than8Character() throws Exception {
    String username = "newPlayer7";
    String password = "pass";

    RegistrationRequestDTO reg = new RegistrationRequestDTO(username, password, "supplier");

    mockMvc.perform(post("/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(406))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Password must be at least 8 characters.")));
  }

  @Test
  public void register_should_returnHttp400_when_usernameIsMissing() throws Exception {
    RegistrationRequestDTO reg = new RegistrationRequestDTO(null, "password", "supplier");

    mockMvc.perform(post("/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(400))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message[0]", is("Username is required.")));
  }

  @Test
  public void register_should_returnHttp400_when_passwordIsMissing() throws Exception {
    String username = "newPlayer8";

    RegistrationRequestDTO reg = new RegistrationRequestDTO(username, null, "suplier");

    mockMvc.perform(post("/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(400))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message[0]", is("Password is required.")));
  }

  @Test
  public void register_should_returnHttp400_when_usertypeIsMissing() throws Exception {
    String username = "newPlayer8";

    RegistrationRequestDTO reg = new RegistrationRequestDTO(username, "pasword", "");

    mockMvc.perform(post("/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(400))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message[0]", is("Usertype is required.")));
  }

  @Test
  public void register_should_returnHttp400_when_usernameAndPasswordAreMissing() throws Exception {
    RegistrationRequestDTO reg = new RegistrationRequestDTO(null, null, "supplier");

    mockMvc.perform(post("/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(400))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message",
            containsInAnyOrder("Password is required.", "Username is required.")));
  }

  @Test
  public void register_should_returnHttp400_when_usernamePasswordAndUsertypeAreMissing() throws Exception {
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
  public void register_should_returnHttp400_when_emptyRequestWasSent() throws Exception {
    RegistrationRequestDTO reg = null;

    mockMvc.perform(post("/register")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
        .andExpect(status().is(400))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("JSON is not readable.")));
  }

}