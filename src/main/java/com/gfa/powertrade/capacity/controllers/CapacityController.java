package com.gfa.powertrade.capacity.controllers;

import com.gfa.powertrade.capacity.models.*;
import com.gfa.powertrade.capacity.services.CapacityService;
import com.gfa.powertrade.common.exceptions.*;
import com.gfa.powertrade.common.models.ErrorDTO;
import com.gfa.powertrade.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/supplier/capacity", produces = "application/json")
public class CapacityController {

  private CapacityService capacityService;

  @GetMapping("/demands/{idString}")
  public ResponseEntity<?> findDemands(@PathVariable String idString,
      UsernamePasswordAuthenticationToken auth) {
    Integer id;
    try {
      id = Integer.parseInt(idString);
    } catch (NumberFormatException e) {
      return ResponseEntity.status(406).body(new ErrorDTO("Id must be an integer"));
    }
    User user = ((User) auth.getPrincipal());
    try {
      return ResponseEntity.ok().body(capacityService.findDemandsForCapacity(id, user));
    } catch (ForbiddenActionException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }
  }

  @PostMapping
  public ResponseEntity<?> createCapacity(@Valid @RequestBody CapacityRequestDTO capacityRequestDto,
      UsernamePasswordAuthenticationToken auth) {
    User user = ((User) auth.getPrincipal());
    try {
      CapacityResponseDTO capacityResponseDTO = capacityService.createCapacity(user, capacityRequestDto);
      return ResponseEntity.ok().body(capacityResponseDTO);
    } catch (IllegalArgumentException | InvalidEnergySourceException | ForbiddenActionException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }

  }

  @PutMapping("")
  public ResponseEntity<?> updateCapacity(@Valid @RequestBody CapacityUpdateRequestDTO capacityUpdateRequestDTO,
      UsernamePasswordAuthenticationToken auth) {
    User user = ((User) auth.getPrincipal());
    try {
      return ResponseEntity.ok().body(capacityService.updateCapacity(capacityUpdateRequestDTO, user));
    } catch (IdNotFoundException | IllegalArgumentException | ForbiddenActionException
             | ContractedCapacityException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }

  }

  @DeleteMapping("/{idString}")
  public ResponseEntity<?> deleteCapacity(@PathVariable @NotBlank(message = "Id is required.") String idString,
      UsernamePasswordAuthenticationToken auth) {
    Integer id;
    try {
      id = Integer.parseInt(idString);
    } catch (NumberFormatException e) {
      return ResponseEntity.status(406).body(new ErrorDTO("Id must be an integer"));
    }
    User user = ((User) auth.getPrincipal());
    try {
      capacityService.deleteCapacityById(id, user);
      return ResponseEntity.ok().body("Capacity with id " + id + " has been deleted successfully.");
    } catch (IdNotFoundException | IllegalArgumentException | ForbiddenActionException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }
  }

  @GetMapping("")
  public ResponseEntity<?> getCapacities(UsernamePasswordAuthenticationToken auth) {
    User user = ((User) auth.getPrincipal());
    try {
      return ResponseEntity.ok(capacityService.getCapacitesBySupplier(user));
    } catch (ForbiddenActionException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }
  }

}
