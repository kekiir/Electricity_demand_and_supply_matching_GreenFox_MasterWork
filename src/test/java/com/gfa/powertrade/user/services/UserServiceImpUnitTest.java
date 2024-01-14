package com.gfa.powertrade.user.services;

import com.gfa.powertrade.common.exceptions.ForbiddenActionException;
import com.gfa.powertrade.consumers.models.Consumer;
import com.gfa.powertrade.consumers.repositories.ConsumerRepository;
import com.gfa.powertrade.login.exceptions.LoginFailureException;
import com.gfa.powertrade.registration.models.UserType;
import com.gfa.powertrade.supplier.models.Supplier;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImpUnitTest {

  private UserServiceImp userService;
  private ConsumerRepository consumerRepository;
  private SupplierRepository supplierRepository;

  @BeforeEach
  void setUp() {
    consumerRepository = mock(ConsumerRepository.class);
    supplierRepository = mock(SupplierRepository.class);
    userService = new UserServiceImp(null, null);
  }

  @Test
  void testFindByUsername_ValidSupplier() {
    // Arrange
    when(supplierRepository.findByUsername("validSupplier")).thenReturn(Optional.of(new Supplier()));
    userService.setSupplierRepository(supplierRepository);

    // Act
    Optional<User> result = userService.findByUsername("validSupplier", UserType.SUPPLIER.toString());

    // Assert
    assertTrue(result.isPresent());
    assertEquals(UserType.SUPPLIER, result.get().getUserType());
  }

  @Test
  void testFindByUsername_ValidConsumer() {
    // Arrange
    when(consumerRepository.findByUsername("validConsumer")).thenReturn(Optional.of(new Consumer()));
    userService.setConsumerRepository(consumerRepository);

    // Act
    Optional<User> result = userService.findByUsername("validConsumer", UserType.CONSUMER.toString());

    // Assert
    assertTrue(result.isPresent());
    assertEquals(UserType.CONSUMER, result.get().getUserType());
  }

  // Add more test methods for other scenarios...

  @Test
  void testValidateSuppliertype_ValidSupplier() {
    // Arrange
    Supplier supplier = new Supplier();

    // Act and Assert
    assertDoesNotThrow(() -> userService.validateSuppliertype(supplier));
  }

  @Test
  void testValidateConsumertype_ValidConsumer() {
    // Arrange
    Consumer consumer = new Consumer();

    // Act and Assert
    assertDoesNotThrow(() -> userService.validateConsumertype(consumer));
  }

  @Test
  void testFindByUsername_NoUserFound() {
    // Arrange
    when(consumerRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

    userService.setConsumerRepository(consumerRepository);

    // Act and Assert
    assertThrows(LoginFailureException.class,
      () -> userService.findByUsername("nonexistentUser", UserType.CONSUMER.toString()));
  }

  @Test
  void testValidateSuppliertype_InvalidSupplierType() {
    // Arrange
    Consumer consumer = new Consumer();

    // Act and Assert
    assertThrows(ForbiddenActionException.class, () -> userService.validateSuppliertype(consumer));
  }

  @Test
  void testValidateConsumertype_InvalidConsumerType() {
    // Arrange
    Supplier supplier = new Supplier();

    // Act and Assert
    assertThrows(ForbiddenActionException.class, () -> userService.validateConsumertype(supplier));
  }

}