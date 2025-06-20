package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.AdmSuporte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AdmSuporteDTO {

        private Long id;
        private String nome;
        private String cpf;
        private String login;
        private String senha;

        private String email1;
        private String email2;
        private String telefone1;
        private String telefone2;

        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;
        private String cep;
        private String uf;

        public static AdmSuporteDTO create(AdmSuporte admSuporte){
            ModelMapper modelMapper = new ModelMapper();
            AdmSuporteDTO dto = modelMapper.map( admSuporte, AdmSuporteDTO.class);
            return dto;
        }
}
