package com.percheski.mining.services.implementations;

import com.percheski.mining.entities.enums.*;
import com.percheski.mining.entities.model.PaymentData;
import com.percheski.mining.entities.model.UserEntity;
import com.percheski.mining.entities.model.WalletAddresses;
import com.percheski.mining.exceptions.*;
import com.percheski.mining.payload.request.AdminChangeCoinAmount;
import com.percheski.mining.payload.request.AdminChangeWalletRequest;
import com.percheski.mining.payload.response.PaymentDataResponse;
import com.percheski.mining.payload.response.UserResponse;
import com.percheski.mining.repositories.PaymentRepository;
import com.percheski.mining.repositories.UserRepository;
import com.percheski.mining.repositories.WalletRepository;
import com.percheski.mining.services.AdminServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminImpl implements AdminServices {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final WalletRepository walletRepository;


    public UserResponse getUser(String email) {

        UserEntity user = userRepository.findUserEntityByEmail(email.toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Long> total = new ArrayList<>();
        for (var i : user.getPaymentData()) {
            total.add(i.getId());
        }

        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .isVerified(user.isVerified())
                .address(user.getNationality())
                .gender(String.valueOf(user.getGender()))
                .listOfPayments(total)
                .build();

    }

    public PaymentDataResponse searchPaymentId(Long id) {
        PaymentData paymentData = paymentRepository.findById(id).
                orElseThrow(() -> new FileException("Payment id not found"));
        return PaymentDataResponse.builder()
                .id(paymentData.getId())
                .name(paymentData.getName())
                .imageDataUrl(paymentData.getImageDataUrl())
                .status(String.valueOf(paymentData.getStatus()))
                .networks(String.valueOf(paymentData.getNetworks()))
                .currency(String.valueOf(paymentData.getCurrency()))
                .build();
    }


    @Transactional
    public String verifyPayment(Long id, String status) {
        PaymentData paymentData = paymentRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Payment does not exist"));
        paymentData.setStatus(Status.valueOf(status.toUpperCase()));
        paymentRepository.save(paymentData);
        if (Status.valueOf(status.toUpperCase()).equals(Status.CONFIRMED)) {
            UserEntity userEntity = userRepository.findById(paymentData.getUserEntity().getId())
                    .orElseThrow(()-> new UserNotFoundException("User not Found"));
            userEntity.setNumberOfTimes(userEntity.getNumberOfTimes()+ 1);
            userRepository.save(userEntity);
            return "Confirmed";
        }
        return "Declined";
    }

    public String viewPictureProof(String filename) {
        PaymentData paymentData = paymentRepository.findByName(filename)
                .orElseThrow(() -> new FileException("Photo proof does not exist"));
        return paymentData.getImageDataUrl();
    }

    @Transactional
    public String changeWalletDetails(AdminChangeWalletRequest request) {
        WalletAddresses wallet = walletRepository.findByCurrencyAndNetworks(Currency.valueOf(request.getCoin().toUpperCase()),
                Networks.valueOf(request.getNetwork().toUpperCase()));
        wallet.setWalletAddress(request.getWalletAddress());
        walletRepository.save(wallet);
        return "Wallet changed successfully";
    }

    @Transactional
    @Modifying
    public String changeCoinAmount(AdminChangeCoinAmount coin) {
        List<WalletAddresses> wallet = walletRepository.findAllByCurrency(Currency.valueOf(coin.getCoin().toUpperCase()));
        for (WalletAddresses wall : wallet) {
            wall.setAmount(coin.getAmount());
        }
        walletRepository.saveAll(wallet);
        return "Amount updated successfully";
    }
}
