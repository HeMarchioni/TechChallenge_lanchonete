package com.techChallenge.lanchonete.controller;


import com.techChallenge.lanchonete.usecases.pagamento.ConfirmacaoPagamentoDTO;
import com.techChallenge.lanchonete.usecases.pedido.PedidoUseCasePort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PagamentoController {


    private final PedidoUseCasePort pedidoUseCasePort;


    @PostMapping("/notificacoes/pagamento")
    public ResponseEntity<Void> receberNotificacaoPagamento(@RequestBody ConfirmacaoPagamentoDTO ConfirmacaoPagamentoDTO) {

        pedidoUseCasePort.pagamentoPedidoConfirmado(ConfirmacaoPagamentoDTO);

        return ResponseEntity.ok().build();
    }
}



