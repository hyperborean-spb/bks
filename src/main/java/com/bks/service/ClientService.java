package com.bks.service;

import com.bks.domain.Client;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {

	Page<Client> getClientsByName(int page, int size, String name);

	Page<Client>  getClientsByBirthdate(int page, int size, LocalDate birthData);

	Client getClientByPhone(String phone);

	Client  getClientByMail(String mail);
}