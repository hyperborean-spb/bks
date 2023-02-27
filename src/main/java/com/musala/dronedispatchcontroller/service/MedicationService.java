package com.musala.dronedispatchcontroller.service;

import com.musala.dronedispatchcontroller.domain.Medication;
import com.musala.dronedispatchcontroller.service.dto.MedicationDto;

public interface MedicationService {

	Medication registerMedication(MedicationDto medicationDto);
}