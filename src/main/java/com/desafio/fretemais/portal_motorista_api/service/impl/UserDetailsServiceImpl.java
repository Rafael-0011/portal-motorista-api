package com.desafio.fretemais.portal_motorista_api.service.impl;

import com.desafio.fretemais.portal_motorista_api.model.entity.UsuarioEntity;
import com.desafio.fretemais.portal_motorista_api.repository.UsuarioRepository;
import com.desafio.fretemais.portal_motorista_api.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UsuarioEntity usuario = this.usuarioRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuário não cadastrado"));
        return new AuthenticatedUser(usuario);
    }
}