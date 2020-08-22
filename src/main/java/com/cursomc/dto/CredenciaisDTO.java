package com.cursomc.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CredenciaisDTO implements Serializable {

    private String email;

    private String senha;
}
