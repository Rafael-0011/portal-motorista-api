package com.desafio.fretemais.portal_motorista_api.controller;

import com.desafio.fretemais.portal_motorista_api.model.dto.request.LoginReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.response.TokenResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários")
public interface AutenticacaoController {

    @Operation(
        summary = "Autenticar usuário",
        description = "Realiza autenticação do usuário e retorna token JWT para acesso aos endpoints protegidos"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Autenticação realizada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TokenResDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Credenciais inválidas - Email ou senha incorretos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Usuário inativo ou bloqueado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos - Verifique os campos obrigatórios",
            content = @Content
        )
    })
    ResponseEntity<TokenResDto> autenticar(@Valid @RequestBody LoginReqDto loginReqDto);
}
