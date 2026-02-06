package com.desafio.fretemais.portal_motorista_api.service.impl;

import com.desafio.fretemais.portal_motorista_api.model.dto.response.VehicleTypeResDto;
import com.desafio.fretemais.portal_motorista_api.model.enums.VehicleTypeEnum;
import com.desafio.fretemais.portal_motorista_api.service.VehicleTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {

    @Override
    public List<VehicleTypeResDto> listarTodos() {
        return Arrays.stream(VehicleTypeEnum.values())
                .map(type -> VehicleTypeResDto.builder()
                        .value(type.name())
                        .label(type.name())
                        .build())
                .collect(Collectors.toList());
    }
}
