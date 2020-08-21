package com.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.cursomc.service.validation.ClienteInsert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ClienteInsert
public class ClienteNewDTO implements Serializable {

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Size(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String cpfOuCnpj;

    private Integer tipo;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String senha;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String logradouro;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String numero;

    private String complemento;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String bairro;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String cep;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String telefone1;

    private String telefone2;

    private String telefone3;

    private Integer cidadeId;
}
