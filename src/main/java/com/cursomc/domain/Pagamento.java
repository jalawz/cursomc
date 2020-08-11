package com.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.cursomc.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Pagamento implements Serializable {

    @Id
    private Integer id;

    private Integer estado;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    private Pedido pedido;

    public Pagamento (final Integer id, final EstadoPagamento estado, final Pedido pedido) {
        super();
        this.id = id;
        this.estado = estado == null ? null : estado.getCod();
        this.pedido = pedido;
    }

    public Integer getId () {
        return id;
    }

    public void setId (final Integer id) {
        this.id = id;
    }

    public EstadoPagamento getEstado () {
        return EstadoPagamento.toEnum(estado);
    }

    public void setEstado (final EstadoPagamento estado) {
        this.estado = estado.getCod();
    }

    public Pedido getPedido () {
        return pedido;
    }

    public void setPedido (final Pedido pedido) {
        this.pedido = pedido;
    }
}
