package com.example.concesswebapi.api.dto;

import com.example.concesswebapi.Model.Entity.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendedorDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String login;
    private String senha;
    private String telefone1;
    private String telefone2;
    private String email1;
    private String email2;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String uf;
    private String cargo;
    private Long idConcessionaria;
    private String concessionaria;
    private Long empresaId; // Campo ID Empresa adicionado aqui

    public static VendedorDTO create(Vendedor vendedor){
        VendedorDTO dto = new VendedorDTO();

        dto.setId(vendedor.getId());
        dto.setNome(vendedor.getNome());
        dto.setCpf(vendedor.getCpf());
        dto.setLogin(vendedor.getLogin());
        dto.setSenha(vendedor.getSenha());
        dto.setTelefone1(vendedor.getTelefone1());
        dto.setTelefone2(vendedor.getTelefone2());
        dto.setEmail1(vendedor.getEmail1());
        dto.setEmail2(vendedor.getEmail2());
        dto.setLogradouro(vendedor.getLogradouro());
        dto.setNumero(vendedor.getNumero());
        dto.setComplemento(vendedor.getComplemento());
        dto.setBairro(vendedor.getBairro());
        dto.setCep(vendedor.getCep());
        dto.setUf(vendedor.getUf());
        dto.setCargo(vendedor.getCargo());

        if(vendedor.getConcessionaria() != null){
            dto.setIdConcessionaria(vendedor.getConcessionaria().getId());
            dto.setConcessionaria(vendedor.getConcessionaria().getRazaoSocial());
        }

        if (vendedor.getConcessionaria() != null) {
            dto.setEmpresaId(vendedor.getConcessionaria().getEmpresa().getId());
        }
        return dto;
    }
}