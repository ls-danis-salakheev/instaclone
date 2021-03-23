package com.instacloneapp.services;

import com.instacloneapp.entities.User;
import com.instacloneapp.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        final User userFromDb = userRepository.findUserByEmail(s).orElseThrow(() -> {
            throw new UsernameNotFoundException("Cannot find email: " + s);
        });

        return build(userFromDb);
    }

    public User loadUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow();
    }

    public static User build(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                authorities);
    }
}
