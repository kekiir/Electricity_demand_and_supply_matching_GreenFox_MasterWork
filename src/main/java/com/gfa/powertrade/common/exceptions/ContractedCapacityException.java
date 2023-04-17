package com.gfa.powertrade.common.exceptions;

public class ContractedCapacityException extends RuntimeException {

  public static final String MESSAGE = "Amount can not be lower than the contracted amount.";

  public ContractedCapacityException() {
    super(MESSAGE);
  }

}
