package com.pss.project.repository;

import com.pss.project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
//    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.id = :id")
//    Optional<Post> findByIdWithComments(@Param("id") Long id);

}
