package DTO;

import client.Loan;

import java.util.ArrayList;
import java.util.List;

public class ScrambleDTO {
    String customerName;
    private List<String> loansToInvestList;
    private int amountToInvest, maxOwnershipPercentage;

    public ScrambleDTO(String customerName, List<String> loansToInvestList, int amountToInvest, int maxOwnershipPercentage){
        this.loansToInvestList = new ArrayList<>(loansToInvestList);
        this.amountToInvest = amountToInvest;
        this.maxOwnershipPercentage = maxOwnershipPercentage;
        this.customerName = customerName;
    }

    public List<String> getLoansToInvestList() {
        return loansToInvestList;
    }

    public int getAmountToInvest() {
        return amountToInvest;
    }

    public int getMaxOwnershipPercentage() {
        return maxOwnershipPercentage;
    }
}
