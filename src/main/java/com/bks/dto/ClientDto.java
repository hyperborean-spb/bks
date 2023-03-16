package com.bks.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ClientDto {

	private Long id;

	private String name;

	@JsonFormat(pattern = "dd.MM.yyyy")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate birthdate;

	@Size(min = 8, max = 500, message = "Длина пароля вне диапазона [8 - 500]")
	private String password;
}