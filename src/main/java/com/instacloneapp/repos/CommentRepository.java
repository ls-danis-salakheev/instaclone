package com.instacloneapp.repos;

import com.instacloneapp.entities.Comment;
import com.instacloneapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndUserId(Long id, Long userId);

    List<Comment> findAllByPost(Post post);

}
