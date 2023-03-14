package com.bks.service;

import com.bks.service.dto.MedicationDto;
import com.bks.domain.Account;

import java.util.List;

public interface AccountService {

	List<Account> getAccounts();

	//void incrementBalance();
}