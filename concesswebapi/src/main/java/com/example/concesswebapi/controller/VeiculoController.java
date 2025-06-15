package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.*;
import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.api.dto.*;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(veiculos.stream().map(VeiculoResponseDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Veiculo> veiculo = veiculoService.getVeiculoById(id);
        if (veiculo.isEmpty()) {
            return new ResponseEntity("Veiculo não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(veiculo.map(VeiculoResponseDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody VeiculoRequestDTO dto) {
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

    private Veiculo converter(VeiculoRequestDTO dto) {

        Veiculo veiculo;

        if ("usado".equalsIgnoreCase(dto.getCondicao())) {
            veiculo = dto.getVeiculoUsado().converter(dto.getVeiculoUsado(), (VeiculoUsado) dto.converter(dto));

        } else if ("novo".equalsIgnoreCase(dto.getCondicao())) {
            veiculo = dto.converter(dto);

        } else {
            throw new RegraNegocioException("O campo condição é obrigatório");
        }

        if (dto.getConcessionariaId() != null) {
            Concessionaria concessionaria = concessionariaService.getConcessionariaById(dto.getConcessionariaId())
                    .orElseThrow(() -> new RegraNegocioException("Concessionária não encontrada"));

            veiculo.setConcessionaria(concessionaria);

            if (dto.getModeloVeiculo() != null && dto.getModeloVeiculo().getTipoVeiculo() != null) {
                if (dto.getModeloVeiculo().getModeloId() != null) {

                    Modelo modelo = modeloService.getModeloById(dto.getModeloVeiculo().getModeloId())
                            .orElseThrow(() -> new RegraNegocioException("Modelo não encontrado"));

                    ModeloVeiculo modeloVeiculo = dto.getModeloVeiculo().converter(dto.getModeloVeiculo());
                    modeloVeiculo.setModelo(modelo);

                    TipoVeiculo tipoVeiculo;
                    if ("carro".equalsIgnoreCase(dto.getModeloVeiculo().getTipoVeiculo().getTipo())) {
                        if (dto.getModeloVeiculo().getTipoVeiculo().getCarro() == null) {
                            throw new RegraNegocioException("Informações de carro são obrigatórias");
                        }
                        Carro carro = modelMapper.map(dto.getModeloVeiculo().getTipoVeiculo().getCarro(), Carro.class);
                        tipoVeiculo = dto.getModeloVeiculo().getTipoVeiculo().converter(dto.getModeloVeiculo().getTipoVeiculo(), carro);

                    } else if ("moto".equalsIgnoreCase(dto.getModeloVeiculo().getTipoVeiculo().getTipo())) {
                        if (dto.getModeloVeiculo().getTipoVeiculo().getMoto() == null) {
                            throw new RegraNegocioException("Informações de moto são obrigatórias");
                        }
                        Moto moto = dto.getModeloVeiculo().getTipoVeiculo().getMoto().converter(dto.getModeloVeiculo().getTipoVeiculo().getMoto());
                        tipoVeiculo = dto.getModeloVeiculo().getTipoVeiculo().converter(dto.getModeloVeiculo().getTipoVeiculo(), moto);
                    } else {
                        throw new RegraNegocioException("O campo tipo é obrigatório");
                    }

                    modeloVeiculo.setTipoVeiculo(tipoVeiculo);
                    veiculo.setModeloVeiculo(modeloVeiculo);
                    System.out.println("Veículo completo: " + veiculo);
                } else {
                    throw new RegraNegocioException("O campo modelo é obrigatório");
                }
            } else {
                throw new RegraNegocioException("Informações de veículo incompletas");
            }
        } else {
            throw new RegraNegocioException("Concessionária inválida");
        }

        return veiculo;
    }
}
