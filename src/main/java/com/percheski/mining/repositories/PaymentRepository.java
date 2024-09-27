package com.percheski.mining.repositories;

import com.percheski.mining.entities.model.PaymentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentData,Long> {

    boolean existsByName(String name);
    Optional<PaymentData> findByName(String fileName);

}
