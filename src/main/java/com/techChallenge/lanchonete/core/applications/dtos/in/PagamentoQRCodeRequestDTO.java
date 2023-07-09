package com.techChallenge.lanchonete.core.applications.dtos.in;

public class PagamentoQRCodeRequestDTO {

    private String external_reference;  // Referência externa da transação (id do pedido)
    private Double transaction_amount;  // Valor total da transação
    private String description; // Descrição do pagamento exibido para o comprador durante o processo de pagamento
    private String notification_url; // A URL de notificação que o Mercado Pago usará para enviar notificações de status de pagamento.
    private String payment_method_id; //identificador do método de pagamento a ser utilizado (por exemplo, "QR_CODE" para pagamento por QR code).
    private String payer; // Nome do pagador
    private String binary_mode; //Indica se o pagamento está no modo binário
    private String currency_id; //O código da moeda utilizada para o pagamento (por exemplo, "BRL" para Real Brasileiro).


    public PagamentoQRCodeRequestDTO() {
    }


    public String getExternal_reference() {
        return external_reference;
    }

    public void setExternal_reference(String external_reference) {
        this.external_reference = external_reference;
    }

    public Double getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(Double transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotification_url() {
        return notification_url;
    }

    public void setNotification_url(String notification_url) {
        this.notification_url = notification_url;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getBinary_mode() {
        return binary_mode;
    }

    public void setBinary_mode(String binary_mode) {
        this.binary_mode = binary_mode;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }
}
