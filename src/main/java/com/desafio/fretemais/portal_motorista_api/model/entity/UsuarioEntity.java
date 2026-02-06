package com.desafio.fretemais.portal_motorista_api.model.entity;


import com.desafio.fretemais.portal_motorista_api.model.enums.RoleEnum;
import com.desafio.fretemais.portal_motorista_api.model.enums.StatusUsuarioEnum;
import com.desafio.fretemais.portal_motorista_api.model.enums.VehicleTypeEnum;
import com.desafio.fretemais.portal_motorista_api.shared.audit.Audit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class UsuarioEntity extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String uf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusUsuarioEnum status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "tipos_veiculo", columnDefinition = "jsonb")
    private List<VehicleTypeEnum> tiposVeiculo;


}
