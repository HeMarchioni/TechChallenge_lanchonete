package com.techChallenge.lanchonete.controller;

import com.techChallenge.lanchonete.usecases.cliente.BuscaCpfEmailDTO;
import com.techChallenge.lanchonete.usecases.cliente.ClienteDTO;
import com.techChallenge.lanchonete.usecases.cliente.ClienteUseCasePort;
import com.techChallenge.lanchonete.usecases.pedido.PedidoUseCasePort;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteController implements ControllerInterface<ClienteDTO, ClienteDTO> {


    private final ClienteUseCasePort clienteUseCasePort;
    private final PedidoUseCasePort pedidoUseCasePort;


    @Override
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAll() {
        return ResponseEntity.ok(clienteUseCasePort.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> get(@PathVariable("id")Long id) {
        ClienteDTO clienteDTO = clienteUseCasePort.findById(id);

        if (clienteDTO != null) return ResponseEntity.ok(clienteDTO);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<ClienteDTO> post(@Valid @RequestBody ClienteDTO clienteDTO) {
        clienteUseCasePort.create(clienteDTO);
        return ResponseEntity.ok(clienteDTO);
    }


    @Override
    @PutMapping
    public ResponseEntity<?>  put(@Valid @RequestBody ClienteDTO clienteDTO) {

        try {

            if (clienteUseCasePort.update(clienteDTO)) {
                return ResponseEntity.ok(clienteDTO);
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

       try {

           if (pedidoUseCasePort.pedidosClienteEmAberto(id))
               throw new IllegalStateException("Não se pode apagar um cliente com um pedido em Aberto nao Finalzado");


           if (clienteUseCasePort.delete(id)) {
               return ResponseEntity.ok().build();
           }
       }
        catch (IllegalStateException e) {
               return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro conflito de regra!!  "+ e.getMessage()); // -> erro de conflito, (409)
           }
        catch (Exception e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na Solicitação!!  "+ e.getMessage()); // -> erro em geral, se mandar json errado (400)
           }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("/busca/cpf/email")
    public ResponseEntity<?> buscaPorCpfOuEmail(@Valid @RequestBody BuscaCpfEmailDTO buscaCpfEmailDTO) {

        try {
            ClienteDTO clienteDTO = clienteUseCasePort.findByCpfEmail(buscaCpfEmailDTO);

            if (clienteDTO != null)
                return ResponseEntity.ok(clienteDTO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cpf ou Email informado não encontrado");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na Solicitação!!  "+ e.getMessage()); // -> erro em geral, se mandar json errado (400)
        }
    }

}
