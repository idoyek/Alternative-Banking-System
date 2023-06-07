package client;

import java.io.Serializable;

public class Payment implements Serializable {
    private int yazOfPayment;
    private int payment;
    private int interest;
    private int paymentWithInterest;

    public int getYazOfPayment() {
        return yazOfPayment;
    }

    public void setYazOfPayment(int yazOfPayment) {
        this.yazOfPayment = yazOfPayment;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public int getPaymentWithInterest() {
        return paymentWithInterest;
    }

    public void setPaymentWithInterest(int paymentWithInterest) {
        this.paymentWithInterest = paymentWithInterest;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "yazOfPayment=" + yazOfPayment +
                ", payment=" + payment +
                ", interest=" + interest +
                ", paymentWithInterest=" + paymentWithInterest +
                '}';
    }
}
