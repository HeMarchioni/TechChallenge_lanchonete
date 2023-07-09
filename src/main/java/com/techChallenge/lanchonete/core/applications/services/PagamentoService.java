package com.techChallenge.lanchonete.core.applications.services;


import com.techChallenge.lanchonete.core.applications.dtos.in.PagamentoQRCodeRequestDTO;
import com.techChallenge.lanchonete.core.applications.ports.gateways.ApiMetodoPagamentoPort;
import com.techChallenge.lanchonete.core.applications.ports.interfaces.PagamentoServicePort;
import com.techChallenge.lanchonete.core.domain.Pedido;
import com.techChallenge.lanchonete.core.domain.Produto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class PagamentoService  implements PagamentoServicePort {


    private final ApiMetodoPagamentoPort<PagamentoQRCodeRequestDTO> apiMetodoPagamentoPort;
    private final String urlParaNotificacaoPagamento;



    public PagamentoService(ApiMetodoPagamentoPort<PagamentoQRCodeRequestDTO> apiMetodoPagamentoPort, @Value("${endpoint.comfirmacao.pagamento}") String urlParaNotificacaoPagamento) {
        this.apiMetodoPagamentoPort = apiMetodoPagamentoPort;
        this.urlParaNotificacaoPagamento = urlParaNotificacaoPagamento;

    }



    public void gerarQrCodeMercadoPago(Pedido pedido) throws IOException {

        PagamentoQRCodeRequestDTO pagamentoQRCodeRequestDTO = preencherPagamentoQRcodeRequest(pedido);

        String infoPagamento = apiMetodoPagamentoPort.fazerRequisicaoMercadoPago(pagamentoQRCodeRequestDTO);
        pedido.setInformacoesPagamento(infoPagamento);

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
