package com.gfa.powertrade.common.exceptions;

public class KingdomNameAlreadyTakenException extends RuntimeException {

  public static final String MESSAGE = "A kingdom with that name already exists";

  public KingdomNameAlreadyTakenException() {
    super(MESSAGE);
  }

}
