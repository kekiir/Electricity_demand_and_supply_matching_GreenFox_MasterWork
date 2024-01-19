package com.gfa.powertrade.registration.controllers;

import com.gfa.powertrade.common.exceptions.InvalidUserTypeException;
import com.gfa.powertrade.common.models.ErrorDTO;
import com.gfa.powertrade.registration.exceptions.AlreadyTakenUsernameException;
import com.gfa.powertrade.registration.exceptions.InvalidPasswordException;
import com.gfa.powertrade.registration.models.RegistrationRequestDTO;
import com.gfa.powertrade.registration.models.RegistrationResponseDTO;
import com.gfa.powertrade.registration.services.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Tag(name = "Registration")
@RestController
@AllArgsConstructor
public class RegistrationController {

  private RegistrationService registrationService;

  @Operation(summary = "Registration", description = "Register a new player")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "successful registration",
      content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = RegistrationResponseDTO.class))),
    @ApiResponse(responseCode = "409", description = "username is already taken",
      content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = ErrorDTO.class))),
    @ApiResponse(responseCode = "406", description = "invalid password",
      content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = ErrorDTO.class))),
  })
  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO) {

    try {
      RegistrationResponseDTO createdUser = registrationService.saveNewUser(registrationRequestDTO);
      return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    } catch (AlreadyTakenUsernameException e) {
      return ResponseEntity.status(409).body(new ErrorDTO(e.getMessage()));
    } catch (InvalidPasswordException | InvalidUserTypeException e) {
      return ResponseEntity.status(406).body(new ErrorDTO(e.getMessage()));
    }

  }

}
