package com.gfa.powertrade.common.exceptions;

public class InvalidBuildingTypeException extends RuntimeException {

  public static final String MESSAGE = "Invalid building type";

  public InvalidBuildingTypeException() {
    super(MESSAGE);
  }

}
