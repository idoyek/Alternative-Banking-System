package DTO;

import client.Loan;
import client.Notification;
import client.Transaction;
import client.Yaz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientScreenRefresherDTO {
    private List<Loan> loanerLoansList;
    private List<Loan> lenderLoansList;
    private List<Transaction> transactionsList;
    private Set<Loan> loansForSaleList;
    private List<String> categoriesList;
    private List<Loan> loanerLoansPaymentList;
    private List<Notification> notificationsList;
    private Yaz yaz;
    private boolean isInRewindMode;
    private boolean isClientDataNeedToSeen;

    public ClientScreenRefresherDTO(List<Loan> loanerLoansList, List<Loan> lenderLoansList, List<Transaction> transactionsList, Set<Loan> loansForSaleList, List<String> categoriesList, List<Loan> loanerLoansPaymentList, List<Notification> notificationsList, Yaz yaz, boolean isInRewindMode, boolean isClientDataNeedToSeen)
    {
/*
        this.loanerLoansList = new ArrayList<>(loanerLoansList);
        this.lenderLoansList = new ArrayList<>(lenderLoansList);
        this.transactionsList = new ArrayList<>(transactionsList);
        this.loansForSaleList = new HashSet<>(loansForSaleList);
        this.categoriesList = new ArrayList<>(categoriesList);
        this.loanerLoansPaymentList = new ArrayList<>(loanerLoansPaymentList);
        this.notificationsList = new ArrayList<>(notificationsList);
*/
        this.loanerLoansList = loanerLoansList;
        this.lenderLoansList = lenderLoansList;
        this.transactionsList = transactionsList;
        this.loansForSaleList = loansForSaleList;
        this.categoriesList = categoriesList;
        this.loanerLoansPaymentList = loanerLoansPaymentList;
        this.notificationsList = notificationsList;
        this.yaz = yaz;
        this.isInRewindMode = isInRewindMode;
        this.isClientDataNeedToSeen = isClientDataNeedToSeen;
    }

    public List<Loan> getLoanerLoansList() {
        return loanerLoansList;
    }

    public List<Loan> getLenderLoansList() {
        return lenderLoansList;
    }

    public List<Transaction> getTransactionsList() {
        return transactionsList;
    }

    public Set<Loan> getLoansForSaleList() {
        return loansForSaleList;
    }

    public List<String> getCategoriesList() {
        return categoriesList;
    }

    public List<Loan> getLoanerLoansPaymentList() {
        return loanerLoansPaymentList;
    }

    public List<Notification> getNotificationsList() {
        return notificationsList;
    }

    public Yaz getYaz() {
        return yaz;
    }

    public boolean getIsInRewindMode() {
        return isInRewindMode;
    }

    public boolean getIsClientDataNeedToSeen() {
        return isClientDataNeedToSeen;
    }

    @Override
    public String toString() {
        return "ClientScreenRefresherDTO{" +
                "loanerLoansList=" + loanerLoansList +
                ", lenderLoansList=" + lenderLoansList +
                ", transactionsList=" + transactionsList +
                ", loansForSaleList=" + loansForSaleList +
                ", categoriesList=" + categoriesList +
                ", loanerLoansPaymentList=" + loanerLoansPaymentList +
                ", notificationsList=" + notificationsList +
                ", yaz=" + yaz +
                '}';
    }
}
