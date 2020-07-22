package com.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String email;

    private String cpfOuCnpj;

    private Integer tipo;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "TELEFONE")
    @Column(name = "TELEFONE")
    private Set<String> telefones = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente (final Integer id, final String nome, final String email, final String cpfOuCnpj,
            final TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo == null ? null : tipo.getCod();
    }

    public TipoCliente getTipo () {
        return TipoCliente.toEnum(tipo);
    }

    public void setTipo (final TipoCliente tipo) {
        this.tipo = tipo.getCod();
    }

    public static Cliente of (final ClienteDTO dto) {
        return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
    }

    public static Cliente of (final ClienteNewDTO dto) {
        final Cliente cli = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(),
                TipoCliente.toEnum(dto.getTipo()));
        final Cidade cidade = new Cidade(dto.getCidadeId(), null, null);
        final Endereco endereco = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(),
                dto.getBairro(), dto.getCep(), cli, cidade);
        cli.getEnderecos().add(endereco);
        cli.getTelefones().add(dto.getTelefone1());
        Optional.ofNullable(dto.getTelefone2()).ifPresent(tel2 -> cli.getTelefones().add(tel2));
        Optional.ofNullable(dto.getTelefone3()).ifPresent(tel3 -> cli.getTelefones().add(tel3));

        return cli;
    }
}
