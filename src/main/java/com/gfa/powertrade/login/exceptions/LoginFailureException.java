package com.gfa.powertrade.login.exceptions;

public class LoginFailureException extends RuntimeException {

  public LoginFailureException() {
    super("Username or password is incorrect.");
  }

}
