package com.percheski.mining.services.implementations;

import com.percheski.mining.repositories.OTPRepository;
import com.percheski.mining.services.GenerateOTPServices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateOTP implements GenerateOTPServices {
    private final OTPRepository otpRepository;

    public int generateOTP() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Run every 24hrs
    @Scheduled(fixedRate = 120000)
    @Transactional
    public void deleteOldData() {
//        // Calculate the time threshold for deletion (e.g., 15 minutes ago)
//        LocalDateTime thresholdTime = LocalDateTime.now().minusMinutes(11);
        Date thresholdTime = new Date(System.currentTimeMillis() - 11 * 60 * 1000);
        otpRepository.deleteByCreatedAtBefore(thresholdTime);

    }
}
