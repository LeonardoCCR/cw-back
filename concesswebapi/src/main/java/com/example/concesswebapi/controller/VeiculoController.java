package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.*;
import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.api.dto.*;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final ModeloService modeloService;
    private final ModelMapper modelMapper = new ModelMapper();
    private final ConcessionariaService concessionariaService;
    private final ModeloVeiculoService modeloVeiculoService;
    private final TipoVeiculoService tipoVeiculoService;

    public VeiculoController(VeiculoService veiculoService, ModeloService modeloService, ConcessionariaService concessionariaService, ModeloVeiculoService modeloVeiculoService, TipoVeiculoService tipoVeiculoService) {

        this.veiculoService = veiculoService;
        this.modeloService = modeloService;
        this.concessionariaService = concessionariaService;
        this.modeloVeiculoService = modeloVeiculoService;
        this.tipoVeiculoService = tipoVeiculoService;
    }

    @GetMapping
    public ResponseEntity listarTodos() {

        List<Veiculo> veiculos = veiculoService.listarTodos();
        return ResponseEntity.ok(veiculos.stream().map(VeiculoListagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Veiculo> veiculo = veiculoService.getVeiculoById(id);
        if (veiculo.isEmpty()) {
            return new ResponseEntity("Veiculo não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(veiculo.map(VeiculoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody VeiculoDTO dto) {
        try {
            Veiculo veiculo = converter(dto);
            tipoVeiculoService.salvar(veiculo.getModeloVeiculo().getTipoVeiculo());
            modeloVeiculoService.salvar(veiculo.getModeloVeiculo());
            veiculoService.salvar(veiculo);
            return new ResponseEntity(veiculo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody VeiculoDTO dto) {

        Optional<Veiculo> veiculoById = veiculoService.getVeiculoById(id);

        if (veiculoById.isEmpty()) {
            return new ResponseEntity("Veículo não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            this.verificaInconsistenciasDados(id, dto, veiculoById.get());
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        Veiculo veiculo;
        try {
            veiculo = converter(dto);
            veiculo.setId(id);

            TipoVeiculo tipoVeiculo = tipoVeiculoService.salvar(veiculo.getModeloVeiculo().getTipoVeiculo());
            veiculo.getModeloVeiculo().setTipoVeiculo(tipoVeiculo);

            ModeloVeiculo modeloVeiculo = modeloVeiculoService.salvar(veiculo.getModeloVeiculo());
            veiculo.setModeloVeiculo(modeloVeiculo);

            veiculoService.salvar(veiculo);
            return ResponseEntity.ok(veiculo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Veiculo converter(VeiculoDTO dto) {

        Veiculo veiculo;

        if ("usado".equalsIgnoreCase(dto.getCondicao())) {
            veiculo = dto.getVeiculoUsado().converter(dto.getVeiculoUsado(), (VeiculoUsado) dto.converter(dto));

        } else if ("novo".equalsIgnoreCase(dto.getCondicao())) {
            veiculo = dto.converter(dto);

        } else {
            throw new RegraNegocioException("O campo condição é obrigatório");
        }

        this.converteConcessionaria(dto.getConcessionariaId(), veiculo);
        this.converteModeloVeiculo(dto.getModeloVeiculo(), veiculo);

        return veiculo;
    }

    private void converteConcessionaria(Long idConcessionaria, Veiculo veiculo) {
        if (idConcessionaria != null) {
            Concessionaria concessionaria = concessionariaService.getConcessionariaById(idConcessionaria)
                    .orElseThrow(() -> new RegraNegocioException("Concessionária não encontrada"));

            veiculo.setConcessionaria(concessionaria);
        } else {
            throw new RegraNegocioException("Concessionária inválida");
        }
    }

    private void converteModeloVeiculo(ModeloVeiculoDTO dto, Veiculo veiculo) {
        if (dto != null) {
            if (dto.getModeloId() != null) {

                Modelo modelo = modeloService.getModeloById(dto.getModeloId())
                        .orElseThrow(() -> new RegraNegocioException("Modelo não encontrado"));

                ModeloVeiculo modeloVeiculo = dto.converter(dto);
                modeloVeiculo.setModelo(modelo);
                System.out.println("Modelo atualizado: " + modelo);
                modeloVeiculo.setTipoVeiculo(this.converteTipoVeiculo(dto.getTipoVeiculo(), veiculo));
                veiculo.setModeloVeiculo(modeloVeiculo);
            } else {
                throw new RegraNegocioException("O campo modelo é obrigatório");
            }
        } else {
            throw new RegraNegocioException("Informações de veículo incompletas");
        }
    }

    private TipoVeiculo converteTipoVeiculo(TipoVeiculoDTO dto, Veiculo veiculo) {

        if (dto != null) {
            TipoVeiculo tipoVeiculo;
            if ("carro".equalsIgnoreCase(dto.getTipo())) {
                if (dto.getCarro() == null) {
                    throw new RegraNegocioException("Informações de carro são obrigatórias");
                }
                Carro carro = modelMapper.map(dto.getCarro(), Carro.class);
                return tipoVeiculo = dto.converter(dto, carro);

            } else if ("moto".equalsIgnoreCase(dto.getTipo())) {
                if (dto.getMoto() == null) {
                    throw new RegraNegocioException("Informações de moto são obrigatórias");
                }
                Moto moto = dto.getMoto().converter(dto.getMoto());
                return tipoVeiculo = dto.converter(dto, moto);
            } else {
                throw new RegraNegocioException("O campo tipo é obrigatório");
            }
        } else {
            throw new RegraNegocioException("Informações de veículo incompletas");
        }
    }

    private void verificaInconsistenciasDados(Long id, VeiculoDTO dto, Veiculo veiculo) {

        if (veiculo instanceof VeiculoUsado) {
            if (dto.getVeiculoUsado() == null) {
                throw new RegraNegocioException("Um veículo usado deve conter atributos de veículo usado, e não de veículo novo");
            }
        } else if (veiculo instanceof VeiculoNovo) {
            if (dto.getVeiculoUsado() != null) {
                throw new RegraNegocioException("Um veículo novo deve conter atributos de veículo novo, e não de veículo usado");
            }
        }

        if (veiculo.getModeloVeiculo().getTipoVeiculo() instanceof Carro) {
            if (dto.getModeloVeiculo().getTipoVeiculo().getCarro() == null) {
                throw new RegraNegocioException("Um carro deve conter atributos de carro, e não de moto");
            }
        } else if (veiculo.getModeloVeiculo().getTipoVeiculo() instanceof Moto) {
            if (dto.getModeloVeiculo().getTipoVeiculo().getMoto() == null) {
                throw new RegraNegocioException("Uma moto deve conter atributos de moto, e não de carro");
            }
        }
    }
}

