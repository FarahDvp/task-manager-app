package com.isamm.tasks.services.Impl;


import javax.naming.AuthenticationException;

import com.isamm.tasks.config.JwtService;
import com.isamm.tasks.dto.AuthReqDTO;
import com.isamm.tasks.dto.AuthResDTO;
import com.isamm.tasks.dto.RegisterRequestDTO;
import com.isamm.tasks.models.Member;
import com.isamm.tasks.repository.MemberRepository;
import com.isamm.tasks.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


// Service class responsible for user authentication and registration.
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	// Autowired password encoder for secure password storage.
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Autowired repository for interacting with the Member database.
	@Autowired
	private MemberRepository repository;

	// Autowired JwtService for generating authentication tokens.
	@Autowired
	private JwtService jwtService;

	// Autowired authentication manager for handling authentication requests.
	@Autowired
	private final AuthenticationManager authenticationManager;

	// Implementation of user registration.
	@Override
	public AuthResDTO register(RegisterRequestDTO request) {
		try {
			// Check if the username already exists.
			if (repository.findByUsername(request.getUserName()).isPresent()) {
				return AuthResDTO.builder()
						.msg("Username already exists")
						.build();
			}

			// Create a new Member object with the user's registration details.
			var member = Member.builder()
					.username(request.getUserName())
					.fullname(request.getFullname())
					.email(request.getEmail())
					.phone(request.getPhone())
					.password(passwordEncoder.encode(request.getPassword()))
					.role(request.getRole())
					.build();

			// Save the new member to the repository.
			var savedUser = repository.save(member);

			// Generate JWT token for the registered user.
			var jwtToken = jwtService.generateToken(member);

			// Return a response indicating successful user creation and the generated token.
			return AuthResDTO.builder()
					.msg("User created successfully")
					.accessToken(jwtToken)
					.build();
		} catch (Exception e) {
			// Handle exceptions during user registration and return an appropriate response.
			return AuthResDTO.builder()
					.msg("User creation failed")
					.build();
		}
	}

	// Implementation of user authentication.
	@Override
	public AuthResDTO authenticate(AuthReqDTO request) {
		try {
			// Use the authentication manager to authenticate the provided username and password.
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getUserName(),
							request.getPassword()
					)
			);
		} catch (BadCredentialsException e) {
			// Handle bad credentials (invalid username or password) and return an appropriate response.
			return AuthResDTO.builder()
					.msg("Invalid username or password")
					.build();
		} catch (Exception e) {
			// Handle other authentication exceptions and return an appropriate response.
			return AuthResDTO.builder()
					.msg("Authentication failed")
					.build();
		}

		// Assuming authentication is successful, retrieve the user details from the repository.
		var user = repository.findByUsername(request.getUserName())
				.orElseThrow(); // Assuming user is present after successful authentication

		// Generate JWT token and refresh token for the authenticated user.
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);

		// Return a response indicating successful login and the generated tokens.
		return AuthResDTO.builder()
				.msg("Login successfully")
				.accessToken(jwtToken)
				.build();
	}
}
