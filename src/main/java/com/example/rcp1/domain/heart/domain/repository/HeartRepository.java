package com.example.rcp1.domain.heart.domain.repository;

import com.example.rcp1.domain.heart.domain.Heart;
import com.example.rcp1.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByUser(User user);
    // Optional<Heart> findByUserAndCompany(User user, Company company);
}
