package com.example.rcp1.domain.recruitment.domain.repository;


import com.example.rcp1.domain.recruitment.domain.Field;
import com.example.rcp1.domain.recruitment.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByStatusNot(String status);

    Optional<Post> findByIdAndStatusNot(Long id, String status);

    List<Post> findByUser_IdAndStatusNot(Long userId, String status);

    List<Post> findByUser_Id(Long userId);



    /**정적 필터링 로직 사용시**/
    @Query("SELECT p FROM Post p JOIN p.fields f WHERE f.name IN :fieldNames AND p.required_career = :career AND p.status <> 'D'")
    List<Post> findByFilters(@Param("fieldNames") Set<String> fieldNames, @Param("career") Integer career);
    //fieldNames의 요소 중 하나라도 일치하는게 있으면서 career가 일치하는 데이터들 반환(status not "D")

    @Query("SELECT p FROM Post p JOIN p.fields f WHERE f.name IN :fieldNames AND p.status <> 'D'")
    List<Post> findByFilters(@Param("fieldNames") Set<String> fieldNames);

    @Query("SELECT p FROM Post p JOIN p.fields f WHERE p.required_career = :career AND p.status <> 'D'")
    List<Post> findByFilters(@Param("career") Integer career);


}
