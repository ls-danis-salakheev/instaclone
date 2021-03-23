package com.instacloneapp.services;

import com.instacloneapp.dto.UserDto;
import com.instacloneapp.entities.User;
import com.instacloneapp.entities.enums.Roles;
import com.instacloneapp.exceptions.UserRegistrationException;
import com.instacloneapp.payload.request.SignUpRequest;
import com.instacloneapp.repos.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User registerUser(SignUpRequest inUser) {

        User user = new User();
        user.setUsername(inUser.getUsername());
        user.setFirstName(inUser.getFirstName());
        user.setLastName(inUser.getLastName());
        user.setEmail(inUser.getEmail());
        user.setPassword(passwordEncoder.encode(inUser.getPassword()));

        user.getRoles().add(Roles.ROLE_USER);

        try {
            log.info("Saving user with: " + user.toString());

            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error in registration step...");
            log.error(e.getMessage());
            throw new UserRegistrationException(
                    String.
                            format("User with username: %s is already exists. " +
                                   "Please retry again with new credentials", user.getUsername()));
        }
    }

    @Override
    public User updateData(UserDto userDto, Principal principal) {

        final User userFromDb = getUserByPrincipal(principal);

        userFromDb.setEmail(userDto.getEmail());
        userFromDb.setFirstName(userDto.getFirstName());
        userFromDb.setLastName(userDto.getLastName());
        userFromDb.setBio(userDto.getBio());

        return null;
    }

    private User getUserByPrincipal(Principal principal) {
        final String userName = principal.getName();

        return userRepository.findUserByUsername(userName)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
    }


}
