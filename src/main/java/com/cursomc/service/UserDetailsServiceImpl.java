package com.cursomc.service;

import java.util.Objects;

import com.cursomc.domain.Cliente;
import com.cursomc.repository.ClienteRepository;
import com.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername (final String email) throws UsernameNotFoundException {
        final Cliente cliente = repository.findByEmail(email);

        if (Objects.isNull(cliente)) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
    }
}
