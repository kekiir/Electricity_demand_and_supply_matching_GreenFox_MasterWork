package com.gfa.powertrade.registration.services;

import com.gfa.powertrade.common.exceptions.InvalidUserTypeException;
import com.gfa.powertrade.consumers.models.Consumer;
import com.gfa.powertrade.consumers.repositories.ConsumerRepository;
import com.gfa.powertrade.registration.exceptions.AlreadyTakenUsernameException;
import com.gfa.powertrade.registration.exceptions.InvalidPasswordException;
import com.gfa.powertrade.registration.models.*;
import com.gfa.powertrade.supplier.models.Supplier;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RegistrationServiceImpUnitTest {

  RegistrationServiceImp registrationServiceImp;
  private RegistrationServiceImp registrationServiceMock;
  private RegistrationServiceImp registrationServiceSpy;
  private ConsumerRepository consumerRepository;
  private SupplierRepository supplierRepository;

  @BeforeEach
  void setUp() {
    registrationServiceImp = new RegistrationServiceImp(null, null);
    consumerRepository = mock(ConsumerRepository.class);
    supplierRepository = mock(SupplierRepository.class);
    registrationServiceMock = new RegistrationServiceImp(supplierRepository, consumerRepository);
    registrationServiceSpy = new RegistrationServiceImp(supplierRepository, consumerRepository);
    registrationServiceSpy = spy(registrationServiceSpy);
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
  void validateRegistration_When_Supplier_RegistrationRequestDTO_is_valid() {
    //Arrange
    RegistrationRequestDTO validRegSupplier = new RegistrationRequestDTO("AccessibleName1", "validpass", "supplier");
    when(supplierRepository.findByUsername("AccessibleName1")).thenReturn(Optional.empty());

    //Act and Assert
    assertDoesNotThrow(() -> registrationServiceMock.validateRegistration(validRegSupplier));
  }

  @Test
  void validateRegistration_When_Consumer_RegistrationRequestDTO_is_valid() {
    //Arrange
    RegistrationRequestDTO validRegConsumer = new RegistrationRequestDTO("AccessibleName2", "validpass", "consumer");
    when(consumerRepository.findByUsername("AccessibleName")).thenReturn(Optional.empty());

    //Act and Assert
    assertDoesNotThrow(() -> registrationServiceMock.validateRegistration(validRegConsumer));
  }

  @Test
  void validateRegistration_When_RegistrationRequestDTO_is_with_invalidPasssword() {
    //Arrange
    RegistrationRequestDTO regWithInvalidPassword = new RegistrationRequestDTO("validName", "invPas", "consumer");
    when(supplierRepository.findByUsername("validName")).thenReturn(Optional.empty());
    when(consumerRepository.findByUsername("validName")).thenReturn(Optional.empty());

    //Act and Assert
    assertThrows(InvalidPasswordException.class,
      () -> registrationServiceMock.validateRegistration(regWithInvalidPassword));
  }

  @Test
  void validateRegistration_When_RegistrationRequestDTO_with_InvalidUserType() {
    //Arrange
    RegistrationRequestDTO regWithInvalidUserType = new RegistrationRequestDTO("validName", "shortpa",
      "invalidUserType");
    when(supplierRepository.findByUsername("validName")).thenReturn(Optional.empty());
    when(consumerRepository.findByUsername("validName")).thenReturn(Optional.empty());

    //Act and Assert
    assertThrows(InvalidUserTypeException.class,
      () -> registrationServiceMock.validateRegistration(regWithInvalidUserType));
  }

  @Test
  void validateRegistration_When_RegistrationRequestDTO_is_with_AlreadyTakenName() {
    //Arrange
    RegistrationRequestDTO regWithInvalidUserName = new RegistrationRequestDTO("AlreadyTakenName", "validPassword",
      "consumer");

    when(supplierRepository.findByUsername("AlreadyTakenName")).thenReturn(Optional.of(new Supplier()));
    when(consumerRepository.findByUsername("AlreadyTakenName")).thenReturn(Optional.of(new Consumer()));
    //Act and Assert

    assertThrows(AlreadyTakenUsernameException.class,
      () -> registrationServiceMock.validateRegistration(regWithInvalidUserName));
  }

  @Test
  void validateUserType_ValidSupplierUserType() {
    // Arrange
    RegistrationRequestDTO regWithCorrectUserType1 = new RegistrationRequestDTO(null, null, "supplier");
    RegistrationRequestDTO regWithCorrectUserType2 = new RegistrationRequestDTO(null, null, "SuPplier");

    //Assert
    assertDoesNotThrow(() -> registrationServiceMock.validateUserType(regWithCorrectUserType1));
    assertDoesNotThrow(() -> registrationServiceMock.validateUserType(regWithCorrectUserType2));
  }

  @Test
  void validateUserType_ValidConsumerUserType() {
    // Arrange
    RegistrationRequestDTO regWithCorrectUserType3 = new RegistrationRequestDTO(null, null, "consumer");
    RegistrationRequestDTO regWithCorrectUserType4 = new RegistrationRequestDTO(null, null, "ConSumer");
    //Assert
    assertDoesNotThrow(() -> registrationServiceMock.validateUserType(regWithCorrectUserType3));
    assertDoesNotThrow(() -> registrationServiceMock.validateUserType(regWithCorrectUserType4));
  }

  @Test
  void validateUserType_InvalidSupplierUserType() {
    // Arrange
    RegistrationRequestDTO regWithIncorrectUserType1 = new RegistrationRequestDTO(null, null, "suplier");
    RegistrationRequestDTO regWithIncorrectUserType2 = new RegistrationRequestDTO(null, null, "SzuPplier");

    //Assert
    assertThrows(InvalidUserTypeException.class,
      () -> registrationServiceMock.validateUserType(regWithIncorrectUserType1));
    assertThrows(InvalidUserTypeException.class,
      () -> registrationServiceMock.validateUserType(regWithIncorrectUserType2));
  }

  @Test
  void validateUserType_InvaliConsumerdUserType() {
    // Arrange;
    RegistrationRequestDTO regWithIncorrectUserType3 = new RegistrationRequestDTO(null, null, "");
    RegistrationRequestDTO regWithIncorrectUserType4 = new RegistrationRequestDTO(null, null, "konszumer");

    //Assert
    assertThrows(InvalidUserTypeException.class,
      () -> registrationServiceMock.validateUserType(regWithIncorrectUserType3));
    assertThrows(InvalidUserTypeException.class,
      () -> registrationServiceMock.validateUserType(regWithIncorrectUserType4));
  }

  @Test
  void checkUsernameIsAlredyTaken_WhenOtherSupplierIsExistingWithThatName() {

    // Arrange
    RegistrationRequestDTO regSupplierWithOccupiedName = new RegistrationRequestDTO("existingSupplierName", null, null);
    when(supplierRepository.findByUsername("existingSupplierName")).thenReturn(Optional.of(new Supplier()));

    // Act

    // Assert
    assertThrows(AlreadyTakenUsernameException.class,
      () -> registrationServiceMock.checkUsernameIsAlredyTaken(regSupplierWithOccupiedName));
  }

  @Test
  void checkUsernameIsAlredyTaken_WhenOtherConsumerIsExistingWithThatName() {

    // Arrange
    RegistrationRequestDTO regConsumerWithOccupiedName = new RegistrationRequestDTO("existingConsumerName", null, null);
    when(consumerRepository.findByUsername("existingConsumerName")).thenReturn(Optional.of(new Consumer()));
    // Act

    // Assert
    assertThrows(AlreadyTakenUsernameException.class,
      () -> registrationServiceMock.checkUsernameIsAlredyTaken(regConsumerWithOccupiedName));
  }

  @Test
  void checkUsernameIsAlredyTaken_WhenSupplierUsernameIsFree() {

    // Arrange
    RegistrationRequestDTO regSupplierWithFreedName = new RegistrationRequestDTO("FreeSupplierName", null, null);
    when(supplierRepository.findByUsername("FreeSupplierName")).thenReturn(Optional.empty());
    when(consumerRepository.findByUsername("FreeSupplierName")).thenReturn(Optional.empty());
    // Act and Assert
    assertDoesNotThrow(() -> registrationServiceMock.checkUsernameIsAlredyTaken(regSupplierWithFreedName));
  }

  @Test
  void checkUsernameIsAlredyTaken_WhenCoonsumerUsernameIsFree() {

    // Arrange
    RegistrationRequestDTO regConsumerWithFreedName = new RegistrationRequestDTO("FreeConsumerName", null, null);
    when(supplierRepository.findByUsername("FreeConsumerName")).thenReturn(Optional.empty());
    when(consumerRepository.findByUsername("FreeConsumerName")).thenReturn(Optional.empty());

    // Act and Assert
    assertDoesNotThrow(() -> registrationServiceMock.checkUsernameIsAlredyTaken(regConsumerWithFreedName));
  }

  @Test
  void testValidatePassword_NullPassword() {
    // Arrange
    RegistrationRequestDTO regWithNullPassword = new RegistrationRequestDTO("ValidName",null,
      UserType.SUPPLIER.toString());

    // Act

    // Assert
    assertThrows(InvalidPasswordException.class, () -> registrationServiceMock.validatePassword(regWithNullPassword));

  }

  @Test
  void testValidatePassword_LessThen8CharPassword() {
    // Arrange
    RegistrationRequestDTO regWithLessThan8CharPassword = new RegistrationRequestDTO(null, "pass", null);

    // Act

    // Assert
    assertThrows(InvalidPasswordException.class,
      () -> registrationServiceMock.validatePassword(regWithLessThan8CharPassword));

  }

  @Test
  void testValidatePassword_MoreThan7CharPassword() {
    // Arrange
    RegistrationRequestDTO regWithMoreThan7CharPassword = new RegistrationRequestDTO(null, "password", null);

    // Act
    registrationServiceMock.validatePassword(regWithMoreThan7CharPassword);

    // Assert
    assertDoesNotThrow(() -> registrationServiceMock.validatePassword(regWithMoreThan7CharPassword));

  }


  @Test
  void testHashPassword_ConsistentHashing() {
    //Arrange
    String password = "testPassword";

    //Act
    String hashedPassword1 = registrationServiceImp.hashPassword(password);
    String hashedPassword2 = registrationServiceImp.hashPassword(password);

    //Assert
    assertNotEquals(hashedPassword1, hashedPassword2);
  }


  @Test
  void convert_NullUser_ShouldReturn_Null() {
    //Assert

    //Act
    RegistrationResponseDTO result = registrationServiceImp.convert(null);

    //Assert
    assertNull(result);
  }

  @Test
  void convert_NonNulUser_ShouldReturn_RegistrationResponseDTO() {
    //Assert
    User user = new User(1, "userName", "userPassword", UserType.SUPPLIER);
    RegistrationResponseDTO expected = new RegistrationResponseDTO(1, "userName", UserType.SUPPLIER);
    RegistrationServiceImp registrationService = new RegistrationServiceImp(null, null);

    //Act
    RegistrationResponseDTO result = registrationService.convert(user);

    //Assert
    assertEquals(expected.getId(), result.getId());
    assertEquals(expected.getUsername(), result.getUsername());
    assertEquals(expected.getUserType(), result.getUserType());

  }

}