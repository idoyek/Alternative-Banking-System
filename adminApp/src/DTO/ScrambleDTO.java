package DTO;

import client.Loan;

import java.util.ArrayList;
import java.util.List;

public class ScrambleDTO {
    private List<Loan> loansToInvestList;
    private int amountToInvest, maxOwnershipPercentage;

    public ScrambleDTO(List<Loan> loansToInvestList, int amountToInvest, int maxOwnershipPercentage){
        this.loansToInvestList = new ArrayList<>(loansToInvestList);
        this.amountToInvest = amountToInvest;
        this.maxOwnershipPercentage = maxOwnershipPercentage;
    }

    public List<Loan> getLoansToInvestList() {
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
