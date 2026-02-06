package com.desafio.fretemais.portal_motorista_api.controller.impl;

import com.desafio.fretemais.portal_motorista_api.controller.AutenticacaoController;
import com.desafio.fretemais.portal_motorista_api.model.dto.request.LoginReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.response.TokenResDto;
import com.desafio.fretemais.portal_motorista_api.service.AutenticacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autenticacao")
public class AutenticacaoControllerImpl implements AutenticacaoController {
    private final AutenticacaoService autenticacaoService;

    @Override
    @PostMapping("/autenticar")
    public ResponseEntity<TokenResDto> autenticar(@Valid @RequestBody LoginReqDto loginReqDto) {
        return ResponseEntity.ok(autenticacaoService.autenticar(loginReqDto));
    }
}
