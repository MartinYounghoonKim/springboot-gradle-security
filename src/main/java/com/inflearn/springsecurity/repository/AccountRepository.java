package com.inflearn.springsecurity.repository;

import com.inflearn.springsecurity.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
