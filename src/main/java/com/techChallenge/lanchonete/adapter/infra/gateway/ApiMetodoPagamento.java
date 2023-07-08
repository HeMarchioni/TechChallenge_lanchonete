package com.techChallenge.lanchonete.adapter.infra.gateway;

import com.google.gson.Gson;
import com.techChallenge.lanchonete.core.applications.dtos.in.PagamentoQRCodeRequestDTO;
import com.techChallenge.lanchonete.core.applications.ports.gateways.ApiMetodoPagamentoPort;
import com.techChallenge.lanchonete.core.domain.Cliente;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiMetodoPagamento  implements ApiMetodoPagamentoPort <PagamentoQRCodeRequestDTO> {

    @Value("${mercado.pago.api.url}")
    private String mercadoPagoApiUrl;

    @Value("${mercado.pago.access.token}")
    private String accessToken;

    @Override
    public String fazerRequisicaoMercadoPago(PagamentoQRCodeRequestDTO pagamentoQRCodeRequestDTO) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(mercadoPagoApiUrl);
        httpPost.setHeader("Authorization", "Bearer " + accessToken);
        httpPost.setHeader("Content-Type", "application/json");

        String jsonPayload = new Gson().toJson(pagamentoQRCodeRequestDTO);
        httpPost.setEntity(new StringEntity(jsonPayload));

        try {
            //HttpResponse httpResponse = httpClient.execute(httpPost); (N realiza Post usa a simulação)
            HttpResponse httpResponse = simulacaoPagamento(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();

            String response = null;
            if (responseEntity != null) {
                response = EntityUtils.toString(responseEntity);
            }

            if (response == null) {
                response = "Erro: resposta vazia da API do Mercado Pago";
            }

            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro: ocorreu uma exceção ao fazer a requisição para a API do Mercado Pago";
        }
    }



    // Metodo para simulação para Api de Pagamento inves de fazer um post real para API do Mercado Pago
    // é gerado um qr_data para o QrCode e feito um post para o nosso end. Point de confirmação de pagamento
    public HttpResponse simulacaoPagamento (HttpPost httpPost) throws IOException {


        String notificationUrl = httpPost.getURI().toString();

        HttpEntity existingEntity = httpPost.getEntity();
        String existingPayload = EntityUtils.toString(existingEntity);

        String modifiedPayload = existingPayload + ", \"qr_data\": \"00020101021243650016COM.MERCADOLIBRE02013063638f1192a-5fd1-4180-a180-8bcae3556bc35204000053039865802BR5925IZABEL AAAA DE MELO6007BARUERI62070503***63040B6D\"";

        HttpResponse httpResponse = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
        httpResponse.setEntity(new StringEntity(modifiedPayload));

        return httpResponse;

    }

}
