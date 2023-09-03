package com.techChallenge.lanchonete.usecases.pedido;

import com.techChallenge.lanchonete.usecases.UseCaseInterface;
import com.techChallenge.lanchonete.usecases.pagamento.ConfirmacaoPagamentoDTO;

import java.util.List;

public interface PedidoUseCasePort extends UseCaseInterface<PedidoDTO, PedidoOutDTO> {


    void pagamentoPedidoConfirmado(ConfirmacaoPagamentoDTO confirmacaoPagamentoDTO);


    List<PedidoOutDTO> buscarPorStatus(StatusPedido statusPedido);

    boolean alteraStatusPedidoPronto (Long id);

    boolean alteraStatusPedidoFinalizado (Long id);

    boolean pedidosClienteEmAberto (Long id);

    boolean pedidoEmAbertoComProduto (Long id);


}
