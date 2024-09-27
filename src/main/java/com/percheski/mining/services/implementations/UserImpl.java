package com.percheski.mining.services.implementations;

import com.percheski.mining.entities.model.UserEntity;
import com.percheski.mining.exceptions.UserNotFoundException;
import com.percheski.mining.payload.response.ProfileResponse;
import com.percheski.mining.repositories.UserRepository;
import com.percheski.mining.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserServices {

    private final UserRepository userRepository;

    @Override
    public ProfileResponse getDetails() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        return ProfileResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(String.valueOf(user.getGender()))
                .numberOfStakes(user.getNumberOfTimes())
                .build();
    }
}
