package com.desafio.fretemais.portal_motorista_api.repository.specification;

import com.desafio.fretemais.portal_motorista_api.model.dto.request.UsuarioFilterReqDto;
import com.desafio.fretemais.portal_motorista_api.model.entity.UsuarioEntity;
import com.desafio.fretemais.portal_motorista_api.model.enums.VehicleTypeEnum;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioSpecification {

    public Specification<UsuarioEntity> getFilter(UsuarioFilterReqDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por texto (nome, email ou telefone)
            if (filter.texto() != null && !filter.texto().isEmpty()) {
                String searchPattern = "%" + filter.texto().toLowerCase() + "%";
                Predicate nomePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")), searchPattern);
                Predicate emailPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("email")), searchPattern);
                Predicate telefonePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("telefone")), searchPattern);

                predicates.add(criteriaBuilder.or(nomePredicate, emailPredicate, telefonePredicate));
            }

            // Filtro por UF
            if (filter.uf() != null && !filter.uf().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.upper(root.get("uf")),
                        filter.uf().toUpperCase()));
            }

            // Filtro por Cidade
            if (filter.cidade() != null && !filter.cidade().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("cidade")),
                        "%" + filter.cidade().toLowerCase() + "%"));
            }

            // Filtro por Tipos de Veículo (JSONB contains)
            if (filter.tiposVeiculo() != null && !filter.tiposVeiculo().isEmpty()) {
                List<Predicate> vehiclePredicates = new ArrayList<>();
                for (VehicleTypeEnum vehicleType : filter.tiposVeiculo()) {
                    // Para JSONB, vamos usar uma função nativa do PostgreSQL
                    vehiclePredicates.add(
                        criteriaBuilder.isTrue(
                            criteriaBuilder.function(
                                "jsonb_exists",
                                Boolean.class,
                                criteriaBuilder.function(
                                    "cast",
                                    String.class,
                                    root.get("tiposVeiculo")
                                ),
                                criteriaBuilder.literal(vehicleType.name())
                            )
                        )
                    );
                }
                predicates.add(criteriaBuilder.or(vehiclePredicates.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
