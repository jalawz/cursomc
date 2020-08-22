package com.cursomc.domain.enums;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;

@Getter
public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private final Integer cod;

    private final String descricao;

    Perfil (final Integer cod, final String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static Perfil toEnum (final Integer cod) {
        if (Objects.isNull(cod)) {
            return null;
        }

        return Arrays.stream(Perfil.values())
                .filter(ep -> cod.equals(ep.getCod()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Código: %d inválido", cod)));
    }
}
