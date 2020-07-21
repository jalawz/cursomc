package com.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.cursomc.domain.Categoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Size(min = 5, max = 80, message = "Deve conter entre 5 e 80 caracteres")
    private String nome;

    public CategoriaDTO () {
    }

    public CategoriaDTO (final Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }
}
