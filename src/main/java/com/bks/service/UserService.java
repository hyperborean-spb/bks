package com.bks.service;

import com.bks.domain.User;
import com.bks.domain.Account;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

	List<User>  getUsersByName(String name);

	List<User>  getUsersByBirthdate(LocalDate birthData);

	List<User> getUsers();
}