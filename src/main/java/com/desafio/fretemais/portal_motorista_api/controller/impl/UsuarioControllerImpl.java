package com.desafio.fretemais.portal_motorista_api.controller.impl;

import com.desafio.fretemais.portal_motorista_api.controller.UsuarioController;
import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioFilterReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioUpdateReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.response.UsuarioResDto;
import com.desafio.fretemais.portal_motorista_api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioControllerImpl implements UsuarioController {

    private final UsuarioService usuarioService;

    @Override
    @PostMapping("search")
    public ResponseEntity<Page<UsuarioResDto>> buscarComFiltros(
            @RequestBody UsuarioFilterReqDto filter,
            Pageable pageable) {
        return ResponseEntity.ok(usuarioService.buscarComFiltros(filter, pageable));
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<UsuarioResDto> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('MOTORISTA', 'ADMIN')")
    public ResponseEntity<UsuarioResDto> criar(@Valid @RequestBody UsuarioReqDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criar(dto));
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('MOTORISTA', 'ADMIN')")
    public ResponseEntity<UsuarioResDto> atualizar(@PathVariable UUID id, @Valid @RequestBody UsuarioUpdateReqDto dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @Override
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('MOTORISTA', 'ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
