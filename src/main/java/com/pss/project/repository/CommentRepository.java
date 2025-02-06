package com.pss.project.repository;

import com.pss.project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostId(long postId);

    /*
     * Read Comment By  Id
     * Created by Pushpak
     *  */
    Optional<Comment> findByIdAndPostId(int Id, long postId);

}