package com.desafio.fretemais.portal_motorista_api.controller;

import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioFilterReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.response.UsuarioResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários/motoristas")
@SecurityRequirement(name = "bearer-key")
public interface UsuarioController {

    @Operation(summary = "Buscar usuários com filtros",
               description = "Busca usuários com filtros no JSON body e paginação via query parameters (?page=0&size=10&sort=nome,asc)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content)
    })
    ResponseEntity<Page<UsuarioResDto>> buscarComFiltros(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Filtros de busca (texto, UF, cidade, tipos de veículo)",
            required = false,
            content = @Content(schema = @Schema(implementation = UsuarioFilterReqDto.class))
        )
        @RequestBody UsuarioFilterReqDto filter,
        @Parameter(description = "Paginação via query params (ex: ?page=0&size=10&sort=nome,desc)", hidden = true)
        Pageable pageable
    );

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes de um usuário específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    ResponseEntity<UsuarioResDto> buscarPorId(
        @Parameter(description = "ID do usuário (UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id
    );

    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário/motorista no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou faltando campos obrigatórios", content = @Content),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado no sistema", content = @Content)
    })
    ResponseEntity<UsuarioResDto> criar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do novo usuário",
            required = true,
            content = @Content(schema = @Schema(implementation = UsuarioReqDto.class))
        )
        @Valid @RequestBody UsuarioReqDto dto
    );

    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado para outro usuário", content = @Content)
    })
    ResponseEntity<UsuarioResDto> atualizar(
        @Parameter(description = "ID do usuário a ser atualizado")
        @PathVariable UUID id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Novos dados do usuário",
            required = true,
            content = @Content(schema = @Schema(implementation = UsuarioReqDto.class))
        )
        @Valid @RequestBody UsuarioReqDto dto
    );

    @Operation(summary = "Deletar usuário (soft delete)",
               description = "Remove um usuário do sistema alterando seu status para INATIVO (soft delete)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    ResponseEntity<Void> deletar(
        @Parameter(description = "ID do usuário a ser desativado")
        @PathVariable UUID id
    );
}
