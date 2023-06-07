package client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Loan implements Serializable {

    private String loanId;
    private String loanerName;
    private String loanGoal;
    private int totalSumOfLoan;
    private int totalYazTime;
    private int paysEveryYaz;
    private int interestPerPayment;

    private int interestMoneyPerPayment;
    private int totalInterestMoneyPerLoan;
    private int singlePayment;
    private int totalFundWithInterest;

    public enum Status {NEW, PENDING, ACTIVE, RISK, FINISHED};

    private Status status;
    private List<Investor> investorsList;
    private int totalBalance;
    private int missingMoney;

    private List<Payment> paymentsList; //////  need to update these data members after every loaner payment  after(7)
    private int fundPaymentsUntilNow;
    private int fundPaymentsUntilNowWithoutInterest;   /////////// added 25.5
    private int interestPaymentsUntilNow;
    private int fundLeftToPay;
    private int fundLeftToPayWithoutInterest;  /////////// added 25.5
    private int interestLeftToPay;


    ////  to do risk!!!!
    private int nextPaymentAccumulatedInRisk;
    private int nextPaymentAccumulatedInRiskWithoutInterest;
    private List<Payment> unpaidPayments;
    private int numOfUnpaidPayments;
    private int amountOfUnpaidPayments;

    private int startingYaz;
    private int endingYaz;
    private int nextYazOfPayment;

    public Loan(String loanerName, String loanId, String loanGoal, int totalSumOfLoan, int totalYazTime, int paysEveryYaz, int interestPerPayment) {
        this.loanerName = loanerName;
        this.loanGoal = loanGoal;
        this.loanId = loanId;
        this.totalSumOfLoan = totalSumOfLoan;
        this.totalYazTime = totalYazTime;
        this.paysEveryYaz = paysEveryYaz;
        this.interestPerPayment = interestPerPayment;
        this.missingMoney=totalSumOfLoan;////20.5.22

        investorsList = new ArrayList<>();
        paymentsList = new ArrayList<>();
        unpaidPayments = new ArrayList<>();
        status = Status.NEW;
    }

    public String getLoanerName() {
        return loanerName;
    }

    public void setLoanerName(String loanerName) {
        this.loanerName = loanerName;
    }

    public String getLoanGoal() {
        return loanGoal;
    }

    public void setLoanGoal(String loanGoal) {
        this.loanGoal = loanGoal;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public int getTotalYazTime() {
        return totalYazTime;
    }

    public void setTotalYazTime(int totalYazTime) {
        this.totalYazTime = totalYazTime;
    }

    public int getInterestPerPayment() {
        return interestPerPayment;
    }

    public void setInterestPerPayment(int interestPerPayment) {
        this.interestPerPayment = interestPerPayment;
    }

    public int getPaysEveryYaz() {
        return paysEveryYaz;
    }

    public void setPaysEveryYaz(int paysEveryYaz) {
        this.paysEveryYaz = paysEveryYaz;
    }

    public int getSinglePayment() {
        return singlePayment;
    }

    public void setSinglePayment(int singlePayment) {
        this.singlePayment = singlePayment;
    }

    public int getInterestMoneyPerPayment() {
        return interestMoneyPerPayment;
    }

    public void setInterestMoneyPerPayment(int interestMoneyPerPayment) {
        this.interestMoneyPerPayment = interestMoneyPerPayment;
    }

    public int getTotalInterestMoneyPerLoan() {
        return totalInterestMoneyPerLoan;
    }

    public void setTotalInterestMoneyPerLoan(int totalInterestMoneyPerLoan) {
        this.totalInterestMoneyPerLoan = totalInterestMoneyPerLoan;
    }

    public int getTotalFundWithInterest() {
        return totalFundWithInterest;
    }

    public void setTotalFundWithInterest(int totalFundWithInterest) {
        this.totalFundWithInterest = totalFundWithInterest;
    }

    public int getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }

    public int getTotalSumOfLoan() {
        return totalSumOfLoan;
    }

    public void setTotalSumOfLoan(int totalSumOfLoan) {
        this.totalSumOfLoan = totalSumOfLoan;
    }

    public int getMissingMoney() {
        return missingMoney;
    }

    public void setMissingMoney(int missingMoney) {
        this.missingMoney = missingMoney;
    }

    public List<Investor> getInvestorsList() {
        return investorsList;
    }

    public void addInvestorToList(Investor investor) {
        investorsList.add(investor);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Payment> getPaymentsList() {
        return paymentsList;
    }

    public void addToPaymentsList(Payment payment) {
        paymentsList.add(payment);
    }

    public int getFundPaymentsUntilNow() {
        return fundPaymentsUntilNow;
    }

    public void setFundPaymentsUntilNow(int fundPaymentsUntilNow) {
        this.fundPaymentsUntilNow = fundPaymentsUntilNow;
    }

    public int getFundPaymentsUntilNowWithoutInterest() {
        return fundPaymentsUntilNowWithoutInterest;
    }

    public void setFundPaymentsUntilNowWithoutInterest(int fundPaymentsUntilNowWithoutInterest) {
        this.fundPaymentsUntilNowWithoutInterest = fundPaymentsUntilNowWithoutInterest;
    }

    public int getInterestPaymentsUntilNow() {
        return interestPaymentsUntilNow;
    }

    public void setInterestPaymentsUntilNow(int interestPaymentsUntilNow) {
        this.interestPaymentsUntilNow = interestPaymentsUntilNow;
    }

    public int getFundLeftToPay() {
        return fundLeftToPay;
    }

    public void setFundLeftToPay(int fundLeftToPay) {
        this.fundLeftToPay = fundLeftToPay;
    }

    public int getFundLeftToPayWithoutInterest() {
        return fundLeftToPayWithoutInterest;
    }

    public void setFundLeftToPayWithoutInterest(int fundLeftToPayWithoutInterest) {
        this.fundLeftToPayWithoutInterest = fundLeftToPayWithoutInterest;
    }

    public int getInterestLeftToPay() {
        return interestLeftToPay;
    }

    public void setInterestLeftToPay(int interestLeftToPay) {
        this.interestLeftToPay = interestLeftToPay;
    }

    public int getNextPaymentAccumulatedInRisk() {
        return nextPaymentAccumulatedInRisk;
    }

    public void setNextPaymentAccumulatedInRisk(int nextPaymentAccumulatedInRisk) {
        this.nextPaymentAccumulatedInRisk = nextPaymentAccumulatedInRisk;
    }

    public int getNextPaymentAccumulatedInRiskWithoutInterest() {
        return nextPaymentAccumulatedInRiskWithoutInterest;
    }

    public void setNextPaymentAccumulatedInRiskWithoutInterest(int nextPaymentAccumulatedInRiskWithoutInterest) {
        this.nextPaymentAccumulatedInRiskWithoutInterest = nextPaymentAccumulatedInRiskWithoutInterest;
    }

    public List<Payment> getUnpaidPayments() {
        return unpaidPayments;
    }

    public void addToUnpaidPayments(Payment payment) {
        unpaidPayments.add(payment);
    }

    public int getNumOfUnpaidPayments() {
        return numOfUnpaidPayments;
    }

    public void setNumOfUnpaidPayments(int numOfUnpaidPayments) {
        this.numOfUnpaidPayments = numOfUnpaidPayments;
    }

    public int getAmountOfUnpaidPayments() {
        return amountOfUnpaidPayments;
    }

    public void setAmountOfUnpaidPayments(int amountOfUnpaidPayments) {
        this.amountOfUnpaidPayments = amountOfUnpaidPayments;
    }

    public int getStartingYaz() {
        return startingYaz;
    }

    public void setStartingYaz(int startingYaz) {
        this.startingYaz = startingYaz;
    }

    public int getNextYazOfPayment() {
        return nextYazOfPayment;
    }

    public void setNextYazOfPayment(int nextYazOfPayment) {
        this.nextYazOfPayment = nextYazOfPayment;
    }

    public int getEndingYaz() {
        return endingYaz;
    }

    public void setEndingYaz(int endingYaz) {
        this.endingYaz = endingYaz;
    }

    @Override
    public String toString() {
        return "loanerName='" + loanerName + '\'' +
                ", loanGoal='" + loanGoal + '\'' +
                ", loanId='" + loanId + '\'' +
                ", totalSumOfLoan=" + totalSumOfLoan +
                ", totalYazTime=" + totalYazTime +
                ", paysEveryYaz=" + paysEveryYaz +
                ", interestPerPayment=" + interestPerPayment +
                ", status=" + status;
    }


}
