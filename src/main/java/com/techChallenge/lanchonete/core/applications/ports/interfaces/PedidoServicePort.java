package com.techChallenge.lanchonete.core.applications.ports.interfaces;

import com.techChallenge.lanchonete.core.applications.Enum.StatusPedido;
import com.techChallenge.lanchonete.core.applications.dtos.in.ConfirmacaoPagamentoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.in.PedidoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.out.PedidoOutDTO;

import java.util.List;

public interface PedidoServicePort extends ServiceInterface<PedidoDTO, PedidoOutDTO>{


    void pagamentoPedidoConfirmado(ConfirmacaoPagamentoDTO confirmacaoPagamentoDTO);


    List<PedidoOutDTO> buscarPorStatus(StatusPedido statusPedido);


}
