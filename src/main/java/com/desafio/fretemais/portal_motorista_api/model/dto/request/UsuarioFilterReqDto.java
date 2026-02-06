package com.desafio.fretemais.portal_motorista_api.model.dto.request;

import com.desafio.fretemais.portal_motorista_api.model.enums.VehicleTypeEnum;

import java.util.List;

public record UsuarioFilterReqDto(
        String texto,
        String uf,
        String cidade,
        List<VehicleTypeEnum> tiposVeiculo
) {}
