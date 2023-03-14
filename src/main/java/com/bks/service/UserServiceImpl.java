package com.bks.service;

import com.bks.domain.User;
import com.bks.exception.ExceptionMessageCreator;
import com.bks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
	public List<User> getUsers() {
		return userRepository.findAll();
	};

	// Containing or Like?
	@Override
	public List<User>  getUsersByName(String name) {
		return userRepository.findByNameContainingIgnoreCaseOrderByName(name);
	};

	@Override
	public List<User>  getUsersByBirthdate(LocalDate birthData) {
		return userRepository.findByBirthDateAfterOrderByBirthDate(birthData);
	};
}