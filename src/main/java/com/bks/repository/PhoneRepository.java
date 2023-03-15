package com.bks.repository;

import com.bks.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

	/*
	* согласно https://docs.spring.io/spring-data/jpa/docs/current/reference/html/, 4.4.5
	* "You can append an optional numeric value to top or first to specify the maximum result size to be returned. If the number is left out, a result size of 1 is assumed
	* For the queries that limit the result set to one instance, wrapping the result into with the Optional keyword is supported."
	* */
	Optional<Phone> getFirstByPhone (String phone);
}