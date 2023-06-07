package client;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Client implements Serializable {
    private String name;
    private int totalMoney;
    private Account account;
    private int loginYaz;

    public Client(String name, int totalMoney) {
        this.name = name;
        this.totalMoney = totalMoney;
        account = new Account();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Account getAccount() {
        return account;
    }

    public int getLoginYaz() {
        return loginYaz;
    }

    public void setLoginYaz(int loginYaz) {
        this.loginYaz = loginYaz;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", totalMoney=" + totalMoney;
    }

}