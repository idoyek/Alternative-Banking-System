package client;

import chat.ChatManager;
import xml.Xml;
import schema.generated.AbsDescriptor;
import schema.generated.AbsLoan;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.*;

public class Database implements Serializable {
    private Map<String, Client> clientsMap;
    private Map<String, Loan> loansMap;
    private List<String> categoriesList;
    //private Map<Loan,String> loansForSaleMap;
    //private Map<String, Loan> loansForSaleMap;
    private Map<String, LoanSeller> loansForSaleMap;

    private Yaz yaz;
    private Xml xml;

    private ChatManager chatManager;

    public ChatManager getChatManager() {
        return chatManager;
    }

    public Database() {
        clientsMap = new HashMap<>();
        yaz = new Yaz();
        loansMap = new HashMap<>();
        categoriesList = new ArrayList<>();
        xml = new Xml();
        loansForSaleMap = new HashMap<>();

        chatManager = new ChatManager();

//        Client client = new Client("ido", 0);
//        Loan loan = new Loan("ido", "Wedding", "Wedding", 10000, 10, 2, 5);
//        loansMap.put("Wedding", loan);
//        client.getAccount().addLoansTaken(loan);
//        clientsMap.put("ido", client);

    }

    public Map<String, Loan> getLoansMap() {
        return loansMap;
    }

    public void addLoanToMap(Loan loan) {
        loansMap.put(loan.getLoanId(), loan);
    }

    public Map<String, Client> getClientsMap() {
        return clientsMap;
    }

    public void addClientToMap(Client client) {
        clientsMap.put(client.getName(), client);
    }

    public List<String> getCategoriesList() {
        return categoriesList;
    }

    public void addCategoryToList(String category) {
        categoriesList.add(category);
    }

/*
    public Map<Loan, String> getLoansForSaleMap() {
        return loansForSaleMap;
    }

    public void addToLoansForSaleMap(Loan loanForSale, String sellerName) {
        loansForSaleMap.put(loanForSale,sellerName);
    }
*/

/*
    public Map<String, Loan> getLoansForSaleMap() {
        return loansForSaleMap;
    }

    public void addToLoansForSaleMap(String LoanId, Loan loanForSale) {
        loansForSaleMap.put(LoanId, loanForSale);
    }
*/

    public Map<String, LoanSeller> getLoansForSaleMap() {
        return loansForSaleMap;
    }

    public void addToLoansForSaleMap(String LoanId, LoanSeller loanForSale) {
        loansForSaleMap.put(LoanId, loanForSale);
    }

    public Yaz getYaz() {
        return yaz;
    }

    public void setYaz(Yaz yaz) {
        this.yaz = yaz;
    }

    public Xml getXml() {
        return xml;
    }

    public void setXml(Xml xml) {
        this.xml = xml;
    }

    public boolean copyClassesFromXml(InputStream inputStream, String clientName) {
        boolean isCopySucceeded;
        AbsDescriptor descriptor = xml.deserialization(inputStream);

        xml.setCheckYaz(checkLoansYazDivision(descriptor));
        xml.setCheckDuplicateLoans(checkDuplicateLoans(descriptor));

        isCopySucceeded = xml.getCheckYaz() && xml.getCheckDuplicateLoans();
        if (isCopySucceeded){
            addCustomerLoansToMapAndToLoansTaken(descriptor, clientName);
            addCategories(descriptor);
        }

        return isCopySucceeded;
    }

