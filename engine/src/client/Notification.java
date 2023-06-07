package client;

import java.io.Serializable;

public class Notification implements Serializable {
    private String loanId;
    private int yazOfPayment;
    private int paymentWithInterest;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public int getYazOfPayment() {
        return yazOfPayment;
    }

    public void setYazOfPayment(int yazOfPayment) {
        this.yazOfPayment = yazOfPayment;
    }

    public int getPaymentWithInterest() {
        return paymentWithInterest;
    }

    public void setPaymentWithInterest(int paymentWithInterest) {
        this.paymentWithInterest = paymentWithInterest;
    }

    @Override
    public String toString() {
        return  "* Loan ID: " + loanId +
                ", Yaz Of Payment: " + yazOfPayment +
                ", Payment With Interest: " + paymentWithInterest +
                ".";
    }
}
