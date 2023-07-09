package com.techChallenge.lanchonete.adapter.controller;


import com.techChallenge.lanchonete.core.applications.dtos.in.ConfirmacaoPagamentoDTO;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.PedidoServicePort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PagamentoController {


    private final PedidoServicePort pedidoServicePort;


    @PostMapping("/notificacoes/pagamento")
    public ResponseEntity<Void> receberNotificacaoPagamento(@RequestBody ConfirmacaoPagamentoDTO ConfirmacaoPagamentoDTO) {

        pedidoServicePort.pagamentoPedidoConfirmado(ConfirmacaoPagamentoDTO);

        return ResponseEntity.ok().build();
    }
}



