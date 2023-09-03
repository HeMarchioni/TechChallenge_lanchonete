package com.techChallenge.lanchonete.usecases.pagamento;

public class ConfirmacaoPagamentoDTO {

    private String id;   //O ID da transação gerado pelo Mercado Pago.
    private String status; //O status atual da transação, como "approved" (aprovado), "pending" (pendente) ou "rejected" (rejeitado).
    private String externalReference; // Uma referência externa associada à transação, como o ID do pedido no seu sistema.
    private Double transactionAmount; //  O valor total da transação.
    private String currencyId; // O código da moeda utilizada na transação, como "BRL" (Real brasileiro).
    private String description; // A descrição da transação.
    private String notificationType; // O tipo de notificação recebida, como "payment" (pagamento).
    private String payerId; // O ID do pagador.
    private String dateCreated; //  A data e hora de criação da transação.
    private String paymentMethodId; // O ID do método de pagamento utilizado. QR_CODE


    public ConfirmacaoPagamentoDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
