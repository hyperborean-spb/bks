package com.bks.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

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
public class OpenApiConfig {

}