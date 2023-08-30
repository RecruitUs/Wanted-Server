package com.example.rcp1.domain.user.domain.repository;

import com.example.rcp1.domain.applying.domain.Applying;
import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.dto.SignInReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    @Query("SELECT a FROM Applying a WHERE a.id = :applyingId AND a.user.id = :userId")
    Applying findByIdAndUserId(@Param("applyingId") Long applyingId, @Param("userId") String userId);
}
