package com.techChallenge.lanchonete.adapter.controller;

import com.techChallenge.lanchonete.core.applications.Enum.StatusPedido;
import com.techChallenge.lanchonete.core.applications.dtos.in.PedidoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.out.PedidoOutDTO;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.PedidoServicePort;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PedidoController implements ControllerInterface<PedidoDTO, PedidoOutDTO> {



    private final PedidoServicePort pedidoServicePort;


    @Override
    @GetMapping
    public ResponseEntity<List<PedidoOutDTO>> getAll() {
        return ResponseEntity.ok(pedidoServicePort.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PedidoOutDTO> get(@PathVariable("id")Long id) {

        PedidoOutDTO pedidoOutDTO = pedidoServicePort.findById(id);

        if (pedidoOutDTO != null) return ResponseEntity.ok(pedidoOutDTO);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<PedidoOutDTO>  post(@Valid @RequestBody PedidoDTO pedidoDTO) {
        PedidoOutDTO pedidoOutDTO = pedidoServicePort.create(pedidoDTO);
        return ResponseEntity.ok(pedidoOutDTO);
    }

    @Override
    @PutMapping
    public ResponseEntity<?> put(PedidoDTO pedidoDTO) {
        try {

            if (pedidoServicePort.update(pedidoDTO)) {
                return ResponseEntity.ok(pedidoDTO);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  //-> id nao encontrado (reposta 404)
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro conflito de regra!!  "+ e.getMessage()); // -> erro de conflito, (409)
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na Solicitação!!  "+ e.getMessage()); // -> erro em geral, se mandar json errado (400)
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id) {
        if (pedidoServicePort.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("status/{status}")
    public ResponseEntity<List<PedidoOutDTO>> get(@PathVariable("status")String status) {

        StatusPedido statusPedido;

        try {
            statusPedido = StatusPedido.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<PedidoOutDTO> produtos = pedidoServicePort.buscarPorStatus(statusPedido);

        if (produtos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(produtos);

    }

    @PutMapping("pronto/{id}")
    public ResponseEntity<?> pedidoPronto(@PathVariable("id")Long id) {

        try {
            if (pedidoServicePort.alteraStatusPedidoPronto(id)) {
                return ResponseEntity.ok().body("Pedido id:"+id+" Alterado para Pronto com Sucesso");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido com id:"+id+" não encontrado");  //-> id nao encontrado (reposta 404)
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro conflito de regra!!  "+ e.getMessage()); // -> erro de conflito, (409)
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na Solicitação!!  "+ e.getMessage()); // -> erro em geral, se mandar json errado (400)
        }

    }

    @PutMapping("finalizado/{id}")
    public ResponseEntity<?> pedidoFinalizado(@PathVariable("id")Long id) {

        try {
            if (pedidoServicePort.alteraStatusPedidoFinalizado(id)) {
                return ResponseEntity.ok().body("Pedido id:"+id+" Alterado para Finalizado com Sucesso");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido com id:"+id+" não encontrado");  //-> id nao encontrado (reposta 404)
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro conflito de regra!!  "+ e.getMessage()); // -> erro de conflito, (409)
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na Solicitação!!  "+ e.getMessage()); // -> erro em geral, se mandar json errado (400)
        }

    }



}
