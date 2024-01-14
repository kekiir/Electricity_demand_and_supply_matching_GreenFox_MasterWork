package com.gfa.powertrade.registration.services;

import com.gfa.powertrade.consumers.repositories.ConsumerRepository;
import com.gfa.powertrade.registration.exceptions.InvalidPasswordException;
import com.gfa.powertrade.registration.models.RegistrationRequestDTO;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.user.services.UserService;
import com.gfa.powertrade.user.services.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class RegistrationServiceImpUnitTest {

  private RegistrationServiceImp registrationService;
  private ConsumerRepository consumerRepository;
  private SupplierRepository supplierRepository;

  @BeforeEach
  void setUp() {
    consumerRepository = mock(ConsumerRepository.class);
    supplierRepository = mock(SupplierRepository.class);
    registrationService = new RegistrationServiceImp(supplierRepository, consumerRepository);
  }

  @Test
  void saveNewUser() {
  }

  @Test
  void saveSupplier() {
  }

  @Test
  void saveConsumer() {
  }

  @Test
  void validateRegistration() {
  }

  @Test
  void validateUserType() {
  }

  @Test
  void checkUsernameIsAlredyTaken() {

    // Arrange

    // Act

    // Assert
  }

  @Test
  void validatePassword() {
    // Arrange
    RegistrationRequestDTO regWithNullPassword = new RegistrationRequestDTO();
    RegistrationRequestDTO regWithLessThan8CharPassword = new RegistrationRequestDTO(null, "pass", null);
    RegistrationRequestDTO regWithMoreThan7CharPassword = new RegistrationRequestDTO(null, "password", null);

    // Act

    // Assert
    assertThrows(InvalidPasswordException.class, () -> registrationService.validatePassword(regWithNullPassword));
    assertThrows(InvalidPasswordException.class,
      () -> registrationService.validatePassword(regWithLessThan8CharPassword));
    assertDoesNotThrow(() -> registrationService.validatePassword(regWithMoreThan7CharPassword));


  }

  @Test
  void hashPassword() {
  }

  @Test
  void convert() {
  }

}