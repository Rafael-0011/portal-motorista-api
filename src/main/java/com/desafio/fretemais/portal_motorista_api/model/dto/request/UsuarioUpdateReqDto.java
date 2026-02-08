package com.desafio.fretemais.portal_motorista_api.model.dto.request;

import com.desafio.fretemais.portal_motorista_api.model.enums.RoleEnum;
import com.desafio.fretemais.portal_motorista_api.model.enums.StatusUsuarioEnum;
import com.desafio.fretemais.portal_motorista_api.model.enums.VehicleTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record UsuarioUpdateReqDto(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        String email,

        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        @NotBlank(message = "UF é obrigatório")
        @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
        String uf,

        @NotNull(message = "Role é obrigatório")
        RoleEnum role,

        @NotNull(message = "Status é obrigatório")
        StatusUsuarioEnum status,

        List<VehicleTypeEnum> tiposVeiculo
) {
}
