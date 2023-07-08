package com.techChallenge.lanchonete.core.applications.services;


import com.techChallenge.lanchonete.core.applications.dtos.in.PagamentoQRCodeRequestDTO;
import com.techChallenge.lanchonete.core.applications.ports.gateways.ApiMetodoPagamentoPort;
import com.techChallenge.lanchonete.core.domain.Pedido;
import com.techChallenge.lanchonete.core.domain.Produto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class PagamentoService {


    private final ApiMetodoPagamentoPort apiMetodoPagamentoPort;

    @Value("${mercado.pago.api.url}")
    private String urlParaNotificacaoPagamento;


    public PagamentoService(ApiMetodoPagamentoPort apiMetodoPagamentoPort) {
        this.apiMetodoPagamentoPort = apiMetodoPagamentoPort;
    }



    public String gerarQrCodeMercadoPago(Pedido pedido) throws IOException {

        PagamentoQRCodeRequestDTO pagamentoQRCodeRequestDTO = preencherPagamentoQRcodeRequest(pedido);


        return apiMetodoPagamentoPort.fazerRequisicaoMercadoPago(pagamentoQRCodeRequestDTO);

    }



    private PagamentoQRCodeRequestDTO preencherPagamentoQRcodeRequest(Pedido pedido) {

        PagamentoQRCodeRequestDTO pagamentoQRCodeRequestDTO = new PagamentoQRCodeRequestDTO();
        pagamentoQRCodeRequestDTO.setExternal_reference(String.valueOf(pedido.getId()));
        pagamentoQRCodeRequestDTO.setTransaction_amount(Double.valueOf(pedido.getValorTotal()));
        pagamentoQRCodeRequestDTO.setDescription(pedido.getListaProduto().stream().map(Produto::getNomeProduto).collect(Collectors.joining(", ")));
        pagamentoQRCodeRequestDTO.setNotification_url(urlParaNotificacaoPagamento);
        pagamentoQRCodeRequestDTO.setPayment_method_id("QR_CODE");
        pagamentoQRCodeRequestDTO.setPayer(pedido.getCliente()==null? "": pedido.getCliente().getCpf());
        pagamentoQRCodeRequestDTO.setBinary_mode("true");
        pagamentoQRCodeRequestDTO.setCurrency_id("BRL");

        return pagamentoQRCodeRequestDTO;

    }

}
