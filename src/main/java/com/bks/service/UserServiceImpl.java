package com.bks.service;

import com.bks.domain.Client;
import com.bks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	/*private final AccountRepository accountRepository;
	private final ExceptionMessageCreator messageCreator;
	private final ModelMapper modelMapper;*/

	@Override
	public List<Client> getUsers() {
		return userRepository.findAll();
	};

	// Containing or Like?
	@Override
	public Page<Client> getUsersByName(int page, int size, String name) {
		Pageable pageable = PageRequest.of(page, size);
		return userRepository.findByNameContainingIgnoreCaseOrderByName(pageable, name);
	};

	@Override
	public Page<Client>  getUsersByBirthdate(int page, int size, LocalDate birthdate) {
		Pageable pageable = PageRequest.of(0, 10);
		return userRepository.findByBirthDateAfterOrderByBirthDate(pageable, birthdate);
	};
}