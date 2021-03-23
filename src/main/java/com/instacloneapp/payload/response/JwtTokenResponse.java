package com.instacloneapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenResponse {

    private boolean isSuccessAuth;
    private String jwt;

}
