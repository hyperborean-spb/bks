package com.musala.dronedispatchcontroller.service;

import com.musala.dronedispatchcontroller.domain.Medication;
import com.musala.dronedispatchcontroller.repository.MedicationRepository;
import com.musala.dronedispatchcontroller.service.dto.MedicationDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicationServiceImpl  implements MedicationService{

	private final MedicationRepository mR;
	private final ModelMapper modelMapper;

	@Override
	public Medication registerMedication(MedicationDto medicationDto) {
		Medication medication = modelMapper.map(medicationDto, Medication.class);
		return mR.saveAndFlush(medication);
	}
}