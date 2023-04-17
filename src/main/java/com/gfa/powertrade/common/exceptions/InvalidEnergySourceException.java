package com.gfa.powertrade.common.exceptions;

public class InvalidEnergySourceException extends RuntimeException {

  public static final String MESSAGE = "Invalid energy source.";

  public InvalidEnergySourceException() {
    super(MESSAGE);
  }

}
