package com.bks.service;

import com.bks.domain.Client;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

	Page<Client> getUsersByName(int page, int size, String name);

	Page<Client>  getUsersByBirthdate(int page, int size, LocalDate birthData);

	List<Client> getUsers();
}