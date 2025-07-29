package com.example.concesswebapi.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {

    private String login;
    private String senha;
}
