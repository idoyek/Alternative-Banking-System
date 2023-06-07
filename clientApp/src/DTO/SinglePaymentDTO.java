package DTO;

import client.Loan;

public class SinglePaymentDTO {
    private String loanToPayId;
    private int amountToPay;

    public SinglePaymentDTO(String loanToPayId, int amountToPay) {
        this.loanToPayId = loanToPayId;
        this.amountToPay = amountToPay;
    }

    public String getLoanToPayId() {
        return loanToPayId;
    }

    public int getAmountToPay() {
        return amountToPay;
    }

    @Override
    public String toString() {
        return "SinglePaymentDTO{" +
                "loanToPayId='" + loanToPayId + '\'' +
                ", amountToPay=" + amountToPay +
                '}';
    }
}
