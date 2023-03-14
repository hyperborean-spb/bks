package com.bks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Client {

	@Id
	private Long id;

	@Column (nullable = false)
	private String name;

	@Column (nullable = false, name = "DATE_OF_BIRTH")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate birthDate;

	@Column (nullable = false)
	@Size (min = 8, max = 500, message = "Длина пароля вне диапазона [8 - 500]")
	private String password;
}