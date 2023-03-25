package com.bks.service;

import com.bks.domain.Account;
import com.bks.domain.Client;
import com.bks.domain.Mail;
import com.bks.domain.Phone;
import com.bks.dto.ClientDto;
import com.bks.exception.ClientException;
import com.bks.exception.ExceptionMessageCreator;
import com.bks.repository.AccountRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.bks.service.support.ServiceConstants.*;

@Service
//@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final PhoneRepository phoneRepository;
	private final MailRepository mailRepository;
	private final AccountRepository accountRepository;
	private final ExceptionMessageCreator messageCreator;
	private final ModelMapper modelMapper;

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

	/* какой уровень изоляции достаточен?
	* NAN or infinity привели бы к NumberFormatException
	* */
	@Transactional
	@Override
	public boolean moneyTransfer(long senderId, long recipientId, float amount){

		Account sender ;
		Account recipient;

		if(Float.isInfinite(amount))
			throw ClientException.of(messageCreator.createMessage(AMOUNT_IS_INFINITE));

		if(Float.isNaN(amount))
			throw ClientException.of(messageCreator.createMessage(AMOUNT_IS_NAN));

		BigDecimal amountAsBigDecimal = new BigDecimal(amount);

		List<Account> senderAccounts = accountRepository.getAccountByClientId(senderId);
		List<Account> recipientAccounts = accountRepository.getAccountByClientId(recipientId);

		if (senderAccounts.isEmpty()) {
			throw ClientException.of(messageCreator.createMessage(SENDER_ID_ACCOUNT_NOT_FOUND));
		} else {
			sender = senderAccounts.get(0);
		}

		if (recipientAccounts.isEmpty()) {
			throw ClientException.of(messageCreator.createMessage(RECIPIENT_ID_ACCOUNT_NOT_FOUND));
		} else {
			recipient = recipientAccounts.get(0);
		}

		if (sender.getBalance().compareTo(amountAsBigDecimal) >= 0 ) {
			sender.getAccountLock().writeLock().lock();
			recipient.getAccountLock().writeLock().lock();
			sender.setBalance(sender.getBalance().subtract(amountAsBigDecimal));
			sender.getAccountLock().writeLock().unlock();
			recipient.setBalance(recipient.getBalance().add(amountAsBigDecimal));
			recipient.getAccountLock().writeLock().unlock();
			return true;
		} else {
			throw ClientException.of(messageCreator.createMessage(NOT_ENOUGH_FUNDS));
		}
	};
}