package com.gfa.powertrade.demand.controllers;

import com.gfa.powertrade.common.exceptions.ForbiddenActionException;
import com.gfa.powertrade.common.exceptions.InvalidEnergySourceException;
import com.gfa.powertrade.common.models.ErrorDTO;
import com.gfa.powertrade.demand.models.*;
import com.gfa.powertrade.demand.services.DemandService;
import com.gfa.powertrade.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/consumer/demand", produces = "application/json")
public class DemandController {

  private DemandService demandService;

  @GetMapping("/capacities/{idString}")
  public ResponseEntity<?> findDemands( @PathVariable String idString,
      UsernamePasswordAuthenticationToken auth) {
    Integer id;
    try {
      id = Integer.parseInt(idString);
    } catch (NumberFormatException e) {
      return ResponseEntity.status(406).body(new ErrorDTO("Id must be an integer"));
    }
    User user = ((User) auth.getPrincipal());
    try {
      return ResponseEntity.ok().body(demandService.findCapacitiesForDemand(id, user));
    } catch ( ForbiddenActionException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }
  }



  @PostMapping
  public ResponseEntity<?> createDemand(@Valid @RequestBody DemandRequestDTO demandRequestDTO,
      UsernamePasswordAuthenticationToken auth) {
    User user = ((User) auth.getPrincipal());
    try {
      DemandResponseDTO demandResponseDTO = demandService.createDemand(user, demandRequestDTO);
      return ResponseEntity.ok().body(demandResponseDTO);
    } catch (IllegalArgumentException | InvalidEnergySourceException | ForbiddenActionException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }

  }

  @PutMapping("")
  public ResponseEntity<?> updateDemand( @Valid @RequestBody DemandUpdateRequestDTO demandUpdateRequestDTO,
      UsernamePasswordAuthenticationToken auth) {
    User user = ((User) auth.getPrincipal());
    try {
      return ResponseEntity.ok().body(demandService.updateDemand(demandUpdateRequestDTO,user));
    } catch (IllegalArgumentException | InvalidEnergySourceException | ForbiddenActionException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDemand(@PathVariable Integer id, UsernamePasswordAuthenticationToken auth) {
    User user = ((User) auth.getPrincipal());
    try {
      demandService.deleteDemendById(id, user);
      return ResponseEntity.ok().body("Demand with id " + id + " has been deleted successfully.");
    } catch (IllegalArgumentException | InvalidEnergySourceException | ForbiddenActionException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<?> getDemands(UsernamePasswordAuthenticationToken auth) {
    User user = ((User) auth.getPrincipal());
    try {
      return ResponseEntity.ok(demandService.getDemandsByConsumer(user));
    } catch (ForbiddenActionException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }
  }

}

