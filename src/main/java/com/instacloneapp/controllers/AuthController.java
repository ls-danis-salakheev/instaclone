package com.instacloneapp.controllers;

import com.instacloneapp.payload.request.LoginRequest;
import com.instacloneapp.payload.request.SignUpRequest;
import com.instacloneapp.payload.response.JwtTokenResponse;
import com.instacloneapp.payload.response.MessageResponse;
import com.instacloneapp.security.JwtTokenProvider;
import com.instacloneapp.security.SecurityConstants;
import com.instacloneapp.services.UserService;
import com.instacloneapp.validations.ResponseErrorValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@PreAuthorize("permitAll")
@AllArgsConstructor
public class AuthController {

    private final ResponseErrorValidation responseErrorValidation;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;


    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {

        final ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)) return errors;

        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);


        return ResponseEntity.ok(new JwtTokenResponse(true, jwt));
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest request,
                                               BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);

        if (!ObjectUtils.isEmpty(errors)) return errors;
        userService.registerUser(request);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }


}
