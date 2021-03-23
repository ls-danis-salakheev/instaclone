package com.instacloneapp.security;

import com.google.gson.Gson;
import com.instacloneapp.payload.response.InvalidLoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {

        InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
        final String invalidJson = new Gson().toJson(invalidLoginResponse);

        httpServletResponse.setContentType(SecurityConstants.CONTENT_TYPE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().println(invalidJson);
    }
}
