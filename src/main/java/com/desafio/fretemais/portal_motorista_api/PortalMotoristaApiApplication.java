package com.desafio.fretemais.portal_motorista_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PortalMotoristaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalMotoristaApiApplication.class, args);
	}

}
