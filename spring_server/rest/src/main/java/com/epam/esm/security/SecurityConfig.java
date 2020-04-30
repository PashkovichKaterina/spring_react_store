package com.epam.esm.security;

import com.epam.esm.security.jwt.FilterManager;
import com.epam.esm.security.jwt.JwtTokenProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String SIGNUP_ENDPOINT = "/signup";
    private static final String CERTIFICATES_ENDPOINT = "/certificates/**";
    private static final String USER_ENDPOINT = "/users/{userId}/**";
    private static final String MAIN_ENDPOINT = "/**";

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(SIGNUP_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, CERTIFICATES_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, USER_ENDPOINT)
                .access("@userSecurity.hasUserId(authentication,#userId) or hasRole('ADMIN')")
                .antMatchers(USER_ENDPOINT).access("@userSecurity.hasUserId(authentication,#userId) and hasRole('USER')")
                .antMatchers(MAIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .oauth2Login()
                .successHandler(new CustomAuthenticationSuccessHandler(jwtTokenProvider, userService))
                .and()
                .apply(new FilterManager(jwtTokenProvider));
    }
}