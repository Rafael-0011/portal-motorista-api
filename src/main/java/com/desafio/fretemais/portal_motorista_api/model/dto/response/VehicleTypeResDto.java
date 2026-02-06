package com.desafio.fretemais.portal_motorista_api.model.dto.response;

import lombok.Builder;

@Builder
public record VehicleTypeResDto(
        String value,
        String label
) {}
