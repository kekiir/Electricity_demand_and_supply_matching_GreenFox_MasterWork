package com.gfa.powertrade.capacity.controllers;

import com.gfa.powertrade.capacity.models.CapacityRequestDTO;
import com.gfa.powertrade.capacity.models.CapacityResponseDTO;
import com.gfa.powertrade.capacity.services.CapacityService;
import com.gfa.powertrade.common.exceptions.InvalidEnergySourceException;
import com.gfa.powertrade.common.models.ErrorDTO;
import com.gfa.powertrade.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/supplier/capacity", produces = "application/json")
public class CapacityController {

  private CapacityService capacityService;

  @PostMapping
  public ResponseEntity<?> createCapacity(@Valid @RequestBody CapacityRequestDTO capacityRequestDto,
      UsernamePasswordAuthenticationToken auth) {
    User user = ((User) auth.getPrincipal());
    try {
      CapacityResponseDTO capacityResponseDTO = capacityService.createCapacity(user, capacityRequestDto);
return ResponseEntity.ok().body(capacityResponseDTO);
    } catch (IllegalArgumentException |InvalidEnergySourceException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }

  }

}
