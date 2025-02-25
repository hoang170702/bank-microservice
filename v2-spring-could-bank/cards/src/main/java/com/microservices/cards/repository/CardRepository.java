package com.microservices.cards.repository;

import com.microservices.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Cards, Long> {
    @Modifying
    void deleteByMobileNumber(String mobileNumber);

    Optional<Cards> findByMobileNumber(String mobileNumber);
}
