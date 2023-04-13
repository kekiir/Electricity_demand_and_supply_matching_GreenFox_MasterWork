package com.gfa.powertrade.capacity.controllers;

import com.gfa.powertrade.capacity.models.CapacityDTO;
import com.gfa.powertrade.capacity.services.CapacityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/supplier/capacity", produces = "application/json")
public class CapacityController {

  private CapacityService capacityService;

  @PostMapping
  public ResponseEntity<?> createCapacity(@Valid @RequestBody CapacityDTO capacityDto,
      UsernamePasswordAuthenticationToken auth) {
return  null;
  }

}
