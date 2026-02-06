package com.desafio.fretemais.portal_motorista_api.model.dto.request;

import jakarta.validation.constraints.*;

public record LoginReqDto(
        @NotBlank(message = "Email é obrigatório!")
        @Email(message = "Email inválido!")
        String email,

        @NotBlank(message = "Senha é obrigatória!")
        @Size(min = 5, max = 32, message = "Senha deve ter entre 5 e 32 caracteres!")
        String senha
) {}