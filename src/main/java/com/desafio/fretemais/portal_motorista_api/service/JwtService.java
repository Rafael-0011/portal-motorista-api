package com.desafio.fretemais.portal_motorista_api.service;

import org.springframework.security.core.Authentication;

public interface JwtService {

    String criarToken(Authentication authentication);
}
