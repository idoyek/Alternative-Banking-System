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
    List<Loan> loanerLoansList;
    List<Loan> lenderLoansList;
    List<Transaction> transactionsList;
    Set<Loan> loansForSaleList;
    List<String> categoriesList;
    List<Loan> loanerLoansPaymentList;
    List<Notification> notificationsList;
    Yaz yaz;

    public ClientScreenRefresherDTO(List<Loan> loanerLoansList, List<Loan> lenderLoansList, List<Transaction> transactionsList, Set<Loan> loansForSaleList, List<String> categoriesList, List<Loan> loanerLoansPaymentList, List<Notification> notificationsList, Yaz yaz)
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
