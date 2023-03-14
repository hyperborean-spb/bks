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

	/*@Override
	public Integer getCapacityForSerial(String serial) {
		return userRepository.getCapacityForSerial(serial);
	}

	@Override
	public List<User> getDroneByState(State state){
		return userRepository.findByState(state);
	};*/

	/*
	* IDLE -> LOADING -> LOADED
	*
	* increase WEIGHT
	* */
	/*@Override
	public  String  loadDroneWithMedications(String droneSerial, List<String> medicationCodes) {
		User d = userRepository.findById(droneSerial).orElseThrow(() -> ClientException.of(messageCreator.createMessage(ServiceConstants.DRONE_SERIAL_NUMBER_NOT_FOUND)));
		medicationCodes.forEach(mC -> {
			Account m = mR.findById(mC).orElseThrow(() -> ClientException.of(messageCreator.createMessage(ServiceConstants.MEDICATION_CODE_NOT_FOUND)));
			m.setDrone(d);
			mR.saveAndFlush(m);
			int newWeight = d.getWeight() + m.getWeight();
			if (newWeight < ServiceConstants.WEIGHT_LIMIT) {
				d.setWeight(newWeight);
				d.setState(State.LOADING);
			}
			if (newWeight == ServiceConstants.WEIGHT_LIMIT) {
				d.setWeight(newWeight);
				d.setState(State.LOADED);
			}
			if (newWeight > ServiceConstants.WEIGHT_LIMIT)
				// we don't set status as LOADED as we might try to load a lighter medication
				ClientException.of(messageCreator.createMessage(ServiceConstants.MEDICATION_OVERLOAD)) ;
		});
		userRepository.saveAndFlush(d);
		return messageCreator.createMessage(ServiceConstants.DRONE_LOADED);
	}

	@Override
	public List<Account> getDroneMedications(String droneSerial) {
		User d = userRepository.findById(droneSerial).orElseThrow(() -> ClientException.of(messageCreator.createMessage(ServiceConstants.DRONE_SERIAL_NUMBER_NOT_FOUND)));
		return mR.findByDrone(d);
	}

	@Override
	public User registerDrone(DroneDto droneDto) {
		if (userRepository.count() == ServiceConstants.DRONE_FLEET_LIMIT)
			ClientException.of(messageCreator.createMessage(ServiceConstants.DRONE_FLEET_SIZE_EXCEEDED));
		User drone = modelMapper.map(droneDto, User.class);
		drone.setWeight(0);
		drone.setState(State.IDLE);
		return userRepository.saveAndFlush(drone);
	}

	*/
}