package com.isamm.tasks.services;


import com.isamm.tasks.dto.AuthReqDTO;
import com.isamm.tasks.dto.AuthResDTO;
import com.isamm.tasks.dto.RegisterRequestDTO;

// Interface defining the contract for authentication services.
public interface AuthenticationService {

	// Register a new user based on the provided registration request.
	// Throws an exception if registration fails.
	AuthResDTO register(RegisterRequestDTO request) throws Exception;

	// Authenticate a user based on the provided authentication request.
	AuthResDTO authenticate(AuthReqDTO request);

}

