package com.cursomc.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    public JWTAuthorizationFilter (
            final AuthenticationManager authenticationManager, final JWTUtil jwtUtil,
            final UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal (final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {
        final String header = request.getHeader("Authorization");

        if (!Objects.isNull(header) && header.startsWith("Bearer ")) {
            final UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
            if (!Objects.isNull(auth)) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication (final String token) {
        if (jwtUtil.tokenValido(token)) {
            final String userName = jwtUtil.getUsername(token);
            final UserDetails user = userDetailsService.loadUserByUsername(userName);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }
}
