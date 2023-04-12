package com.gfa.powertrade.login.controller;

import com.gfa.powertrade.login.models.LoginDTO;
import com.gfa.powertrade.login.service.LoginServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class LoginController {

  private LoginServiceImpl loginService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginDTO login) {
    return ResponseEntity.status(200).body(loginService.login(login));
  }

}
