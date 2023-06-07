package client;

import java.io.Serializable;

public class Transaction implements Serializable {
    private int yaz;
    private int amount;
    private char type;
    private int preBalance;
    private int postBalance;

    public Transaction() {
        amount = 0;
    }

    public int getYaz() {
        return yaz;
    }

    public void setYaz(int yaz) {
        this.yaz = yaz;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public int getPreBalance() {
        return preBalance;
    }

    public void setPreBalance(int preBalance) {
        this.preBalance = preBalance;
    }

    public int getPostBalance() {
        return postBalance;
    }

    public void setPostBalance(int postBalance) {
        this.postBalance = postBalance;
    }

    @Override
    public String toString() {
        return  "yaz=" + yaz +
                ", amount=" + amount +
                ", type=" + type +
                ", preBalance=" + preBalance +
                ", postBalance=" + postBalance;
    }
}
