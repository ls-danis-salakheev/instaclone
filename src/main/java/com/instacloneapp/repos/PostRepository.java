package com.instacloneapp.repos;

import com.instacloneapp.entities.Post;
import com.instacloneapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUsrOrderByCreatedDateDesc(User usr);

    List<Post> findAllByOrderByCreatedDateDesc();

    Optional<Post> findPostByIdAndUsr(Long id, User usr);

}
