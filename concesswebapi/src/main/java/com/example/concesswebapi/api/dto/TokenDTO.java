package com.example.concesswebapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String login;
    private String token;
    private List<String> roles;
}