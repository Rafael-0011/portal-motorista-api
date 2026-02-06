package com.desafio.fretemais.portal_motorista_api.service;

import com.desafio.fretemais.portal_motorista_api.model.dto.response.VehicleTypeResDto;

import java.util.List;

public interface VehicleTypeService {

    List<VehicleTypeResDto> listarTodos();
}
