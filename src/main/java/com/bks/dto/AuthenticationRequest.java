package com.bks.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

//default constructor for JSON Parsing
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest implements Serializable {

	private String username;
	private String password;
}