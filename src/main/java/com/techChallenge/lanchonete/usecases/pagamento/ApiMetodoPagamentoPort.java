package com.techChallenge.lanchonete.usecases.pagamento;

import java.io.IOException;

public interface ApiMetodoPagamentoPort <PagamentoQRCodeRequestDTO>  {

    String fazerRequisicaoMercadoPago(PagamentoQRCodeRequestDTO pagamentoQRCodeRequestDTO) throws IOException;

}