    public boolean checkLoansYazDivision(AbsDescriptor descriptor) {
        for (AbsLoan absLoan : descriptor.getAbsLoans().getAbsLoan()) {
            double yazDivision = (double) absLoan.getAbsTotalYazTime() / absLoan.getAbsPaysEveryYaz();
            if (yazDivision % 1 != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDuplicateLoans(AbsDescriptor descriptor) {
        for (AbsLoan absLoan : descriptor.getAbsLoans().getAbsLoan()) {
            if (loansMap.containsKey(absLoan.getId())){
                return false;
            }
        }
        return true;
    }


/*
    public String getFileType(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
*/

    public void clearAll() {
//        clientsMap.clear();
//        loansMap.clear();
//        categoriesList.clear();
//        loansForSaleMap.clear();
//
//        xml.setCheckCategories(false);
//        xml.setCheckClients(false);
//        xml.setCheckYaz(false);
//        xml.setCheckClientDuplicate(false);
    }

    public void addCustomerLoansToMapAndToLoansTaken(AbsDescriptor descriptor, String clientName) {
        for (AbsLoan absLoan : descriptor.getAbsLoans().getAbsLoan()) {
            Loan loan = new Loan(clientName, absLoan.getId().trim(), absLoan.getAbsCategory(),
                    absLoan.getAbsCapital(), absLoan.getAbsTotalYazTime(), absLoan.getAbsPaysEveryYaz(),
                    absLoan.getAbsIntristPerPayment());
            loansMap.put(loan.getLoanId(), loan);
            clientsMap.get(clientName).getAccount().addLoansTaken(loan);
            System.out.println(loan);
        }
        System.out.println(loansMap);
        System.out.println(clientsMap.get(clientName).getAccount().getLoansTaken());
    }

    public void addCategories(AbsDescriptor descriptor) {
        for (String category: descriptor.getAbsCategories().getAbsCategory()){
            categoriesList.add(category.trim());
        }
        //categoriesList.addAll(descriptor.getAbsCategories().getAbsCategory());
        System.out.println(categoriesList);
    }

    public void checkCategories() {
//        boolean flag = true;
//
//        for (Loan loan : loansMap.values()) {
//            String loanGoal = loan.getLoanGoal().trim();
//            flag = false;
//            for (String category : categoriesList){
//                if(category.equalsIgnoreCase(loanGoal)){
//                    flag = true;
//                    break;
//                }
//            }
//            if(!flag)
//                break;
///*
//            if (!categoriesList.contains(loan.getLoanGoal())) {
//                flag = false;
//                break;
//            }
//*/
//        }
//        xml.setCheckCategories(flag);
    }
    public void checkClients() {
//        boolean flag = true;
//
//        for (Loan loan : loansMap.values()) {
//            String loanerName=loan.getLoanerName().trim();
//            flag=false;
//            for (Client client:clientsMap.values()){
//                if(client.getName().equalsIgnoreCase(loanerName)){
//                    flag = true;
//                    break;
//                }
//            }
//            if(!flag)
//                break;
//        }
//        xml.setCheckClients(flag);
//
    }

/*
    public void checkClients() {
        boolean flag = true;
        for (Loan loan : loansMap.values()) {
            if (!clientsMap.containsKey(loan.getLoanerName())) {
                flag = false;
                break;
            }
        }
        xml.setCheckClients(flag);
    }
*/

    public void addMoneyToAccount(int num, String name) {
        Transaction transaction = new Transaction();

        transaction.setYaz(yaz.getYaz());
        transaction.setAmount(num);
        transaction.setType('+');
        transaction.setPreBalance(clientsMap.get(name).getTotalMoney());
        transaction.setPostBalance(clientsMap.get(name).getTotalMoney() + num);
        clientsMap.get(name).getAccount().addTransaction(transaction);

        clientsMap.get(name).setTotalMoney(clientsMap.get(name).getTotalMoney() + num);
    }

    public void withdrawMoney(int num, String name) {
        Transaction transaction = new Transaction();

        transaction.setYaz(yaz.getYaz());
        transaction.setAmount(num);
        transaction.setType('-');
        transaction.setPreBalance(clientsMap.get(name).getTotalMoney());
        transaction.setPostBalance(clientsMap.get(name).getTotalMoney() - num);
        clientsMap.get(name).getAccount().addTransaction(transaction);

        clientsMap.get(name).setTotalMoney(clientsMap.get(name).getTotalMoney() - num);
    }

    public boolean checkLoanSuitability(String investorName,Loan loan, List<String> categoriesList, int minInterestPerYaz, int minYazTime, int maxOpenLoans) {
        boolean isMatch = false;
        int sumOfLoans = 0;

        if(investorName.equals(loan.getLoanerName())){
            return false;
        }
        for (Loan singleLoan: clientsMap.get(loan.getLoanerName()).getAccount().getLoansTaken()){
            if(singleLoan.getStatus()!=Loan.Status.FINISHED)
            {
                sumOfLoans++;
            }
        }
        if(sumOfLoans > maxOpenLoans){
            return false;
        }
        for (String str : categoriesList) {
            if (loan.getLoanGoal().equals(str)) {
                isMatch = true;
                break;
            }
        }

        return isMatch && loan.getTotalYazTime() >= minYazTime && loan.getInterestPerPayment() >= minInterestPerYaz;
    }

    public String getLoanerNameByIdHelper(String loanId) {
//        if(loansMap.containsKey(loanId)){
//            return loan;
//        }
        for (Loan loan : getLoansMap().values()) {
            if (loan.getLoanId().equals(loanId)) {
                return loan.getLoanerName();
            }
        }
        return null;
    }

    public String getLoanerNameById(String loanId) {
        List<Loan> loanList = new ArrayList<>();
        for (Client client : clientsMap.values()) {
            loanList = client.getAccount().getLoansTaken();
            String loanerName = getLoanerNameByIdHelper(loanId);
            if (loanerName != null) {
                return loanerName;
            }
        }
        return null;
    }
    public boolean checkAmountToInvestInput(String investorName,int amountToInvest){
        return negativeInputCheck(amountToInvest) && moreThanMaximalAmountCheck(investorName,amountToInvest);
    }
    public boolean negativeInputCheck(int amountToInvest) {
        return (amountToInvest > 0);
    }

    public boolean moreThanMaximalAmountCheck(String investorName,int amount) {
        return (amount <= getClientsMap().get(investorName).getTotalMoney());
    }

    public void assignInvestmentByMinimalLoan(String investorName, int investmentAmount, List<Loan> investorLoansChoices, int maxOwnershipPercentage) {
        int divide;
        Loan minLoan;
        List<Loan> tmp = new ArrayList<>(investorLoansChoices);
        List<Loan> finalInvestmentsLoansList = new ArrayList<>();//הלוואות שהוא באמת השקיע(היה לו כסף אליהן)
        //int maxOwnership;
        int totalInvestmentAmountToPay;
        int totalMoneyToWithdraw = 0;
        while (tmp.size() > 0 && investmentAmount != 0) {
            divide = investmentAmount / tmp.size();
            minLoan = findMinimalLoan(tmp);

            //maxOwnership = minLoan.getTotalSumOfLoan()*(maxOwnershipPercentage/100);
            if (minLoan.getTotalSumOfLoan() > divide) {
                totalMoneyToWithdraw += divideEqually(investorName, divide, investmentAmount, tmp, maxOwnershipPercentage);
                finalInvestmentsLoansList.addAll(tmp);
                investmentAmount = 0;
            } else {
                totalInvestmentAmountToPay = checkIfLoanShouldTurnToActive(minLoan, divide, investorName, maxOwnershipPercentage);
                //turnLoanToActive(investorName, minLoan.getTotalSumOfLoan(), minLoan);
                //clientsMap.get(minLoan.getLoanerName()).getAccount().addLoansTaken(minLoan); //////
                //investmentAmount -= minLoan.getTotalSumOfLoan();
                totalMoneyToWithdraw += totalInvestmentAmountToPay;
                investmentAmount -= totalInvestmentAmountToPay;
                tmp.remove(minLoan);
                finalInvestmentsLoansList.add(minLoan);
            }

        }
        withdrawMoney(totalMoneyToWithdraw, investorName);
        for (Loan loan : finalInvestmentsLoansList) {
            getClientsMap().get(investorName).getAccount().addToInvestmentLoans(loan);
        }
    }

    public void turnLoanToActive(String investorName, int moneyToAdd, Loan loan) {
        String loanerName;
        Investor investor = new Investor();
        int numOfPayments = loan.getTotalYazTime() / loan.getPaysEveryYaz(); // 25 / 5 = 5
        int singlePaymentWithoutInterest = loan.getTotalSumOfLoan() / numOfPayments ; // 2500 / 5 = 500
        double interestPercentage = (double) loan.getInterestPerPayment() / 100; // 15 / 100 = 0.15
        boolean isInvestorExist = false;

        loan.setStatus(Loan.Status.ACTIVE);
        loan.setTotalBalance(loan.getTotalSumOfLoan());
        loan.setMissingMoney(0);
        loanerName = getLoanerNameById(loan.getLoanId());
        addMoneyToAccount(moneyToAdd, loanerName);
        //investor.setClient(clientsMap.get(investorName));

        investor.setClientName(investorName);
        investor.setSum(moneyToAdd + investor.getSum());
        int investorPaymentWithoutInterest = investor.getSum() / numOfPayments;
        investor.setPaymentWithInterest((int) (investorPaymentWithoutInterest + investorPaymentWithoutInterest * interestPercentage));
        investor.setInvestmentFundLeftToPay(investor.getPaymentWithInterest() * numOfPayments);

        //investor.setPaymentWithInterest((int) (loan.getSinglePayment() * (double) (investor.getSum() / loan.getTotalSumOfLoan())));
        //investor.setInvestmentFundLeftToPay( (int) (investor.getSum() + (investor.getSum() * ( (double) loan.getInterestPerPayment() / 100))));

        //investor.setInvestmentFundLeftToPay( (int) (investor.getSum() + ((investor.getSum() * ( (double) loan.getInterestPerPayment() / 100)) * (loan.getTotalYazTime() / loan.getPaysEveryYaz()))));
        for (int i = 0; i < loan.getInvestorsList().size(); i++){
            if (investorName.equals(loan.getInvestorsList().get(i).getClientName())){
                loan.getInvestorsList().set(i, investor);
                isInvestorExist = true;
                break;
            }
        }
        if (!isInvestorExist){
            loan.addInvestorToList(investor);
        }
        //withdrawMoney(moneyToAdd, investorName);
        loan.setStartingYaz(yaz.getYaz());
        loan.setNextYazOfPayment(yaz.getYaz() + loan.getPaysEveryYaz());

        loan.setSinglePayment( (int) (singlePaymentWithoutInterest + singlePaymentWithoutInterest * interestPercentage));
        loan.setTotalFundWithInterest(loan.getSinglePayment() * numOfPayments);

        //loan.setTotalFundWithInterest((int) (loan.getTotalSumOfLoan() * ((double) (loan.getInterestPerPayment() + 100) / 100)));
        //loan.setSinglePayment(loan.getTotalFundWithInterest() / (loan.getTotalYazTime() / loan.getPaysEveryYaz()));

        //loan.setSinglePayment( (int) (loan.getTotalSumOfLoan() * (double) (loan.getInterestPerPayment() / 100)));
        //loan.setTotalFundWithInterest(loan.getTotalSumOfLoan() + (loan.getSinglePayment() * (loan.getTotalYazTime() / loan.getPaysEveryYaz())));
/*
        loan.setNextPaymentAccumulatedInRisk(loan.getSinglePayment());
        loan.setNextPaymentAccumulatedInRiskWithoutInterest(loan.getTotalSumOfLoan() / (loan.getTotalYazTime()/loan.getPaysEveryYaz()));
*/
        loan.setNextPaymentAccumulatedInRisk(0);
        loan.setNextPaymentAccumulatedInRiskWithoutInterest(0);
        //investor.setPaymentWithInterest((int)((investor.getSum() * ((double)((loan.getInterestPerPayment() + 100) / 100))) / (loan.getTotalYazTime()/loan.getPaysEveryYaz())));
        //investor.setPaymentWithInterest((int) (loan.getSinglePayment() * (double) (investor.getSum() / loan.getTotalSumOfLoan())));

        loan.setInterestMoneyPerPayment(loan.getSinglePayment() - singlePaymentWithoutInterest);
        //loan.setInterestMoneyPerPayment((int) ((loan.getTotalSumOfLoan() * ((double) loan.getInterestPerPayment() / 100)) / (loan.getTotalYazTime() / loan.getPaysEveryYaz())));

        //loan.setTotalInterestMoneyPerLoan(loan.getTotalSumOfLoan()*(loan.getInterestPerPayment()/100));
        //loan.setTotalInterestMoneyPerLoan((int) (loan.getTotalSumOfLoan() * ((double) loan.getInterestPerPayment() / 100)));
        loan.setTotalInterestMoneyPerLoan(loan.getInterestMoneyPerPayment() * numOfPayments);

        loan.setFundLeftToPay(loan.getTotalFundWithInterest());
        loan.setFundLeftToPayWithoutInterest(loan.getTotalSumOfLoan());
        loan.setInterestLeftToPay(loan.getTotalInterestMoneyPerLoan());
    }

    public Loan findMinimalLoan(List<Loan> investorLoansChoices) {
        Loan min = investorLoansChoices.get(0);
        for (int i = 1; i < investorLoansChoices.size(); i++) {
            if (investorLoansChoices.get(i).getTotalSumOfLoan() < min.getTotalSumOfLoan())
                min = investorLoansChoices.get(i);
        }
        return min;
    }

    public Loan findMinimalLoanByStartingYaz(List<Loan> startingYazList) {
        Loan min = startingYazList.get(0);
        for (int i = 1; i < startingYazList.size(); i++) {
            if (startingYazList.get(i).getStartingYaz() < min.getStartingYaz())
                min = startingYazList.get(i);
        }
        return min;
    }

    public int divideEqually(String investorName, int divide, int investmentAmount, List<Loan> loansList,int maxOwnershipPercentage) {
/*
        String loanId;
        Loan loanById;
        String clientName;
        int totalInvestmentAmountToPay = 0;
        Investor investor = new Investor();
*/
        //int maxOwnership;
        int totalInvestmentAmountToPay = 0;

        for (Loan loan : loansList) {

            totalInvestmentAmountToPay += checkIfLoanShouldTurnToActive(loan,divide,investorName,maxOwnershipPercentage);
/*
            maxOwnership = loan.getTotalSumOfLoan()*(maxOwnershipPercentage/100);
            if(maxOwnership<divide)
                divide=maxOwnership;
            if(loan.getMissingMoney()<divide) {
                turnLoanToActive(investorName,loan.getMissingMoney(),loan);
                totalInvestmentAmountToPay+=loan.getMissingMoney();
            }
            else{
                loanId = loan.getLoanId();
                loanById = loansMap.get(loanId);
                loanById.setStatus(Loan.Status.PENDING);
                loanById.setTotalBalance(loanById.getTotalBalance() + divide);
                loanById.setMissingMoney(loanById.getTotalSumOfLoan() - loanById.getTotalBalance());
                clientName = getLoanerNameById(loanId);
                //clientName=getLoanerNameById(loan.getLoanId());
                addMoneyToAccount(divide, clientName);
                investor.setClient(clientsMap.get(investorName));
                investor.setSum(divide + investor.getSum());
                loan.addInvestorToList(investor);
                totalInvestmentAmountToPay+=divide;

            }
*/
        }
        //withdrawMoney(totalInvestmentAmountToPay, investorName);
        return totalInvestmentAmountToPay;
    }
    public int checkIfLoanShouldTurnToActive(Loan loan, int divide, String investorName, int maxOwnershipPercentage){
        String loanId;
        Loan loanById;
        String clientName;
        int totalInvestmentAmountToPay = 0;
        Investor investor = new Investor();

        int maxOwnership = (int)(loan.getTotalSumOfLoan() * (((double)maxOwnershipPercentage / 100)));
        if(maxOwnership < divide)
            divide = maxOwnership;
        if(loan.getMissingMoney() <= divide) {
            totalInvestmentAmountToPay += loan.getMissingMoney();
            turnLoanToActive(investorName, loan.getMissingMoney(), loan);
/*
            for(int i = 0; i < clientsMap.get(loan.getLoanerName()).getAccount().getLoansTaken().size(); i++){
                if (clientsMap.get(loan.getLoanerName()).getAccount().getLoansTaken().get(i).getLoanId().equals(loan.getLoanId())){
                    clientsMap.get(loan.getLoanerName()).getAccount().getLoansTaken().set(i, loan);
                }
            }
*/
            //clientsMap.get(loan.getLoanerName()).getAcCcount().addLoansTaken(loan);
        }
        else{
            loanId = loan.getLoanId();
            loanById = loansMap.get(loanId);
            loanById.setStatus(Loan.Status.PENDING);
            loanById.setTotalBalance(loanById.getTotalBalance() + divide);
            loanById.setMissingMoney(loanById.getTotalSumOfLoan() - loanById.getTotalBalance());
            clientName = getLoanerNameById(loanId);
            //clientName=getLoanerNameById(loan.getLoanId());
            addMoneyToAccount(divide, clientName);
            //investor.setClient(clientsMap.get(investorName));
            investor.setClientName(investorName);
            investor.setSum(divide + investor.getSum());
            investor.setInvestmentFundLeftToPay(investor.getSum() + (investor.getSum() * (loan.getInterestPerPayment() / 100)));
            //investor.setInvestmentFundLeftToPay(investor.getSum() + ((investor.getSum() * (loan.getInterestPerPayment() / 100)) * (loan.getTotalYazTime() / loan.getPaysEveryYaz())));
            loan.addInvestorToList(investor);
            totalInvestmentAmountToPay += divide;

        }
        return totalInvestmentAmountToPay;
    }

    ///action 7

    public void loansRepayment(Loan loan) {
        //double investorLoanPercentage;
        int numOfPayments = loan.getTotalYazTime() / loan.getPaysEveryYaz(); // 25 / 5 = 5
        int singlePaymentWithoutInterest = loan.getTotalSumOfLoan() / numOfPayments ; // 2500 / 5 = 500

        for (Investor investor : loan.getInvestorsList()) {
            //investorLoanPercentage =(double) investor.getSum() / loan.getTotalSumOfLoan();
            //addMoneyToAccount((int)(loan.getNextPaymentAccumulatedInRisk() * investorLoanPercentage), investor.getClient().getName());
            //investor.setInvestmentFundLeftToPay(investor.getInvestmentFundLeftToPay()- (int)(loan.getNextPaymentAccumulatedInRisk()*investorLoanPercentage));

            //addMoneyToAccount((int)(loan.getSinglePayment() * investorLoanPercentage), /*investor.getClient().getName()*/investor.getClientName());
            //investor.setInvestmentFundLeftToPay(investor.getInvestmentFundLeftToPay() - (int)(loan.getSinglePayment() * investorLoanPercentage));

            addMoneyToAccount(investor.getPaymentWithInterest(), investor.getClientName());
            investor.setInvestmentFundLeftToPay(investor.getInvestmentFundLeftToPay() - investor.getPaymentWithInterest());
        }
        withdrawMoney(loan.getSinglePayment(), loan.getLoanerName());
        //loan.setTotalBalance(loan.getTotalBalance() + loan.getSinglePayment());
        //loan.setMissingMoney(loan.getTotalSumOfLoan() - loan.getTotalBalance());
        Payment payment = new Payment();
        payment.setYazOfPayment(yaz.getYaz());
        payment.setPayment(singlePaymentWithoutInterest);//תשלום חודשי בלי ריבית
        payment.setInterest(loan.getInterestPerPayment());
        payment.setPaymentWithInterest(loan.getSinglePayment());//תשלום חודשי עם ריבית
        loan.addToPaymentsList(payment);
        loan.setFundPaymentsUntilNow(loan.getFundPaymentsUntilNow() + loan.getSinglePayment());//מעדכנים כמה כסף החזרנו למשקיע עד עכשיו כולל ריבית
        //loan.setFundPaymentsUntilNowWithoutInterest(loan.getFundPaymentsUntilNowWithoutInterest() + loan.getSinglePayment() - loan.getInterestMoneyPerPayment());
        loan.setFundPaymentsUntilNowWithoutInterest(loan.getFundPaymentsUntilNowWithoutInterest() + singlePaymentWithoutInterest);
        loan.setFundLeftToPay(loan.getTotalFundWithInterest() - loan.getFundPaymentsUntilNow());
        //loan.setFundLeftToPayWithoutInterest(loan.getTotalSumOfLoan() - loan.getFundPaymentsUntilNowWithoutInterest());
        loan.setFundLeftToPayWithoutInterest(loan.getTotalSumOfLoan() - loan.getFundPaymentsUntilNowWithoutInterest());
        loan.setInterestPaymentsUntilNow(loan.getInterestPaymentsUntilNow() + loan.getInterestMoneyPerPayment());
        loan.setInterestLeftToPay(loan.getTotalInterestMoneyPerLoan() - loan.getInterestPaymentsUntilNow());
        loan.setNextYazOfPayment(yaz.getYaz() + loan.getPaysEveryYaz());
        loan.setNextPaymentAccumulatedInRisk(0);
        loan.setNextPaymentAccumulatedInRiskWithoutInterest(0);
        loan.setStatus(Loan.Status.ACTIVE);
        loan.setNumOfUnpaidPayments(0);
        loan.setAmountOfUnpaidPayments(0);

        if (loan.getFundLeftToPay() == 0) {
            loan.setStatus(Loan.Status.FINISHED);
            loan.setEndingYaz(yaz.getYaz());
        }
        System.out.println(loan.getLoanerName()+" payments:");
        System.out.println(loan.getPaymentsList());

        System.out.println("Clients' transactions:");
        for (Client client:clientsMap.values()){
            System.out.println(client.getName()+": "+client.getAccount().getTransactions());
        }
    }

    public void loanRepaymentInRisk(Loan loan, int amountToPay){
        double investorLoanPercentage;
        int investorAddMoneyToAccount;

        for (Investor investor : loan.getInvestorsList()) {
            investorLoanPercentage = (double) investor.getSum() / loan.getTotalSumOfLoan();
            investorAddMoneyToAccount = (int) (amountToPay * investorLoanPercentage);

            addMoneyToAccount(investorAddMoneyToAccount, investor.getClientName());
            investor.setInvestmentFundLeftToPay(investor.getInvestmentFundLeftToPay() - investorAddMoneyToAccount);
        }
        withdrawMoney(amountToPay, loan.getLoanerName());//check if loaner has the money if not ->risk
        //loan.setTotalBalance(loan.getTotalBalance() + loan.getSinglePayment());
        //loan.setMissingMoney(loan.getTotalSumOfLoan() - loan.getTotalBalance());
        Payment payment = new Payment();
        payment.setYazOfPayment(yaz.getYaz());
        payment.setPayment(amountToPay - (amountToPay * (loan.getInterestPerPayment() / 100)));//תשלום חודשי בלי ריבית
        payment.setInterest(loan.getInterestPerPayment());
        payment.setPaymentWithInterest(amountToPay);//תשלום חודשי עם ריבית
        loan.addToPaymentsList(payment);

        loan.setFundPaymentsUntilNow(loan.getFundPaymentsUntilNow() + payment.getPaymentWithInterest());//מעדכנים כמה כסף החזרנו למשקיע עד עכשיו כולל ריבית
        loan.setFundPaymentsUntilNowWithoutInterest(loan.getFundLeftToPayWithoutInterest() + payment.getPayment());

        loan.setFundLeftToPay(loan.getTotalFundWithInterest() - loan.getFundPaymentsUntilNow());
        loan.setFundLeftToPayWithoutInterest(loan.getTotalSumOfLoan() - loan.getFundPaymentsUntilNowWithoutInterest());
        loan.setInterestPaymentsUntilNow(loan.getInterestPaymentsUntilNow() + loan.getInterestMoneyPerPayment());
        loan.setInterestLeftToPay(loan.getTotalInterestMoneyPerLoan() - loan.getInterestPaymentsUntilNow());

        loan.setNextPaymentAccumulatedInRisk(loan.getNextPaymentAccumulatedInRisk() - amountToPay);
        loan.setNextPaymentAccumulatedInRiskWithoutInterest(loan.getNextPaymentAccumulatedInRisk() - amountToPay);

        ///if client close his debt and his loan turn to Active
        if(loan.getNextPaymentAccumulatedInRisk() == 0){
            //loan.setNextYazOfPayment(yaz.getYaz()+loan.getPaysEveryYaz());
            loan.setNextPaymentAccumulatedInRisk(0);
            loan.setNextPaymentAccumulatedInRiskWithoutInterest(0);
            loan.setStatus(Loan.Status.ACTIVE);
            loan.setNumOfUnpaidPayments(0);
            loan.setAmountOfUnpaidPayments(0);
        }

        if (loan.getFundLeftToPay() == 0) {
            loan.setStatus(Loan.Status.FINISHED);
            loan.setEndingYaz(yaz.getYaz());
        }
    }
    public void riskMode(Loan loan){
        int numOfPayments = loan.getTotalYazTime() / loan.getPaysEveryYaz(); // 25 / 5 = 5
        int singlePaymentWithoutInterest = loan.getTotalSumOfLoan() / numOfPayments ; // 2500 / 5 = 500

        loan.setStatus(Loan.Status.RISK);
        loan.setNextYazOfPayment(loan.getNextYazOfPayment() + loan.getPaysEveryYaz());
        if((yaz.getYaz() < loan.getTotalYazTime() + loan.getStartingYaz())){
            loan.setNextPaymentAccumulatedInRisk(loan.getNextPaymentAccumulatedInRisk() + loan.getSinglePayment());
            //loan.setNextPaymentAccumulatedInRiskWithoutInterest(loan.getNextPaymentAccumulatedInRiskWithoutInterest()+(loan.getTotalSumOfLoan() / (loan.getTotalYazTime()/loan.getPaysEveryYaz())));
            loan.setNextPaymentAccumulatedInRiskWithoutInterest(loan.getNextPaymentAccumulatedInRiskWithoutInterest() + singlePaymentWithoutInterest);

            Payment payment = new Payment();
            payment.setYazOfPayment(yaz.getYaz());
            payment.setPayment(loan.getNextPaymentAccumulatedInRiskWithoutInterest());//תשלום חודשי בלי ריבית
            payment.setInterest(loan.getInterestPerPayment());
            payment.setPaymentWithInterest(loan.getNextPaymentAccumulatedInRisk());//תשלום חודשי עם ריבית
            loan.addToUnpaidPayments(payment);

            loan.setNumOfUnpaidPayments(loan.getNumOfUnpaidPayments() + 1);
            loan.setAmountOfUnpaidPayments(loan.getAmountOfUnpaidPayments() + loan.getNextPaymentAccumulatedInRisk());
            //addNotificationToClient(loan);
/*
            for(int i = 0; i < clientsMap.get(loan.getLoanerName()).getAccount().getLoansTaken().size(); i++){
                if (clientsMap.get(loan.getLoanerName()).getAccount().getLoansTaken().get(i).getLoanId().equals(loan.getLoanId())){
                    clientsMap.get(loan.getLoanerName()).getAccount().getLoansTaken().set(i, loan);
                }
            }
*/
        }
    }
    public void addNotifications(){
        for (Loan loan : loansMap.values()) {
            if (loan.getStatus() == Loan.Status.ACTIVE || loan.getStatus() == Loan.Status.RISK) {
                if (yaz.getYaz() - loan.getNextYazOfPayment() == 0) {
                    addNotificationToClient(loan);
                }
            }
        }

    }
    public void addNotificationToClient(Loan loan){
        Notification notification = new Notification();
        notification.setLoanId(loan.getLoanId());
        notification.setYazOfPayment(yaz.getYaz());
        notification.setPaymentWithInterest(loan.getSinglePayment());
        clientsMap.get(loan.getLoanerName()).getAccount().addNotificationToList(notification);
    }

    public void closeEntireLoan(Loan loan){
        int numOfPayments = loan.getTotalYazTime() / loan.getPaysEveryYaz(); // 25 / 5 = 5
        int singlePaymentWithoutInterest = loan.getTotalSumOfLoan() / numOfPayments ; // 2500 / 5 = 500

        for (Investor investor : loan.getInvestorsList()) {
            //addMoneyToAccount((investor.getInvestmentFundLeftToPay()), /*investor.getClient().getName()*/investor.getClientName());
            //addMoneyToAccount((int)(investor.getInvestmentFundLeftToPay()*((double)(loan.getInterestPerPayment()+100)/100)), /*investor.getClient().getName()*/investor.getClientName());
            addMoneyToAccount(investor.getInvestmentFundLeftToPay(), investor.getClientName());
            investor.setInvestmentFundLeftToPay(0);
        }
        withdrawMoney(loan.getFundLeftToPay(), loan.getLoanerName());
        //loan.setTotalBalance(loan.getTotalBalance() + loan.getSinglePayment());
        //loan.setMissingMoney(loan.getTotalSumOfLoan() - loan.getTotalBalance());
        Payment payment = new Payment();
        payment.setYazOfPayment(yaz.getYaz());
        payment.setPayment(loan.getFundLeftToPayWithoutInterest());//תשלום חודשי בלי ריבית
        payment.setInterest(loan.getInterestPerPayment());
        payment.setPaymentWithInterest(loan.getFundLeftToPay());//תשלום חודשי עם ריבית
        loan.addToPaymentsList(payment);

        loan.setFundPaymentsUntilNow(loan.getTotalFundWithInterest());//מעדכנים כמה כסף החזרנו למשקיע עד עכשיו כולל ריבית
        loan.setFundLeftToPay(0);
        loan.setInterestPaymentsUntilNow(loan.getTotalInterestMoneyPerLoan());
        loan.setInterestLeftToPay(0);
        //loan.setNextYazOfPayment(yaz.getYaz()+loan.getPaysEveryYaz());
        loan.setNextPaymentAccumulatedInRisk(0);
        loan.setNextPaymentAccumulatedInRiskWithoutInterest(0);
        loan.setNumOfUnpaidPayments(0);
        loan.setAmountOfUnpaidPayments(0);
        loan.setStatus(Loan.Status.FINISHED);
        loan.setEndingYaz(yaz.getYaz());
    }

    //////////////////// exercise 3 - 22.6 additions

    public boolean createNewLoansFromInputStream(InputStream inputStream, String clientName) throws JAXBException, IOException {
        if (inputStream != null) {
            return getXmlFile(inputStream, clientName);
        }
        return false;
    }
    public boolean getXmlFile(InputStream inputStream, String clientName) {
        //String pathName = xmlFile.getAbsolutePath();

//        if (!checkFileType(pathName)) {
//            //headerController.changeFilePathTextField();
//            isCopySucceeded = false;
//        }

        //clearAll();
        return copyClassesFromXml(inputStream, clientName);

//        if (!getXml().getCheckCategories()) {
//            System.out.println("Invalid xml file, it contains an invalid category. Please try again.");
//            isCopySucceeded = false;
//        } else if (!getXml().getCheckClients()) {
//            System.out.println("Invalid xml file, it contains a loan of a person that is not a customer. Please try again.");
//            isCopySucceeded = false;
//        } else if (!getXml().getCheckYaz()) {
//            System.out.println("Invalid xml file, there is a loan with an incorrect time division. Please try again.");
//            isCopySucceeded = false;
//        } else if (!getXml().getCheckClientDuplicate()) {
//            System.out.println("Invalid xml file, there are clients with the same name. Please try again.");
//            isCopySucceeded = false;
//        }
//        if (isCopySucceeded) {
//            //headerController.copySucceeded(pathName);
//            System.out.println("File loaded successfully.");
//            getYaz().setYaz(1);
//        }
//        else{
//            clearAll();
//        }
    }
/*
    public boolean checkFileType(String pathName) {
        return getFileType(pathName).equals("xml");
    }
*/

    /////////////// 29.6 addons
    public void changeClientsInLoanInvestorsList(Loan selectedLoanToBuy, String customerSellerName, String customerBuyerName){
        for (Investor investor : loansMap.get(selectedLoanToBuy.getLoanId()).getInvestorsList()) {
            if(/*investor.getClient().getName()*/investor.getClientName().equals(customerSellerName))
            {
                //investor.setClient(database.getClientsMap().get(customerBuyerName));
                investor.setClientName(clientsMap.get(customerBuyerName).getName());
            }
        }
    }

    public void swapLoanFromClientsInvestmentsList(Loan selectedLoanToBuy, String customerSellerName, String customerBuyerName){
        for (Loan loan: clientsMap.get(customerSellerName).getAccount().getMyInvestmentLoans()) {
            if(loan.getLoanId().equals(selectedLoanToBuy.getLoanId()))
            {
                clientsMap.get(customerSellerName).getAccount().getMyInvestmentLoans().remove(loan);
                break;
            }
        }
        clientsMap.get(customerBuyerName).getAccount().addToInvestmentLoans(selectedLoanToBuy);
        withdrawMoney(selectedLoanToBuy.getFundLeftToPayWithoutInterest(), customerBuyerName);
        addMoneyToAccount(selectedLoanToBuy.getFundLeftToPayWithoutInterest(), customerSellerName);
    }

    public void checkIfLoanTurnIntoRisk(){
        for (Loan loan: loansMap.values()) {
            if (loan.getStatus().equals(Loan.Status.ACTIVE) || loan.getStatus().equals(Loan.Status.RISK)) {
                if (yaz.getYaz() > loan.getNextYazOfPayment()) {
                    riskMode(loan);
                }
            }
        }
    }

}