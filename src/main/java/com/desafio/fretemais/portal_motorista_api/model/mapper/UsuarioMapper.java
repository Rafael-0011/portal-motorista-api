package com.desafio.fretemais.portal_motorista_api.model.mapper;

import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioUpdateReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.response.UsuarioResDto;
import com.desafio.fretemais.portal_motorista_api.model.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final PasswordEncoder passwordEncoder;

    public UsuarioEntity toEntity(UsuarioReqDto dto) {
        return UsuarioEntity.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.senha()))
                .telefone(dto.telefone())
                .cidade(dto.cidade())
                .uf(dto.uf().toUpperCase())
                .role(dto.role())
                .status(dto.status())
                .tiposVeiculo(dto.tiposVeiculo())
                .build();
    }

    public UsuarioResDto toResponseDTO(UsuarioEntity entity) {
        return UsuarioResDto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .cidade(entity.getCidade())
                .uf(entity.getUf())
                .perfil(entity.getRole())
                .status(entity.getStatus())
                .tiposVeiculo(entity.getTiposVeiculo())
                .createdAt(entity.getCriadoEm())
                .updatedAt(entity.getAtualizadoEm())
                .build();
    }

    public void updateEntityFromDTO(UsuarioEntity entity, UsuarioReqDto dto) {
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        if (dto.senha() != null && !dto.senha().isEmpty()) {
            entity.setSenha(passwordEncoder.encode(dto.senha()));
        }
        entity.setTelefone(dto.telefone());
        entity.setCidade(dto.cidade());
        entity.setUf(dto.uf().toUpperCase());
        entity.setRole(dto.role());
        entity.setStatus(dto.status());
        entity.setTiposVeiculo(dto.tiposVeiculo());
    }

    public void updateEntityFromDTO(UsuarioEntity entity, UsuarioUpdateReqDto dto) {
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        // Atualiza a senha apenas se foi fornecida
        if (dto.senha() != null && !dto.senha().isEmpty()) {
            entity.setSenha(passwordEncoder.encode(dto.senha()));
        }
        entity.setTelefone(dto.telefone());
        entity.setCidade(dto.cidade());
        entity.setUf(dto.uf().toUpperCase());
        entity.setRole(dto.role());
        entity.setStatus(dto.status());
        entity.setTiposVeiculo(dto.tiposVeiculo());
    }
}
