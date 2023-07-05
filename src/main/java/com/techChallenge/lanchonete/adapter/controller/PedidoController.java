package com.techChallenge.lanchonete.adapter.controller;

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
    public ResponseEntity<PedidoDTO>  post(@Valid @RequestBody PedidoDTO pedidoDTO) {
        pedidoServicePort.create(pedidoDTO);
        return ResponseEntity.ok(pedidoDTO);
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

}
