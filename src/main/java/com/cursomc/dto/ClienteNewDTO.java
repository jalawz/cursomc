package com.cursomc.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClienteNewDTO implements Serializable {

    private String nome;

    private String email;

    private String cpfOuCnpj;

    private Integer tipo;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cep;

    private String telefone1;

    private String telefone2;

    private String telefone3;

    private Integer cidadeId;
}
