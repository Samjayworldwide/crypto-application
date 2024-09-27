package com.percheski.mining.repositories;

import com.percheski.mining.entities.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByEmail(String email);
    Optional<UserEntity>  findByEmail(String email);
    Boolean existsByEmail(String email);

    @Override
    void delete(UserEntity user);
}
