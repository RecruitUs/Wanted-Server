package com.example.rcp1.domain.applying.domain.repository;

import com.example.rcp1.domain.applying.domain.Applying;
import com.example.rcp1.domain.recruitment.domain.Post;
import com.example.rcp1.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface ApplyingRepository extends JpaRepository<Applying, Long> {

    ArrayList<Applying> findAllByUser(User user);


    Optional<Applying> findOneByIdAndUser(Long applyingId, User user);
}
