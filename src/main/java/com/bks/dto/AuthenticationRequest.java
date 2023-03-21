package com.bks.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//default constructor for JSON Parsing
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest implements Serializable {

	private String username;
	private String password;
}