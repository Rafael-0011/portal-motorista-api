package com.desafio.fretemais.portal_motorista_api.service.impl;

import com.desafio.fretemais.portal_motorista_api.security.AuthenticatedUser;
import com.desafio.fretemais.portal_motorista_api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtEncoder jwtEncoder;

    @Value("${jwt.time.minutes.exp}")
    private int jwtTimeMinutes;

    @Override
    public String criarToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(60L * jwtTimeMinutes);

        String scope = authentication
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var usuarioAutenticado = (AuthenticatedUser) authentication.getPrincipal();

        var claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(expiresAt)
                .issuer("com.desafio.fretemais.portal_motorista_api")
                .subject(usuarioAutenticado.getUsername())
                .claims(construirClaims(usuarioAutenticado, scope))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private Consumer<Map<String, Object>> construirClaims(AuthenticatedUser usuarioAutenticado, String scope) {
        var usuario = usuarioAutenticado.usuario();

        return claims -> {
            claims.put("user", usuario.getId());
            claims.put("name", usuario.getNome());
            claims.put("scope", scope);
        };
    }
}
