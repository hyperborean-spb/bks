package com.bks.repository;

import com.bks.domain.Account;
import com.bks.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
