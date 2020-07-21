package com.cursomc.dto;

import java.io.Serializable;

import com.cursomc.domain.Categoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDTO implements Serializable {

    private Integer id;

    private String nome;

    public CategoriaDTO () {
    }

    public CategoriaDTO (final Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }
}
