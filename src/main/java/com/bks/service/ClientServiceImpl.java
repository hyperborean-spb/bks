package com.bks.service;

import com.bks.domain.Client;
import com.bks.domain.Mail;
import com.bks.domain.Phone;
import com.bks.dto.ClientDto;
import com.bks.exception.ClientException;
import com.bks.exception.ExceptionMessageCreator;
import com.bks.repository.MailRepository;
import com.bks.repository.PhoneRepository;
import com.bks.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static com.bks.service.support.ServiceConstants.MAIL_NOT_FOUND;
import static com.bks.service.support.ServiceConstants.PHONE_NOT_FOUND;

@Service
//@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final PhoneRepository phoneRepository;
	private final MailRepository mailRepository;
	private final ExceptionMessageCreator messageCreator;
	private final ModelMapper modelMapper;


	// Containing or Like?
	@Override
	public Page<Client> getClientsByName(int page, int size, String name) {
		Pageable pageable = PageRequest.of(page, size);
		return clientRepository.findByNameContainingIgnoreCaseOrderByName(pageable, name);
	};

	@Override
	public Page<Client>  getClientsByBirthdate(int page, int size, LocalDate birthdate) {
		Pageable pageable = PageRequest.of(page, size);
		return clientRepository.findByBirthdateAfterOrderByBirthdate(pageable, birthdate);
	};

	@Override
	public Client  getClientByPhone(String phone){
		Phone p = phoneRepository.getFirstByPhone(phone).orElseThrow(() -> ClientException.of(messageCreator.createMessage(PHONE_NOT_FOUND)));
		return p.getClient();
	};

	@Override
	public Client getClientByMail(String mail) {
		Mail m = mailRepository.getFirstByMail(mail).orElseThrow(() -> ClientException.of(messageCreator.createMessage(MAIL_NOT_FOUND)));
		return m.getClient();
	}


	@Override
	public Client registerClient(ClientDto clientDto) {
		Client client = modelMapper.map(clientDto, Client.class);
		return clientRepository.saveAndFlush(client);
	}
}