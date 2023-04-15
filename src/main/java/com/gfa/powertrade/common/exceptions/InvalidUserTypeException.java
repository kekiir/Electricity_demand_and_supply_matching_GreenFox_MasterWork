package com.gfa.powertrade.common.exceptions;

public class InvalidUserTypeException extends RuntimeException {

  public static final String MESSAGE = "Invalid usertype.";

  public InvalidUserTypeException() {
    super(MESSAGE);
  }

}
