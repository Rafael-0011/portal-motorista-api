package com.desafio.fretemais.portal_motorista_api.repository;

import com.desafio.fretemais.portal_motorista_api.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID>, JpaSpecificationExecutor<UsuarioEntity> {

    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
