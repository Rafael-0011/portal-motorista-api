package com.desafio.fretemais.portal_motorista_api.controller;

import com.desafio.fretemais.portal_motorista_api.model.dto.response.VehicleTypeResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Tipos de Veículos", description = "Endpoints para listar tipos de veículos disponíveis")
@SecurityRequirement(name = "bearer-key")
public interface VehicleTypeController {

    @Operation(summary = "Listar tipos de veículos",
               description = "Retorna todos os tipos de veículos disponíveis para uso em dropdowns")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de tipos de veículos retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    })
    ResponseEntity<List<VehicleTypeResDto>> listarTodos();
}
