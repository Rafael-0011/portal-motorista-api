package com.desafio.fretemais.portal_motorista_api.service.impl;

import com.desafio.fretemais.portal_motorista_api.shared.exception.EmailJaCadastradoException;
import com.desafio.fretemais.portal_motorista_api.shared.exception.UsuarioNotFoundException;
import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioFilterReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.response.UsuarioResDto;
import com.desafio.fretemais.portal_motorista_api.model.entity.UsuarioEntity;
import com.desafio.fretemais.portal_motorista_api.model.enums.StatusUsuarioEnum;
import com.desafio.fretemais.portal_motorista_api.model.mapper.UsuarioMapper;
import com.desafio.fretemais.portal_motorista_api.repository.UsuarioRepository;
import com.desafio.fretemais.portal_motorista_api.repository.specification.UsuarioSpecification;
import com.desafio.fretemais.portal_motorista_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioSpecification usuarioSpecification;

    @Override
    public UsuarioResDto criar(UsuarioReqDto dto) {
        validarEmailUnico(dto.email(), null);

        UsuarioEntity entity = usuarioMapper.toEntity(dto);
        UsuarioEntity savedEntity = usuarioRepository.save(entity);

        return usuarioMapper.toResponseDTO(savedEntity);
    }

    @Override
    public UsuarioResDto atualizar(UUID id, UsuarioReqDto dto) {

        UsuarioEntity entity = buscarPorIdOuLancarExcecao(id);
        validarEmailUnico(dto.email(), id);

        usuarioMapper.updateEntityFromDTO(entity, dto);
        UsuarioEntity updatedEntity = usuarioRepository.save(entity);

        return usuarioMapper.toResponseDTO(updatedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResDto buscarPorId(UUID id) {
        UsuarioEntity entity = buscarPorIdOuLancarExcecao(id);
        return usuarioMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResDto> buscarComFiltros(UsuarioFilterReqDto filter, Pageable pageable) {
        Page<UsuarioEntity> entities = usuarioRepository.findAll(
                usuarioSpecification.getFilter(filter),
                pageable
        );

        return entities.map(usuarioMapper::toResponseDTO);
    }

    @Override
    public void deletar(UUID id) {
        UsuarioEntity entity = buscarPorIdOuLancarExcecao(id);
        entity.setStatus(StatusUsuarioEnum.INATIVO);
        usuarioRepository.save(entity);
    }

    // Métodos privados auxiliares

    private UsuarioEntity buscarPorIdOuLancarExcecao(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado com ID: " + id));
    }

    private void validarEmailUnico(String email, UUID idUsuarioAtual) {
        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            if (idUsuarioAtual == null || !usuario.getId().equals(idUsuarioAtual)) {
                throw new EmailJaCadastradoException("Email já cadastrado: " + email);
            }
        });
    }
}
