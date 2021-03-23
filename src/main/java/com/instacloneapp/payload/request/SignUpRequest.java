package com.instacloneapp.payload.request;

import com.instacloneapp.annotaions.PasswordMatcher;
import com.instacloneapp.annotaions.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatcher
public class SignUpRequest {

    @Email(message = "Your email should have a email format")
    @NotBlank(message = "Email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    @NotEmpty(message = "Login cannot be empty")
    private String username;
    @NotEmpty(message = "Password name cannot be empty")
    @Size(min = 8, message = "Password should be more than 8 symbols")
    private String password;
    private String confirmPassword;

}
