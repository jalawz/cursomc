package com.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cursomc.domain.enums.Perfil;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String email;

    private String cpfOuCnpj;

    private Integer tipo;

    @JsonIgnore
    private String senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "TELEFONE")
    @Column(name = "TELEFONE")
    private Set<String> telefones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente () {
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente (final Integer id, final String nome, final String email, final String cpfOuCnpj,
            final TipoCliente tipo, final String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo == null ? null : tipo.getCod();
        this.senha = senha;
        addPerfil(Perfil.CLIENTE);
    }

    public TipoCliente getTipo () {
        return TipoCliente.toEnum(tipo);
    }

    public void setTipo (final TipoCliente tipo) {
        this.tipo = tipo.getCod();
    }

    public static Cliente of (final ClienteDTO dto) {
        return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
    }

    public static Cliente of (final ClienteNewDTO dto) {
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final Cliente cli = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(),
                TipoCliente.toEnum(dto.getTipo()), encoder.encode(dto.getSenha()));
        final Cidade cidade = new Cidade(dto.getCidadeId(), null, null);
        final Endereco endereco = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(),
                dto.getBairro(), dto.getCep(), cli, cidade);
        cli.getEnderecos().add(endereco);
        cli.getTelefones().add(dto.getTelefone1());
        Optional.ofNullable(dto.getTelefone2()).ifPresent(tel2 -> cli.getTelefones().add(tel2));
        Optional.ofNullable(dto.getTelefone3()).ifPresent(tel3 -> cli.getTelefones().add(tel3));

        return cli;
    }

    public Set<Perfil> getPerfis () {
        return perfis.stream()
                .map(perfilId -> Perfil.toEnum(perfilId)).collect(Collectors.toSet());
    }

    public void addPerfil (final Perfil perfil) {
        perfis.add(perfil.getCod());
    }
}
