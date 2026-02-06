package com.desafio.fretemais.portal_motorista_api.service.impl;

import com.desafio.fretemais.portal_motorista_api.shared.exception.CredenciaisInvalidasException;
import com.desafio.fretemais.portal_motorista_api.shared.exception.UsuarioInativoException;
import com.desafio.fretemais.portal_motorista_api.model.dto.request.LoginReqDto;
import com.desafio.fretemais.portal_motorista_api.model.dto.response.TokenResDto;
import com.desafio.fretemais.portal_motorista_api.model.enums.StatusUsuarioEnum;
import com.desafio.fretemais.portal_motorista_api.repository.UsuarioRepository;
import com.desafio.fretemais.portal_motorista_api.security.AuthenticatedUser;
import com.desafio.fretemais.portal_motorista_api.service.AutenticacaoService;
import com.desafio.fretemais.portal_motorista_api.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoServiceImpl implements AutenticacaoService {
    private final UserDetailsService userDetailsService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public TokenResDto autenticar(LoginReqDto loginReqDto) {
        var userDetails = userDetailsService.loadUserByUsername(loginReqDto.email());
        if (!(userDetails instanceof AuthenticatedUser usuario)){
            throw new CredenciaisInvalidasException("Usuário inválido!");
        }

        if (usuario.usuario().getStatus() != StatusUsuarioEnum.ATIVO){
            throw new UsuarioInativoException("Usuário inativo ou bloqueado.");
        }

        if (!passwordEncoder.matches(loginReqDto.senha(), usuario.getPassword())) {
            throw new CredenciaisInvalidasException("Usuário ou senha inválidos!");
        }

        var autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        return new TokenResDto(jwtService.criarToken(autenticacao));
    }

    @Override
    public AuthenticatedUser resgatarUsuarioLogado() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = usuarioRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return new AuthenticatedUser(user);
    }
}
