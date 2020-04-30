package com.epam.esm.security.jwt;

import com.epam.esm.handler.MainExceptionHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {
    private static final String EXPIRED_JWT_MESSAGE = "JWT are expired, login again";
    private static final String INCORRECT_JWT = "Your JWT incorrectly formed";

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            if (token != null && jwtTokenProvider.validateToken(token)
                    && jwtTokenProvider.getAuthentication(token) != null) {
                SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.getWriter().write(EXPIRED_JWT_MESSAGE);
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (JwtException e) {
            response.getWriter().write(INCORRECT_JWT);
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (NumberFormatException e) {
            response.getWriter().write("Id should be a number");
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}