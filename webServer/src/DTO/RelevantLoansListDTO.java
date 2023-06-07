package DTO;

import java.util.ArrayList;
import java.util.List;

public class RelevantLoansListDTO {
    private int minimumInterest, minimumYaz, maxOpenLoans;
    private List<String> userCategoriesList;

    public RelevantLoansListDTO(List<String> userCategoriesList, int minimumInterest, int minimumYaz, int maxOpenLoans){
        this.userCategoriesList = new ArrayList<>(userCategoriesList);
        this.minimumInterest = minimumInterest;
        this.minimumYaz = minimumYaz;
        this.maxOpenLoans = maxOpenLoans;
    }

    public int getMinimumInterest() {
        return minimumInterest;
    }

    public int getMinimumYaz() {
        return minimumYaz;
    }

    public int getMaxOpenLoans() {
        return maxOpenLoans;
    }

    public List<String> getUserCategoriesList() {
        return userCategoriesList;
    }

    @Override
    public String toString() {
        return "RelevantLoansListDTO{" +
                "minimumInterest=" + minimumInterest +
                ", minimumYaz=" + minimumYaz +
                ", maxOpenLoans=" + maxOpenLoans +
                ", userCategoriesList=" + userCategoriesList +
                '}';
    }
}
