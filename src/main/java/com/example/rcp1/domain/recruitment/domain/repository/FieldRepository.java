package com.example.rcp1.domain.recruitment.domain.repository;

import com.example.rcp1.domain.recruitment.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field,Long> {
    Optional<Field> findByName(String fieldName);
}
