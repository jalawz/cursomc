package com.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cursomc.dto.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter (final AuthenticationManager authenticationManager, final JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication (final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        try {
            final CredenciaisDTO credenciais = new ObjectMapper()
                    .readValue(request.getInputStream(), CredenciaisDTO.class);
            final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credenciais.getEmail(), credenciais.getSenha(), new ArrayList<>());
            final Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication (final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        final String username = ((UserSS) authResult.getPrincipal()).getUsername();
        final String token = jwtUtil.generateToken(username);
        response.addHeader("Authorization", String.format("Bearer %s", token));
    }
}
