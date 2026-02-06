package com.desafio.fretemais.portal_motorista_api.model.params;

import com.desafio.fretemais.portal_motorista_api.model.enums.VehicleTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioSearchParams {

    private String texto;
    private String uf;
    private String cidade;
    private List<VehicleTypeEnum> tiposVeiculo;
}
