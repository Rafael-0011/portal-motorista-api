package com.desafio.fretemais.portal_motorista_api.service;

import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioFilterReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.response.UsuarioResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UsuarioService {

    UsuarioResDto criar(UsuarioReqDto dto);

    UsuarioResDto atualizar(UUID id, UsuarioReqDto dto);

    UsuarioResDto buscarPorId(UUID id);

    Page<UsuarioResDto> buscarComFiltros(UsuarioFilterReqDto filter, Pageable pageable);

    void deletar(UUID id);
}
