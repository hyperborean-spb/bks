package com.musala.dronedispatchcontroller.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponseDto {
	private final String message;

/*	public static ErrorResponseDto of(String message) {
		return new ErrorResponseDto(message);
	}*/
}