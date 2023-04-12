package com.gfa.powertrade.common.exceptions;

public class NotEnoughResourceException extends RuntimeException {

  public static final String MESSAGE = "Not enough resource";

  public NotEnoughResourceException() {
    super(MESSAGE);
  }

}