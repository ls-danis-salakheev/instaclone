package com.instacloneapp.security;

import com.instacloneapp.entities.User;
import com.instacloneapp.services.SecurityUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.util.StringUtils.hasText;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtTokenProvider tokenProvider;

    private SecurityUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJwtFromRequest(httpServletRequest);
            if (hasText(jwt) && tokenProvider.validateToken(jwt)) {

                final Long userIdFromToken = tokenProvider.getUserIdFromToken(jwt);
                final User userFromJwt = userDetailsService.loadUserById(userIdFromToken);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userFromJwt, null, Collections.emptyList()
                        );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public String getJwtFromRequest(HttpServletRequest request) {

        String bearToken = request.getHeader(SecurityConstants.HEADER_STRING);
        if (hasText(bearToken) && bearToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return bearToken.split(" ")[1];
        }
        return null;

    }
}
