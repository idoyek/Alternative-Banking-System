package DTO;

import client.Loan;

import java.util.ArrayList;
import java.util.List;

public class ScrambleDTOwebApp {
    private List<String> loansToInvestList;
    private int amountToInvest, maxOwnershipPercentage;

    public ScrambleDTOwebApp(List<String> loansToInvestList, int amountToInvest, int maxOwnershipPercentage){
        this.loansToInvestList = new ArrayList<>(loansToInvestList);
        this.amountToInvest = amountToInvest;
        this.maxOwnershipPercentage = maxOwnershipPercentage;
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

    @Override
    public String toString() {
        return "ScrambleDTO{" +
                "loansToInvestList=" + loansToInvestList +
                ", amountToInvest=" + amountToInvest +
                ", maxOwnershipPercentage=" + maxOwnershipPercentage +
                '}';
    }
}
