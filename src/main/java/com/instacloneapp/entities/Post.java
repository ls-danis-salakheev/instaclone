package com.instacloneapp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String caption;

    private String location;

    private Integer likes;

    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> likedUsers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User usr;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(updatable = false)
    @JsonFormat(pattern = "HH:mm:ss dd.MM.yyyy")
    private LocalDate createdDate;

    @PrePersist
    public void doCreate() {
        this.createdDate = LocalDate.now();
    }

}
