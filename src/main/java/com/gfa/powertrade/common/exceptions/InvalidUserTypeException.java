package com.gfa.powertrade.common.exceptions;

public class InvalidUserTypeException extends RuntimeException {

  public InvalidUserTypeException(String errorMessage) {
    super(errorMessage);
  }
}
