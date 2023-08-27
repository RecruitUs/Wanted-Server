package com.example.rcp1.domain.user.domain.repository;

import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.dto.SignInReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);
}
