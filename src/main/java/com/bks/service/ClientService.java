package com.bks.service;

import com.bks.domain.Client;
import com.bks.dto.ClientDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface ClientService {

	Page<Client> getClientsByName(int page, int size, String name);

	Page<Client>  getClientsByBirthdate(int page, int size, LocalDate birthData);

	Client getClientByPhone(String phone);

	Client  getClientByMail(String mail);

	Client registerClient(ClientDto clientDto);

	boolean moneyTransfer(long sourceId, long recipientId, float amount);
}