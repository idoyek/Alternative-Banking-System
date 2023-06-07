package DTO;

import java.util.ArrayList;
import java.util.List;

public class RelevantLoansListDTO {
    String customerName;
    private int minimumInterest, minimumYaz, maxOpenLoans;
    private List<String> userCategoriesList;

    public RelevantLoansListDTO(String customerName, List<String> userCategoriesList, int minimumInterest, int minimumYaz, int maxOpenLoans){
        this.customerName = customerName;
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

}
