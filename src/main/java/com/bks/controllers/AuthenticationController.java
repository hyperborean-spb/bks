package com.bks.controllers;

import com.bks.dto.AuthenticationRequest;
import com.bks.dto.AuthenticationResponse;
import com.bks.exception.ClientException;
import com.bks.exception.ExceptionMessageCreator;
import com.bks.service.support.JwtUtil;
import com.bks.service.BksUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.bks.service.support.ServiceConstants.INCORRECT_MAIL_OR_PASS;


@RequiredArgsConstructor
@RestController
@Tag(name="Аутентификация и формирование JWT")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final ExceptionMessageCreator messageCreator;
	private final JwtUtil jwtTokenUtil;
	private final BksUserDetailsService userDetailsService;

	@PostMapping("/authenticate")
	@Operation(summary = "Аутентификация и формирование JWT в теле отклика")
	@SecurityRequirement(name = "JWT")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws ClientException {

		try {
			/*способ аутентификации определен в WebSecurityConfig.configure(AuthenticationManagerBuilder auth) */
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(), authenticationRequest.getPassword())
				);
		}
		catch (BadCredentialsException e) {
			throw ClientException.of(messageCreator.createMessage(INCORRECT_MAIL_OR_PASS));
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getMail());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	@GetMapping({ "/hello" })
	@Operation(summary = "изначально защищенная тестовая точка для проверки корректности работы JWT")
	@SecurityRequirement(name = "JWT")
	public String firstPage() {
		return "Hello World";
	}
}