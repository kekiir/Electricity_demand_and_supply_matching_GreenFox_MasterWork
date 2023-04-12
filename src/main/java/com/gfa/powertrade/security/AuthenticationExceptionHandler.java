package com.gfa.powertrade.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.powertrade.common.models.ErrorDTO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authenticationException)
      throws IOException {
    String jwt = request.getHeader("Authorization");
    String errorMessage = jwt == null ? "No authentication token is provided!" : "Authentication token is invalid!";
    ErrorDTO error = new ErrorDTO(errorMessage);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    ObjectMapper objectMapper = new ObjectMapper();
    String errorJson = objectMapper.writeValueAsString(error);
    response.getOutputStream().println(errorJson);
  }

}