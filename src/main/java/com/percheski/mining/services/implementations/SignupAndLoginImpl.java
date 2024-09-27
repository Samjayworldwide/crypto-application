package com.percheski.mining.services.implementations;

import com.percheski.mining.entities.enums.Gender;
import com.percheski.mining.entities.model.UserEntity;
import com.percheski.mining.exceptions.*;
import com.percheski.mining.payload.response.LoginResponse;
import com.percheski.mining.payload.request.UserLoginRequest;
import com.percheski.mining.payload.request.UserSignupRequest;
import com.percheski.mining.repositories.UserRepository;
import com.percheski.mining.security.JWTGenerator;
import com.percheski.mining.services.SignupAndLoginUser;
import com.percheski.mining.services.EmailServices;
import com.percheski.mining.services.VerificationServices;
import com.percheski.mining.events.RegistrationCompleteEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.percheski.mining.entities.enums.Roles.ADMIN;
import static com.percheski.mining.entities.enums.Roles.USER;

@Service
@RequiredArgsConstructor
public class SignupAndLoginImpl implements SignupAndLoginUser {

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final JWTGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher publisher;
    private final VerificationServices verificationServices;
    private final EmailServices emailServices;

    @Override
    public String register(UserSignupRequest request,HttpServletRequest requests) {

        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new PasswordMisMatchException("Password does not match");
        }
        if(repository.existsByEmail(request.getEmail().toLowerCase())){
            throw new UserAlreadyExistException("User Already Exist");
        }

        String path = requests.getServletPath();


        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail().toLowerCase())
                .gender(Gender.valueOf(request.getGender().toUpperCase()))
                .dob(request.getDob())
                .nationality(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .roles( path.contains("admin") ? ADMIN : USER)
                .numberOfTimes(0L)
                .isVerified(false)
                .build();

        repository.save(userEntity);
        publisher.publishEvent(new RegistrationCompleteEvent(userEntity, applicationUrl(requests)));

        return "Registration Successful";
    }

    @Override
    public LoginResponse login(UserLoginRequest request, HttpServletRequest httpReq) {

        String path = httpReq.getServletPath();

        UserEntity userEntity =  repository.findUserEntityByEmail(request.getEmail().toLowerCase())
                .orElseThrow(()->new UserNotFoundException("User Not found"));
        if(!encoder.matches(request.getPassword(), userEntity.getPassword())){
            throw new PasswordMisMatchException("Wrong password");
        }

        if(!path.contains(String.valueOf(userEntity.getRoles()).toLowerCase())){
            throw new UnauthorizedException("You are not authorized") ;
        }

        if(!userEntity.isVerified()){

            verificationServices.re_sendVerificationEmail(userEntity.getEmail());
            throw new EmailNotVerifiedException("Email not verified");


        }

       Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
               (request.getEmail().toLowerCase(),request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtGenerator.generateToken(auth, 120L);
        String freshToken = jwtGenerator.generateToken(auth, 1440L);

        String name = userEntity.getFirstName();
        return new LoginResponse(token, freshToken,name,userEntity.getNumberOfTimes());
    }



    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
