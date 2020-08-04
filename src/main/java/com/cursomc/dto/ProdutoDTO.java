package com.cursomc.dto;

import java.io.Serializable;

import com.cursomc.domain.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoDTO implements Serializable {

    private Integer id;

    private String nome;

    private Double preco;

    public ProdutoDTO (final Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
    }
}
