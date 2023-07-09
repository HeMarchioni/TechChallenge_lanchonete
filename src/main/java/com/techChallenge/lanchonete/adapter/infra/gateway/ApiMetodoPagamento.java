package com.techChallenge.lanchonete.adapter.infra.gateway;

import com.google.gson.Gson;
import com.techChallenge.lanchonete.core.applications.dtos.in.ConfirmacaoPagamentoDTO;
import com.techChallenge.lanchonete.core.applications.dtos.in.PagamentoQRCodeRequestDTO;
import com.techChallenge.lanchonete.core.applications.ports.gateways.ApiMetodoPagamentoPort;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ThreadLocalRandom;

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
            //HttpResponse httpResponse = httpClient.execute(httpPost); (Não realiza Post usa a simulação)
            HttpResponse httpResponse = simulacaoPagamento(httpPost,pagamentoQRCodeRequestDTO);
            HttpEntity responseEntity = httpResponse.getEntity();

            String response = null;
            if (responseEntity != null) {
                response = EntityUtils.toString(responseEntity);
            }

            if (response == null) {
                throw new IOException();
            }

            return response;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Erro: ocorreu uma exceção ao fazer a requisição para a API do Mercado Pago", e);
        }
    }


    // Metodo para simulação para Api de Pagamento inves de fazer um post real para API do Mercado Pago
    // é gerado um qr_data para o QrCode de pagamento.
    public HttpResponse simulacaoPagamento(HttpPost httpPost,PagamentoQRCodeRequestDTO pagamentoQRCodeRequestDTO) throws IOException {

        HttpEntity existingEntity = httpPost.getEntity();
        String existingPayload = EntityUtils.toString(existingEntity);
        String modifiedPayload = existingPayload + ", \"qr_data\": \"00020101021243650016COM.MERCADOLIBRE02013063638f1192a-5fd1-4180-a180-8bcae3556bc35204000053039865802BR5925IZABEL AAAA DE MELO6007BARUERI62070503***63040B6D\"";

        HttpResponse httpResponse = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
        httpResponse.setEntity(new StringEntity(modifiedPayload));

        simulacaoConfirmacaoPagamento(pagamentoQRCodeRequestDTO );

        return httpResponse;
    }


    // Simulação de confirmação de pagamento, depois que o QRCode é pago a API do mercado pago confirma mandando uma requisição post
    // para o end Point passado para notificação.
    public void simulacaoConfirmacaoPagamento(PagamentoQRCodeRequestDTO pagamentoQRCodeRequestDTO) throws IOException {

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(pagamentoQRCodeRequestDTO.getNotification_url());
        httpPost.setHeader("Content-Type", "application/json");

        ConfirmacaoPagamentoDTO confirmacaoPagamentoDTO = new ConfirmacaoPagamentoDTO();
        confirmacaoPagamentoDTO.setId(String.valueOf(ThreadLocalRandom.current().nextLong()));
        confirmacaoPagamentoDTO.setStatus("approved");
        confirmacaoPagamentoDTO.setExternalReference(pagamentoQRCodeRequestDTO.getExternal_reference());
        confirmacaoPagamentoDTO.setTransactionAmount(pagamentoQRCodeRequestDTO.getTransaction_amount());
        confirmacaoPagamentoDTO.setCurrencyId(pagamentoQRCodeRequestDTO.getCurrency_id());
        confirmacaoPagamentoDTO.setDescription(pagamentoQRCodeRequestDTO.getDescription());
        confirmacaoPagamentoDTO.setNotificationType("payment");
        confirmacaoPagamentoDTO.setPayerId(pagamentoQRCodeRequestDTO.getPayer());
        confirmacaoPagamentoDTO.setDateCreated(String.valueOf(LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))));
        confirmacaoPagamentoDTO.setPaymentMethodId(pagamentoQRCodeRequestDTO.getPayment_method_id());

        String jsonPayload = new Gson().toJson(confirmacaoPagamentoDTO);
        httpPost.setEntity(new StringEntity(jsonPayload));



        HttpResponse httpResponse = httpClient.execute(httpPost);
    }

}
