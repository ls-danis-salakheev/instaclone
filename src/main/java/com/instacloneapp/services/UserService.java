package com.instacloneapp.services;

import com.instacloneapp.dto.UserDto;
import com.instacloneapp.entities.User;
import com.instacloneapp.payload.request.SignUpRequest;

import java.security.Principal;

public interface UserService {

    User registerUser(SignUpRequest incomingUser);

    User updateData(UserDto userDto, Principal principal);

}
