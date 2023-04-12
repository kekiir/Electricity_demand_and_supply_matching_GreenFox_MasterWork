package com.gfa.powertrade.common.exceptions;

public class LowTownhallLevelException extends RuntimeException {

  public static final String MESSAGE = "Cannot build buildings with higher level than the Townhall";

  public LowTownhallLevelException() {
    super(MESSAGE);
  }

}
