package com.techChallenge.lanchonete.core.applications.ports.gateways;

import java.io.IOException;

public interface ApiMetodoPagamentoPort <PagamentoQRCodeRequestDTO>  {

    String fazerRequisicaoMercadoPago(PagamentoQRCodeRequestDTO pagamentoQRCodeRequestDTO) throws IOException;

}
