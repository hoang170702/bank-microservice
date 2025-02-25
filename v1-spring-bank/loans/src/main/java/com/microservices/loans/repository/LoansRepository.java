package com.microservices.loans.repository;

import com.microservices.loans.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface LoansRepository extends JpaRepository<Loans, Long> {
    Optional<Loans> findByMobileNumber(String mobileNumber);

    @Modifying
    void deleteByMobileNumber(String mobileNumber);
}
