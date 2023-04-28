package com.gfa.powertrade.capacity.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.capacity.models.CapacityRequestDTO;
import com.gfa.powertrade.capacity.repositories.CapacityRepository;
import com.gfa.powertrade.common.services.TimeService;
import com.gfa.powertrade.supplier.models.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class CapacityControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  TimeService timeService;

  @Autowired
  CapacityRepository capacityRepository;

  private ObjectMapper objectMapper;
  private Supplier testSupplier;
  private Capacity testCapacity;
  private Authentication authCreateCapacity;
  private LocalDateTime tomorrow;
  private String tomorrow12Am;
  private String tomorrow14Am;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    testSupplier = Supplier.builder().id(1001).username("defaultPlayer").capacityList(new ArrayList<>()).build();
    tomorrow = LocalDateTime.now().plusDays(1).withHour(12).withMinute(0);
    tomorrow12Am = LocalDateTime.now().plusDays(1).withHour(12).withMinute(0)
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00"))
        .toString();
    tomorrow14Am = LocalDateTime.now().plusDays(1).withHour(14).withMinute(0)
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00"))
        .toString();
    authCreateCapacity = new UsernamePasswordAuthenticationToken(testSupplier, null,null);
  }

  @Test
  void createCapacity() throws Exception {
    CapacityRequestDTO capacityRequestDTO =
        new CapacityRequestDTO("WIND", 50d, 45d,tomorrow12Am,tomorrow14Am );

    mockMvc.perform(post("/supplier/capacity")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(capacityRequestDTO))
            .principal(authCreateCapacity))
        .andExpect(jsonPath("$.energySource").value("WIND"))
        .andExpect(jsonPath("$.amountMW").value(50))
        .andExpect(jsonPath("$.available").value(50))
        .andExpect(jsonPath("$.price").value(45))
        .andExpect(jsonPath("$.fromTime").isString())
        .andExpect(jsonPath("$.toTime").isString());


  }

  @Test
  void findDemands() {
  }

  @Test
  void updateCapacity() {
  }

  @Test
  void deleteCapacity() {
  }

  @Test
  void getCapacities() {
  }

}