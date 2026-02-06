package com.desafio.fretemais.portal_motorista_api.config.doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "rafael",
                        email = "rafaelsora0@gmail.com"
                ),
                title = "OpenApi",
                description = "OpenApi documentation for Spring Sercurity",
                version = "1.0"
        ),
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        },
        servers = @Server(
                description = "Local server",
                url = "http://localhost:8080/api"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SpringDocConfig {

}
