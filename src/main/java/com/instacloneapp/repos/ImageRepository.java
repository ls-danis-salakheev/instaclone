package com.instacloneapp.repos;

import com.instacloneapp.entities.PhotoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<PhotoModel, Long> {

    Optional<PhotoModel> findByUserId(Long userId);

    Optional<PhotoModel> findByPostId(Long postId);

}
