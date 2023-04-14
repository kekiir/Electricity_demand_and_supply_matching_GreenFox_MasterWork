package com.gfa.powertrade.registration.controllers;

import com.gfa.powertrade.common.exceptions.InvalidUserTypeException;
import com.gfa.powertrade.common.models.ErrorDTO;
import com.gfa.powertrade.registration.exceptions.AlreadyTakenUsernameException;
import com.gfa.powertrade.registration.exceptions.InvalidPasswordException;
import com.gfa.powertrade.registration.models.RegistrationRequestDTO;
import com.gfa.powertrade.registration.models.RegistrationResponseDTO;
import com.gfa.powertrade.registration.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RegistrationController {

  private RegistrationService registrationService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO) {

    try {
      RegistrationResponseDTO createdUser = registrationService.saveNewUser(
          registrationRequestDTO);
      return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    } catch (AlreadyTakenUsernameException e) {
      return ResponseEntity.status(409).body(new ErrorDTO (e.getMessage()));
    } catch (InvalidPasswordException | InvalidUserTypeException e) {
      return ResponseEntity.status(406).body(new ErrorDTO (e.getMessage()));
    }

  }

}
