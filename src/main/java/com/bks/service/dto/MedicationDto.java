package com.bks.service.dto;

import com.bks.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class MedicationDto {

	@Pattern(
		regexp = "[A-Z0-9_]+",
		message = "only upper case letters, underscore and numbers allowed"
	)
	private String code;

	@Pattern(
		regexp = "[a-zA-Z_0-9-]+",
		message = "only letters, numbers, underscore and hyphen allowed"
	)
	private String name;

	private String picture;

	@Positive
	private Integer weight;

	private User drone;
}