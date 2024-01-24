package com.gfa.powertrade.login.service;

import com.gfa.powertrade.common.exceptions.InvalidUserTypeException;
import com.gfa.powertrade.login.models.LoginDTO;
import com.gfa.powertrade.security.JwtSystemKeys;
import com.gfa.powertrade.user.models.User;
import com.gfa.powertrade.user.services.UserService;
import com.gfa.powertrade.user.services.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
class LoginServiceImplUnitTest {

  LoginService loginService;
  LoginService loginServiceSpy;
  UserService userServiceMock;
  JwtSystemKeys jwtSystemKeysMock;
  LoginDTO validLogin;

  @BeforeEach
  void setUp() {

    userServiceMock = mock(UserServiceImp.class);
    jwtSystemKeysMock = mock(JwtSystemKeys.class);
    loginService = new LoginServiceImpl(userServiceMock, jwtSystemKeysMock);
    loginServiceSpy = spy(loginService);
    validLogin = new LoginDTO("validName", "validPassword", "Supplier");

  }

  @Test
  void login() {
    //Arrange
    doNothing().when(loginServiceSpy).validateUsertype(any(LoginDTO.class));
    when(userServiceMock.findByUsername(anyString(), anyString())).thenReturn(Optional.of(new User()));
    try (MockedStatic<BCrypt> bCryptMockedStatic = Mockito.mockStatic(BCrypt.class)) {
      bCryptMockedStatic.when(() -> BCrypt.checkpw(anyString(), any())).thenReturn(true);

      boolean bCrpytChpw = BCrypt.checkpw("pass", "pass");
      assertTrue(bCrpytChpw);
    }

    //Act


    //Assert

  }

  @Test
  void createToken() {
  }

  @Test
  void validateUsertype_with_valid_Usertye() {
    //Arrange

    //Act and assert
    assertDoesNotThrow(() -> loginService.validateUsertype(validLogin));
  }

  @Test
  void validateUsertype_with_inValid_Usertye() {
    //Arrange
    LoginDTO inValidLogin = new LoginDTO("validName", "validPassword", "invalidUsertype");

    //Act and assert
    assertThrows(InvalidUserTypeException.class, () -> loginService.validateUsertype(inValidLogin));
  }

}