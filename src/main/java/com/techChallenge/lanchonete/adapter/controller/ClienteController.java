package com.techChallenge.lanchonete.adapter.controller;

import com.techChallenge.lanchonete.core.applications.dtos.in.BuscaCpfEmailDTO;
import com.techChallenge.lanchonete.core.applications.dtos.in.ClienteDTO;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.ClienteServicePort;
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


    private final ClienteServicePort clienteServicePort;


    @Override
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAll() {
        return ResponseEntity.ok(clienteServicePort.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> get(@PathVariable("id")Long id) {
        ClienteDTO clienteDTO = clienteServicePort.findById(id);

        if (clienteDTO != null) return ResponseEntity.ok(clienteDTO);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<ClienteDTO> post(@Valid @RequestBody ClienteDTO clienteDTO) {
        clienteServicePort.create(clienteDTO);
        return ResponseEntity.ok(clienteDTO);
    }


    @Override
    @PutMapping
    public ResponseEntity<?>  put(@Valid @RequestBody ClienteDTO clienteDTO) {

        try {

            if (clienteServicePort.update(clienteDTO)) {
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
        if (clienteServicePort.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("/busca/cpf/email")
    public ResponseEntity<?> buscaPorCpfOuEmail(@Valid @RequestBody BuscaCpfEmailDTO buscaCpfEmailDTO) {

        try {
            ClienteDTO clienteDTO = clienteServicePort.findByCpfEmail(buscaCpfEmailDTO);

            if (clienteDTO != null)
                return ResponseEntity.ok(clienteDTO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cpf ou Email informado não encontrado");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na Solicitação!!  "+ e.getMessage()); // -> erro em geral, se mandar json errado (400)
        }
    }

}
