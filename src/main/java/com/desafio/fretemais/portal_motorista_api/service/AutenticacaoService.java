package com.desafio.fretemais.portal_motorista_api.service;

import com.desafio.fretemais.portal_motorista_api.model.dto.request.LoginReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.response.TokenResDto;
import com.desafio.fretemais.portal_motorista_api.security.AuthenticatedUser;

public interface AutenticacaoService {

    TokenResDto autenticar(LoginReqDto loginReqDto);

    AuthenticatedUser resgatarUsuarioLogado();
}
