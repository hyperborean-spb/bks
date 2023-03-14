package com.bks.service;

import com.bks.domain.Account;
import com.bks.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

	BigDecimal balanceRaiseLimit = new BigDecimal(2.07);
	private final AccountRepository accountRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<Account> getAccounts() {
		return accountRepository.findAll();
	};

	/*@Scheduled(fixedRateString = "${scheduler.interval}")
	@Async
	@Override
	public void incrementBalance() {
		List<Account> accounts = getAccounts();
		List<Account> copy = accounts.stream().collect(Collectors.toList());
		copy.forEach( account-> {
			if (account.getBalance().compareTo(accounts.get(accounts.indexOf(account)).getBalance().multiply(balanceRaiseLimit)) < 0)
			{
				account.setBalance(account.getBalance().multiply(new BigDecimal(1.1)));
				log.info("client ID - {}, current  balance - {},  changed at - {}", account.getId(), account.getBalance().toPlainString(), formatEventDate());
			}
		});
	}*/

	private  String formatEventDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm");
		return formatter.format(LocalDateTime.now());
	}
}