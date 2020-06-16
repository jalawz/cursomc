package com.cursomc.domain.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum TipoCliente {

    PESSOA_FISICA(1, "Pessoa Física"),
    PESSOA_JURIDICA(2, "Pessoa Jurídica");

    private final Integer cod;

    private final String descricao;

    private TipoCliente (final Integer cod, final String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static TipoCliente toEnum (final Integer cod) {
        if (cod == null) {
            return null;
        }

        return Arrays.stream(TipoCliente.values())
                .filter(tipoCliente -> cod.equals(tipoCliente.getCod()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Código inválido: %d", cod)));
    }
}
