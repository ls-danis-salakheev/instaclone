package com.instacloneapp.security.configs;

import com.instacloneapp.security.JwtAuthFilter;
import com.instacloneapp.security.JwtAuthenticationEntryPoint;
import com.instacloneapp.security.SecurityConstants;
import com.instacloneapp.services.SecurityUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        proxyTargetClass = true
)
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final SecurityUserDetailsService securityUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstants.SIGN_UP_URLS).permitAll()
                .anyRequest().authenticated();
        
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public Filter jwtAuthenticationFilter() {

        return new JwtAuthFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(securityUserDetailsService).passwordEncoder(encoder());
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {

        return super.authenticationManager();
    }

    @Bean
    protected BCryptPasswordEncoder encoder() {

        return new BCryptPasswordEncoder();
    }
}
