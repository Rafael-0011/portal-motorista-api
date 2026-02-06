package com.desafio.fretemais.portal_motorista_api.model.dto.response;

import com.desafio.fretemais.portal_motorista_api.model.enums.RoleEnum;
import com.desafio.fretemais.portal_motorista_api.model.enums.StatusUsuarioEnum;
import com.desafio.fretemais.portal_motorista_api.model.enums.VehicleTypeEnum;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record UsuarioResDto(
        UUID id,
        String nome,
        String email,
        String telefone,
        String cidade,
        String uf,
        RoleEnum perfil,
        StatusUsuarioEnum status,
        List<VehicleTypeEnum> tiposVeiculo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
