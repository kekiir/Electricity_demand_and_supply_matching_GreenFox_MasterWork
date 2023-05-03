package com.gfa.powertrade.common;

import com.gfa.powertrade.common.models.ErrorDTO;
import com.gfa.powertrade.common.models.ErrorListResponseDTO;
import com.gfa.powertrade.login.exceptions.LoginFailureException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    ErrorListResponseDTO errorListResponseDTO = new ErrorListResponseDTO();
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());
    errorListResponseDTO.setMessage(errors);

    return new ResponseEntity<>(errorListResponseDTO, HttpStatus.BAD_REQUEST);
  }

  @Override
  @ApiResponse(responseCode = "423", description = "JSON is not readable.",
      content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    ErrorDTO errorDTO = new ErrorDTO("JSON is not readable.");

    return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
  }

  @ApiResponse(responseCode = "401", description = "id doesn't belong to logged in player",
      content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
  @ExceptionHandler(LoginFailureException.class)
  public ResponseEntity<ErrorDTO> handle(LoginFailureException ex) {
    ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());
    return ResponseEntity.status(401).body(errorDTO);
  }

  public ResponseEntity<ErrorDTO> handleNotAcceptable(RuntimeException ex) {
    ErrorDTO response = new ErrorDTO(ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
  }

  public ResponseEntity<ErrorDTO> handleConflict(RuntimeException ex) {
    ErrorDTO response = new ErrorDTO(ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  public List<String> collectErrorMessages(MethodArgumentNotValidException ex) {
    return ex.getBindingResult().getFieldErrors().stream().map(
      DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
  }

  public String concatErrorMessages(List<String> errorMessages) {
    if (errorMessages.size() > 1) {

      List<String> wrongFields = collectWrongField(errorMessages);
      String errorMessage = wrongFields.get(0);

      if (wrongFields.size() < 3) {
        errorMessage = errorMessageForTwoWrongFields(wrongFields, errorMessage);
        return errorMessage;
      } else {
        errorMessage = errorMessageForMoreThanTwoWrongFields(wrongFields, errorMessage);
        return errorMessage;
      }
    }
    return errorMessages.get(0);
  }

  private String errorMessageForMoreThanTwoWrongFields(List<String> wrongFields, String errorMessage) {
    for (int i = 1; i < wrongFields.size(); i++) {
      wrongFields.set(i, wrongFields.get(i).toLowerCase());

      if (i < wrongFields.size() - 1) {
        errorMessage = String.join(", ", errorMessage, wrongFields.get(i));
      } else {
        errorMessage = String.join(" and ", errorMessage, wrongFields.get(i));
      }
    }
    errorMessage = String.join(" ", errorMessage, "are required.");
    return errorMessage;
  }

  private String errorMessageForTwoWrongFields(List<String> wrongFields, String errorMessage) {
    for (int i = 1; i < wrongFields.size(); i++) {
      wrongFields.set(i, wrongFields.get(i).toLowerCase());
      errorMessage = String.join(" and ", errorMessage, wrongFields.get(i));
      errorMessage = String.join(" ", errorMessage, "are required.");
    }
    return errorMessage;
  }

  private List<String> collectWrongField(List<String> errorMessages) {
    return errorMessages.stream().map(s -> s.split(" ")).map(s -> s[0]).collect(Collectors.toList());
  }

}
