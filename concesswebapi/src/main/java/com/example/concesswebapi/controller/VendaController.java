package com.example.concesswebapi.controller;

import com.example.concesswebapi.Model.Entity.Cliente;
import com.example.concesswebapi.Model.Entity.ItensVenda;
import com.example.concesswebapi.Model.Entity.ModeloVeiculo;
import com.example.concesswebapi.Model.Entity.Veiculo;
import com.example.concesswebapi.Model.Entity.Venda;
import com.example.concesswebapi.Model.Entity.Vendedor;
import com.example.concesswebapi.api.dto.ItensVendaDTO;
import com.example.concesswebapi.api.dto.VendaDTO;
import com.example.concesswebapi.api.dto.VendaListagemDTO;
import com.example.concesswebapi.exception.RegraNegocioException;
import com.example.concesswebapi.service.ClienteService;
import com.example.concesswebapi.service.ItensVendaService;
import com.example.concesswebapi.service.ModeloVeiculoService;
import com.example.concesswebapi.service.VeiculoService;
import com.example.concesswebapi.service.VendaService;
import com.example.concesswebapi.service.VendedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vendas")
public class VendaController {

    private final VendaService vendaService;
    private final ClienteService clienteService;
    private final VendedorService vendedorService;
    private final ItensVendaService itensVendaService;
    private final ModeloVeiculoService modeloVeiculoService;
    private final VeiculoService veiculoService;

    public VendaController(VendaService vendaService, ClienteService clienteService, VendedorService vendedorService,
                           ItensVendaService itensVendaService, ModeloVeiculoService modeloVeiculoService, VeiculoService veiculoService) {
        this.vendaService = vendaService;
        this.clienteService = clienteService;
        this.vendedorService = vendedorService;
        this.itensVendaService = itensVendaService;
        this.modeloVeiculoService = modeloVeiculoService;
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<List<VendaListagemDTO>> get() {
        List<Venda> vendas = vendaService.getVendas();
        List<VendaListagemDTO> dtoList = vendas.stream()
                .map(VendaListagemDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Venda> vendaOptional = vendaService.getVendaById(id);
        if (vendaOptional.isEmpty()) {
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        VendaDTO dto = toVendaDTO(vendaOptional.get());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody VendaDTO dto) {
        try {
            Venda novaVenda = fromDTO(dto);
            vendaService.salvar(novaVenda);
            return new ResponseEntity<>(VendaListagemDTO.create(novaVenda), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody VendaDTO dto) {
        return vendaService.getVendaById(id).map(vendaExistente -> {
            try {
                Venda atualizada = fromDTO(dto);
                atualizada.setId(vendaExistente.getId());
                vendaService.salvar(atualizada);
                return ResponseEntity.ok(VendaListagemDTO.create(atualizada));
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return vendaService.getVendaById(id).map(venda -> {
            vendaService.excluir(venda);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND));
    }

    private Venda fromDTO(VendaDTO dto) {
        Venda venda = new Venda();
        venda.setData(dto.getData());
        venda.setFormaPag(dto.getFormaPag());
        venda.setDescontoTotal(dto.getDesconto());
        venda.setAprovada("não");

        Cliente cliente = clienteService.getClienteById(dto.getClienteId())
                .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado"));
        venda.setCliente(cliente);

        Vendedor vendedor = vendedorService.getVendedorById(dto.getVendedorId())
                .orElseThrow(() -> new RegraNegocioException("Vendedor não encontrado"));
        venda.setVendedor(vendedor);

        if (dto.getItens() != null) {
            List<ItensVenda> itens = dto.getItens().stream().map(itemDTO -> {
                Veiculo veiculo = veiculoService.getVeiculoById(itemDTO.getVeiculoId())
                        .orElseThrow(() -> new RegraNegocioException("Veículo não encontrado."));

                ModeloVeiculo modelo = veiculo.getModeloVeiculo();
                if (modelo == null) {
                    throw new RegraNegocioException("Veículo com chassi " + veiculo.getChassi() + " não possui um modelo associado.");
                }

                ItensVenda itemVenda = new ItensVenda();
                itemVenda.setVeiculo(veiculo);
                itemVenda.setModeloVeiculo(modelo);
                itemVenda.setDescontoParcial(itemDTO.getDescontoParcial());
                itemVenda.setVenda(venda);
                return itemVenda;
            }).collect(Collectors.toList());
            venda.setItensVenda(itens);
        }
        return venda;
    }

    private ItensVendaDTO toItemDTO(ItensVenda item) {
        return new ItensVendaDTO(
                item.getId(),
                item.getModeloVeiculo().getModelo().getId(),
                item.getVeiculo().getId(),
                item.getVenda().getId(),
                item.getDescontoParcial()
        );
    }

    private VendaDTO toVendaDTO(Venda venda) {
        VendaDTO dto = new VendaDTO();
        dto.setId(venda.getId());
        dto.setData(venda.getData());
        dto.setFormaPag(venda.getFormaPag());
        dto.setDesconto(venda.getDescontoTotal());

        if (venda.getCliente() != null) {
            dto.setClienteId(venda.getCliente().getId());
        }
        if (venda.getVendedor() != null) {
            dto.setVendedorId(venda.getVendedor().getId());
        }
        if (venda.getItensVenda() != null) {
            List<ItensVendaDTO> itensDto = venda.getItensVenda().stream()
                    .map(this::toItemDTO)
                    .collect(Collectors.toList());
            dto.setItens(itensDto);
        }
        return dto;
    }
}