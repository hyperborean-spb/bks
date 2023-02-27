package com.musala.dronedispatchcontroller.repository;

import com.musala.dronedispatchcontroller.domain.Drone;
import com.musala.dronedispatchcontroller.domain.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  MedicationRepository extends JpaRepository<Medication, String> {

	List<Medication>  findByDrone(Drone drone);
}
