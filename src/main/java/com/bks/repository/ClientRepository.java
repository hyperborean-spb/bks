package com.bks.repository;

import com.bks.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

	// result can be multiple

	Page<Client> findByNameContainingIgnoreCaseOrderByName(Pageable pageable, String name);

	Page<Client> findByBirthDateAfterOrderByBirthDate(Pageable pageable, LocalDate birthdate);
}