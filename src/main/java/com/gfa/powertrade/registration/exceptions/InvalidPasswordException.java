package com.gfa.powertrade.registration.exceptions;

public class InvalidPasswordException extends RuntimeException {

  public InvalidPasswordException(String errorMessage) {
    super(errorMessage);
  }
}
