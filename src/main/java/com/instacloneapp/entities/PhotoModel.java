package com.instacloneapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@Data
@NoArgsConstructor
public class PhotoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] imageByte;

    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private Long postId;

}
