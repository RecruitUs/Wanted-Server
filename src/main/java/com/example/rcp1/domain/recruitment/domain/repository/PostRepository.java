package com.example.rcp1.domain.recruitment.domain.repository;


import com.example.rcp1.domain.recruitment.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
