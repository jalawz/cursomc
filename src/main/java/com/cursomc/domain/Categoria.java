package com.cursomc.domain;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Categoria implements Serializable {

    private static final long serialVersionUID = -1447282662484019830L;

    private Integer id;

    private String name;

    public Categoria (final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals (final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id);
    }
}