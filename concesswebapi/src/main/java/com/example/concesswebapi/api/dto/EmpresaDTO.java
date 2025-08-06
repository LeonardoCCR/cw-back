    package com.example.concesswebapi.api.dto;

    import com.example.concesswebapi.Model.Entity.Empresa;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.modelmapper.ModelMapper;

    import java.util.List;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class EmpresaDTO {

        private String razaoSocial;
        private String cnpj;

        private String email1;
        private String email2;
        private String telefone1;
        private String telefone2;

        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;
        private String uf;
        private String cep;

        private List<Long> admsEmpresaIds;
        private List<Long> vendedoresIds;
        private List<Long> gestoresIds;

        public static EmpresaDTO create(Empresa empresa) {
            ModelMapper modelMapper = new ModelMapper();
            EmpresaDTO dto = modelMapper.map(empresa, EmpresaDTO.class);

            dto.setRazaoSocial(empresa.getRazaoSocial());
            dto.setCnpj(empresa.getCnpj());

            dto.setEmail1(empresa.getEmail1());
            dto.setEmail2(empresa.getEmail2());
            dto.setTelefone1(empresa.getTelefone1());
            dto.setTelefone2(empresa.getTelefone2());

            dto.setLogradouro(empresa.getLogradouro());
            dto.setNumero(empresa.getNumero());
            dto.setComplemento(empresa.getComplemento());
            dto.setBairro(empresa.getBairro());
            dto.setUf(empresa.getUf());
            dto.setCep(empresa.getCep());

            return dto;
        }
    }

