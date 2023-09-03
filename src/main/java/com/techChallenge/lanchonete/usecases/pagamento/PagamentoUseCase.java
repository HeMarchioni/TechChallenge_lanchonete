package com.techChallenge.lanchonete.usecases.pagamento;


import com.techChallenge.lanchonete.usecases.pedido.Pedido;
import com.techChallenge.lanchonete.usecases.produto.Produto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class PagamentoUseCase implements PagamentoUseCasePort {


    private final ApiMetodoPagamentoPort<PagamentoQRCodeRequestDTO> apiMetodoPagamentoPort;
    private final String urlParaNotificacaoPagamento;



    public PagamentoUseCase(ApiMetodoPagamentoPort<PagamentoQRCodeRequestDTO> apiMetodoPagamentoPort, @Value("${endpoint.comfirmacao.pagamento}") String urlParaNotificacaoPagamento) {
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
