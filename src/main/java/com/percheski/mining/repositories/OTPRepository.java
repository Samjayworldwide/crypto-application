package com.percheski.mining.repositories;

import com.percheski.mining.entities.model.OTPEntity;
import com.percheski.mining.entities.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTPEntity,Long> {

    Optional<OTPEntity>findByOtpAndEmail(int otp, String email);
    OTPEntity findByEmail(String email);
    @Modifying
    @Transactional
    void deleteByCreatedAtBefore(Date createdAt);
}
