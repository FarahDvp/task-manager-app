package com.isamm.tasks.controllers;

import com.isamm.tasks.dto.AuthReqDTO;
import com.isamm.tasks.dto.AuthResDTO;
import com.isamm.tasks.dto.RegisterRequestDTO;
import com.isamm.tasks.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Indicates that this class is a Spring REST controller and maps to the "/api/auth" base path.
@RestController
@RequestMapping("/api/auth")
// Automatically generates a constructor with required fields using Lombok.
@RequiredArgsConstructor
public class AuthenticationController {

  // Autowires the AuthenticationService for handling authentication logic.
  @Autowired
  private AuthenticationService service;

  // Endpoint for user registration.
  @PostMapping("/register")
  // Handles registration requests, takes a RegisterRequestDTO in the request body, and may throw an Exception.
  public ResponseEntity<AuthResDTO> register(
          @RequestBody RegisterRequestDTO request
  ) throws Exception {

    // Returns a ResponseEntity with an AuthResDTO containing the result of the registration process.
    return ResponseEntity.ok().body(service.register(request));
  }

  // Endpoint for user authentication.
  @PostMapping("/authenticate")
  // Handles authentication requests, takes an AuthReqDTO in the request body.
  public ResponseEntity<AuthResDTO> authenticate(
          @RequestBody AuthReqDTO request
  ) {
    // Returns a ResponseEntity with an AuthResDTO containing the result of the authentication process.
    return ResponseEntity.ok(service.authenticate(request));
  }
}
