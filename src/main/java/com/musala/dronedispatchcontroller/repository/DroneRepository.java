package com.musala.dronedispatchcontroller.repository;

import com.musala.dronedispatchcontroller.domain.Drone;
import com.musala.dronedispatchcontroller.domain.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DroneRepository  extends JpaRepository<Drone, String> {

	List<Drone> findByState(State state);

	@Query("SELECT  d.capacity FROM Drone d WHERE d.serialNumber = :serial")
	Integer getCapacityForSerial(@Param("serial") String serial);
}