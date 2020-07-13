package com.cursomc.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ItemPedido implements Serializable {

    @JsonIgnore
    @EmbeddedId
    private final ItemPedidoPK id = new ItemPedidoPK();

    private Double desconto;

    private Integer quantidade;

    private Double preco;

    public ItemPedido (final Pedido pedido, final Produto produto, final Double desconto, final Integer quantidade,
            final Double preco) {
        id.setPedido(pedido);
        id.setProduto(produto);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    @JsonIgnore
    public Pedido getPedido () {
        return id.getPedido();
    }

    public Produto getProduto () {
        return id.getProduto();
    }
}
