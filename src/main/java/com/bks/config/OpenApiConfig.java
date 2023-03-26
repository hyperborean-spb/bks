package com.bks.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
	info = @Info(
		title = "Тестовое задание БрокерКредитСервис",
		description = "БКС", version = "1.0.0",
		contact = @Contact(
			name = "Андрей Михайлович Степанов",
			email = "andreymstepanov@gmail.com",
			url = "https://github.com/hyperborean72/bks"
			)
		)
	)
@SecurityScheme(
	name = "JWT",
	type = SecuritySchemeType.HTTP,
	bearerFormat = "JWT",
	scheme = "bearer"
)
public class OpenApiConfig {

}