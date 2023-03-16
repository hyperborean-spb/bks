package com.bks.controllers;

import com.bks.domain.Client;
import com.bks.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

	private final ClientService clientService;


	@GetMapping("/getbyphone/{phone}")
	public ResponseEntity<Client> getClientByPhone(@PathVariable String phone) {
		return ResponseEntity.ok().body(clientService.getClientByPhone(phone));
	}

	@GetMapping("/getbymail/{mail}")
	public ResponseEntity<Client> getClientByMail(@PathVariable String mail) {
		return ResponseEntity.ok().body(clientService.getClientByMail(mail));
	}

	@GetMapping("/getbybirthtdate")
	public ResponseEntity<Page<Client>> getClientByBirthdate(@RequestParam int page, @RequestParam int size, @RequestParam  LocalDate birthdate) {
		return ResponseEntity.ok().body(clientService.getClientsByBirthdate(page,  size, birthdate));
	}
}