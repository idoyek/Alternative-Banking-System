package client;

import java.io.Serializable;

public class Investor implements Serializable {
    //private Client client;
    private String clientName;
    private int sum;
    //private int sumWithInterest;
    private int paymentWithInterest;
    private int investmentFundLeftToPay;

/*
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
*/

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getPaymentWithInterest() {
        return paymentWithInterest;
    }

    public void setPaymentWithInterest(int interestPerPayment) {
        this.paymentWithInterest = interestPerPayment;
    }

    public int getInvestmentFundLeftToPay() {
        return investmentFundLeftToPay;
    }

    public void setInvestmentFundLeftToPay(int investmentFundLeftToPay) {
        this.investmentFundLeftToPay = investmentFundLeftToPay;
    }

/*
    public int getSumWithInterest() {
        return sumWithInterest;
    }

    public void setSumWithInterest(int sumWithInterest) {
        this.sumWithInterest = sumWithInterest;
    }
*/

    @Override
    public String toString() {
        return  "Client's name=" + /*client.getName()*/ clientName +
                ", Investment amount=" + sum;
    }
}
