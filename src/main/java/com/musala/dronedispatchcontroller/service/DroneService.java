package com.musala.dronedispatchcontroller.service;

import com.musala.dronedispatchcontroller.domain.Drone;
import com.musala.dronedispatchcontroller.domain.Medication;
import com.musala.dronedispatchcontroller.domain.enums.State;
import com.musala.dronedispatchcontroller.service.dto.DroneDto;
import java.util.List;

public interface DroneService {

	List<Drone>  getAllDrones();

	Integer getCapacityForSerial(String serialNumber);

	List<Drone>  getDroneByState(State state);

	String  loadDroneWithMedications(String droneSerial, List<String> medicationCodes);

	List<Medication> getDroneMedications(String droneSerial);

	Drone registerDrone(DroneDto droneDto);

	void logCapacity() ;
}