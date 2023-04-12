package com.gfa.powertrade.common.exceptions;

public class ForbiddenActionException extends RuntimeException {

  public static final String MESSAGE = "Forbidden action";

  public ForbiddenActionException() {
    super(MESSAGE);
  }

}
