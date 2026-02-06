package com.desafio.fretemais.portal_motorista_api.model.enums;

import lombok.Getter;

@Getter
public enum StatusUsuarioEnum {

    ATIVO("Ativo"),
    INATIVO("Inativo"),
    BLOQUEADO("Bloqueado");

    private final String descricao;

    StatusUsuarioEnum(String descricao) {
        this.descricao = descricao;
    }
}
