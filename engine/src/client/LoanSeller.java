package client;

import java.io.Serializable;

public class LoanSeller implements Serializable {
    private String loanSellerName;
    private Loan loanForSale;

    public String getLoanSellerName() {
        return loanSellerName;
    }

    public void setLoanSellerName(String loanSellerName) {
        this.loanSellerName = loanSellerName;
    }

    public Loan getLoanForSale() {
        return loanForSale;
    }

    public void setLoanForSale(Loan loanForSale) {
        this.loanForSale = loanForSale;
    }
}
