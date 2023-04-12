package com.gfa.powertrade.common.exceptions;

public class NotValidAcademyIdException extends RuntimeException {

  public static final String MESSAGE = "Not a valid academy id";

  public NotValidAcademyIdException() {
    super(MESSAGE);
  }

}
