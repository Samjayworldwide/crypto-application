package com.percheski.mining.security;
import com.percheski.mining.entities.model.UserEntity;
import com.percheski.mining.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServices implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity user = userRepo.findUserEntityByEmail(email)
               .orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user));
    }

    public Collection<GrantedAuthority> mapRolesToAuthorities(UserEntity user){
        return Collections.singleton(new SimpleGrantedAuthority(String.valueOf(user.getRoles())));
    }
}
