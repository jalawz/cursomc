package com.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

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

    public Double getSubTotal () {
        return (preco - desconto) * quantidade;
    }

    @JsonIgnore
    public Pedido getPedido () {
        return id.getPedido();
    }

    public void setPedido (final Pedido pedido) {
        id.setPedido(pedido);
    }

    public Produto getProduto () {
        return id.getProduto();
    }

    public void setProduto (final Produto produto) {
        id.setProduto(produto);
    }

    @Override
    public String toString () {
        final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        final StringBuilder builder = new StringBuilder();
        builder.append(getProduto().getNome());
        builder.append(", Qtde: ");
        builder.append(getQuantidade());
        builder.append(", Preço unitário: ");
        builder.append(nf.format(getPreco()));
        builder.append(", Subtotal: ");
        builder.append(nf.format(getSubTotal()));
        builder.append("\n");
        return builder.toString();
    }
}
