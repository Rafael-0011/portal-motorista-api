package com.desafio.fretemais.portal_motorista_api.controller.impl;

import com.desafio.fretemais.portal_motorista_api.controller.VehicleTypeController;
import com.desafio.fretemais.portal_motorista_api.model.dto.response.VehicleTypeResDto;
import com.desafio.fretemais.portal_motorista_api.service.VehicleTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehicle-types")
@RequiredArgsConstructor
public class VehicleTypeControllerImpl implements VehicleTypeController {

    private final VehicleTypeService vehicleTypeService;

    @Override
    @GetMapping
    public ResponseEntity<List<VehicleTypeResDto>> listarTodos() {
        return ResponseEntity.ok(vehicleTypeService.listarTodos());
    }
}
