package client;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    private String name;
    private List<Transaction> transactions;

    private List<Loan> myInvestmentLoans;
    private List<Loan> loansTaken;
    private List<Notification> notificationsList;

    public Account(){
        myInvestmentLoans = new ArrayList<>();
        loansTaken = new ArrayList<>();
        transactions = new ArrayList<>();
        notificationsList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Loan> getMyInvestmentLoans() {
        return myInvestmentLoans;
    }

    public void addToInvestmentLoans(Loan loan) {
        myInvestmentLoans.add(loan);
    }

    public List<Loan> getLoansTaken() {
        return loansTaken;
    }

    public void addLoansTaken(Loan loan) {
        loansTaken.add(loan);
    }

    public List<Notification> getNotificationsList() {
        return notificationsList;
    }

    public void addNotificationToList(Notification notification) {
        notificationsList.add(notification);
    }
}
