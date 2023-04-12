package com.gfa.powertrade.registration.exceptions;

public class AlreadyTakenUsernameException extends RuntimeException {

  public AlreadyTakenUsernameException(String errorMessage) {
    super(errorMessage);
  }

}

