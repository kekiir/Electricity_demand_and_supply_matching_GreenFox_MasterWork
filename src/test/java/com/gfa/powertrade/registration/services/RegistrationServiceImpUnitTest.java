package com.gfa.powertrade.registration.services;

import com.gfa.powertrade.consumers.models.Consumer;
import com.gfa.powertrade.consumers.repositories.ConsumerRepository;
import com.gfa.powertrade.registration.exceptions.AlreadyTakenUsernameException;
import com.gfa.powertrade.registration.exceptions.InvalidPasswordException;
import com.gfa.powertrade.registration.models.RegistrationRequestDTO;
import com.gfa.powertrade.supplier.models.Supplier;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.user.services.UserService;
import com.gfa.powertrade.user.services.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
  void checkUsernameIsAlredyTaken_WhenOtherSupplierIsExistingWithThatName() {

    // Arrange
    RegistrationRequestDTO regSupplierWithOccupiedName =
      new RegistrationRequestDTO("existingSupplierName", null, null);
    when(supplierRepository.findByUsername("existingSupplierName")).thenReturn(Optional.of(new Supplier()));

    // Act

    // Assert
    assertThrows(AlreadyTakenUsernameException.class,
      () -> registrationService.checkUsernameIsAlredyTaken(regSupplierWithOccupiedName));
  }

  @Test
  void checkUsernameIsAlredyTaken_WhenOtherConsumerIsExistingWithThatName() {

    // Arrange
    RegistrationRequestDTO regConsumerWithOccupiedName =
      new RegistrationRequestDTO("existingConsumerName", null, null);
    when(consumerRepository.findByUsername("existingConsumerName")).thenReturn(Optional.of(new Consumer()));
    // Act

    // Assert
    assertThrows(AlreadyTakenUsernameException.class,
      () -> registrationService.checkUsernameIsAlredyTaken(regConsumerWithOccupiedName));
  }

  @Test
  void checkUsernameIsAlredyTaken_WhenUsernameIsFree() {

    // Arrange
    RegistrationRequestDTO regSupplierWithFreedName =
      new RegistrationRequestDTO("FreeSupplierName", null, null);
    RegistrationRequestDTO regConsumerWithFreedName =
      new RegistrationRequestDTO("FreeConsumerName", null, null);

    when(supplierRepository.findByUsername("FreeSupplierName")).thenReturn(Optional.empty());
    when(supplierRepository.findByUsername("FreeConsumerName")).thenReturn(Optional.empty());

    // Act

    // Assert
    assertDoesNotThrow(() -> registrationService.checkUsernameIsAlredyTaken(regSupplierWithFreedName));
    assertDoesNotThrow(() -> registrationService.checkUsernameIsAlredyTaken(regConsumerWithFreedName));
  }

  @Test
  void testValidatePassword_NullPassword() {
    // Arrange
    RegistrationRequestDTO regWithNullPassword = new RegistrationRequestDTO();

    // Act

    // Assert
    assertThrows(InvalidPasswordException.class, () -> registrationService.validatePassword(regWithNullPassword));

  }

  @Test
  void testValidatePassword_NullPassword_LessThen8CharPassword() {
    // Arrange
    RegistrationRequestDTO regWithLessThan8CharPassword = new RegistrationRequestDTO(null, "pass", null);

    // Act

    // Assert
    assertThrows(InvalidPasswordException.class,
      () -> registrationService.validatePassword(regWithLessThan8CharPassword));

  }

  @Test
  void testValidatePassword_NullPassword_MoreThan7CharPassword() {
    // Arrange
    RegistrationRequestDTO regWithMoreThan7CharPassword = new RegistrationRequestDTO(null, "password", null);

    // Act

    // Assert
    assertDoesNotThrow(() -> registrationService.validatePassword(regWithMoreThan7CharPassword));

  }

  @Test
  void hashPassword() {
  }

  @Test
  void convert() {
  }

}