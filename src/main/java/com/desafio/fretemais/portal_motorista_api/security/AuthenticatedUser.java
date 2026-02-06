package com.desafio.fretemais.portal_motorista_api.security;

import com.desafio.fretemais.portal_motorista_api.model.entity.UsuarioEntity;
import com.desafio.fretemais.portal_motorista_api.model.enums.StatusUsuarioEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record AuthenticatedUser(UsuarioEntity usuario) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuario.getRole().getAuthorities();
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return usuario.getStatus() != StatusUsuarioEnum.BLOQUEADO;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.getStatus() == StatusUsuarioEnum.ATIVO;
    }
}
