package com.bks.controllers;

import com.bks.domain.Client;
import com.bks.dto.ClientDto;
import com.bks.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

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

	@GetMapping("/getbyname")
	public ResponseEntity<Page<Client>> getClientByName(@RequestParam int page, @RequestParam int size, @RequestParam  String name) {
		return ResponseEntity.ok().body(clientService.getClientsByName(page,  size,  name));
	}

	@GetMapping("/getbymail/{mail}")
	public ResponseEntity<Client> getClientByMail(@PathVariable String mail) {
		return ResponseEntity.ok().body(clientService.getClientByMail(mail));
	}

	@GetMapping("/getbybirthdate")
	public ResponseEntity<Page<Client>> getClientByBirthdate(@RequestParam int page, @RequestParam int size, @RequestParam  @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate birthdate) {
		return ResponseEntity.ok().body(clientService.getClientsByBirthdate(page,  size, birthdate));
	}


	@PostMapping("/registerclient")
	public ResponseEntity<Client> registerClient(@RequestBody @Valid ClientDto clientDto) {
		return ResponseEntity.ok(clientService.registerClient(clientDto));
	}
}