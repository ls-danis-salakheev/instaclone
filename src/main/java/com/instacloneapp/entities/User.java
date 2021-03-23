package com.instacloneapp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.instacloneapp.entities.enums.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@Table(name = "usr")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, updatable = false,
            length = 32)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(length = 1200)
    private String password;

    @Column(columnDefinition = "text")
    private String bio;

    @ElementCollection(targetClass = Roles.class)
    @CollectionTable(name = "usr_roles",
            joinColumns = @JoinColumn(name = "usr_id"))
    private Set<Roles> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, mappedBy = "usr", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @JsonFormat(pattern = "HH:mm:ss dd.MM.yyyy")
    @Column(updatable = false)
    private LocalDate createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;


    public User(Long id,
                String username,
                String email,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }


    @PrePersist
    public void doCreate() {
        this.createdDate = LocalDate.now();
    }

    /**
    * SECURITY BLOG
     */

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
