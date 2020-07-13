package com.cursomc.domain.enums;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;

@Getter
public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private final Integer cod;

    private final String descricao;

    EstadoPagamento (final Integer cod, final String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static EstadoPagamento toEnum (final Integer cod) {
        if (Objects.isNull(cod)) {
            return null;
        }

        return Arrays.stream(EstadoPagamento.values())
                .filter(ep -> cod.equals(ep.getCod()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Código: %d inválido", cod)));
    }
}
