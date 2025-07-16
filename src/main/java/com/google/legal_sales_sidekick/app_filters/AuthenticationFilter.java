package com.google.legal_sales_sidekick.app_filters;

import com.google.legal_sales_sidekick.exception.AuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.google.legal_sales_sidekick.constants.constants.*;

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationProvider configuredAuthProvider;

    @Autowired
    public AuthenticationFilter(AuthenticationProvider configuredAuthProvider) {
        this.configuredAuthProvider = configuredAuthProvider;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (Objects.isNull(token)) {
            Cookie[] cookies = request.getCookies();
            Optional<Cookie> tokenCookie = Arrays.stream(cookies).filter(cookie -> StringUtils.equals(cookie.getName(), TOKEN)).findAny();
            if (tokenCookie.isPresent()) {
                token = tokenCookie.get().getValue();
            }
        }

        return token;
    }

    private void handleInvalidToken(final HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(INVALID_USER_TOKEN);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        if (Arrays.asList(UNAUTHORIZED_PATHS).contains(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = getToken(request);
            if (StringUtils.isEmpty(token)) {
                throw new AuthenticationException();
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null, token);
            Authentication authentication = configuredAuthProvider.authenticate(authenticationToken);
            if (Objects.isNull(authentication)) {
                throw new AuthenticationException();
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleInvalidToken(response);
        }
    }
}
