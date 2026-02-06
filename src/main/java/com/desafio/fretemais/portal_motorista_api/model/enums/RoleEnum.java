package com.desafio.fretemais.portal_motorista_api.model.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public enum RoleEnum {

    USUARIO("ROLE_USUARIO", "Usuário"),
    MOTORISTA("ROLE_MOTORISTA", "Motorista"),
    ADMIN("ROLE_ADMIN", "Administrador");

    private final String role;
    private final String descricao;

    RoleEnum(String role, String descricao) {
        this.role = role;
        this.descricao = descricao;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }
}
