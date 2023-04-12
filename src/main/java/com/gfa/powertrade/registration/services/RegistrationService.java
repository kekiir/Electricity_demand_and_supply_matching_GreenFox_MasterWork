package com.gfa.powertrade.registration.services;

import com.gfa.powertrade.common.exceptions.InvalidUserTypeException;
import com.gfa.powertrade.registration.exceptions.*;
import com.gfa.powertrade.registration.models.RegistrationRequestDTO;
import com.gfa.powertrade.registration.models.RegistrationResponseDTO;

public interface RegistrationService {

  RegistrationResponseDTO saveNewUser(RegistrationRequestDTO registrationRequestDTO)
      throws AlreadyTakenUsernameException, InvalidPasswordException;

  void validateRegistration(RegistrationRequestDTO reg) throws AlreadyTakenUsernameException,
      InvalidPasswordException, InvalidUserTypeException;

  public String hashPassword(String password);

}
