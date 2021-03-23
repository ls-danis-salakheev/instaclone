package com.instacloneapp.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
public class UserDto {

    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String bio;

}
