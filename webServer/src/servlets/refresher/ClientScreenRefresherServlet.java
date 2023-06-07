package servlets.refresher;

import DTO.ClientScreenRefresherDTO;
import DTO.DummyClientScreenRefresherDTO;
import client.*;
import jakarta.servlet.annotation.WebServlet;
import utils.ServletUtils;
import com.google.gson.Gson;
//import engine.users.UserManager;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "ClientScreenRefresherServlet", urlPatterns = {"/clientScreenRefresherServlet"})
public class ClientScreenRefresherServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        Database database = ServletUtils.getDatabase(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(request);
        Gson gson = new Gson();
        ClientScreenRefresherDTO clientScreenRefresherDTO;
        //Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        synchronized (this){
            try (PrintWriter out = response.getWriter()) {

                boolean isInRewindMode = ServletUtils.getHistoryRewind().getIsInRewindMode();
                boolean isClientLoginAfter = false;
                boolean isClientDataNeedToSeen;
                if (database.getYaz().getYaz() < ServletUtils.getLastYazDatabase().getClientsMap().get(usernameFromSession).getLoginYaz()){
                    isClientLoginAfter = true;
                }
                isClientDataNeedToSeen = isInRewindMode && isClientLoginAfter;

                Yaz yaz = database.getYaz();

                if (isClientDataNeedToSeen){
                    clientScreenRefresherDTO = new ClientScreenRefresherDTO(null, null, null, null, null, null, null, yaz, isInRewindMode, isClientDataNeedToSeen);
                }
                else{
                    List<Loan> loanerLoansList = database.getClientsMap().get(usernameFromSession).getAccount().getLoansTaken();
                    List<Loan> lenderLoansList = database.getClientsMap().get(usernameFromSession).getAccount().getMyInvestmentLoans();
                    List<Transaction> transactionsList = database.getClientsMap().get(usernameFromSession).getAccount().getTransactions();
                    //List<Loan> loansForSaleList = new ArrayList<>(database.getLoansForSaleMap().keySet());
                    List<Loan> loansForSaleList = new ArrayList<>();
                    List<LoanSeller> loanSellersList = new ArrayList<>(database.getLoansForSaleMap().values());
                    for (LoanSeller loanSeller: loanSellersList){
                        if (!loanSeller.getLoanForSale().getStatus().equals(Loan.Status.ACTIVE)){
                            database.getLoansForSaleMap().remove(loanSeller.getLoanForSale().getLoanId());
                        }
                        else{
                            loansForSaleList.add(loanSeller.getLoanForSale());
                        }
                    }
                    List<String> categoriesList = database.getCategoriesList();
                    List<Loan> loanerLoansPaymentList = new ArrayList<>();
                    for (Loan loan: loanerLoansList) {
                        if(loan.getStatus().equals(Loan.Status.ACTIVE) || loan.getStatus().equals(Loan.Status.RISK)){
                            //Loan newLoan = new Loan(loan.getLoanerName(), loan.getLoanId(), loan.getLoanGoal(), loan.getTotalSumOfLoan(), loan.getTotalYazTime(), loan.getPaysEveryYaz(), loan.getInterestPerPayment());
                            //loanerLoansPaymentList.add(newLoan);
                            loanerLoansPaymentList.add(loan);
                        }
                    }
                    List<Notification> notificationsList = database.getClientsMap().get(usernameFromSession).getAccount().getNotificationsList();
                    clientScreenRefresherDTO = new ClientScreenRefresherDTO(loanerLoansList, lenderLoansList, transactionsList, loansForSaleList, categoriesList, loanerLoansPaymentList, notificationsList, yaz, isInRewindMode, isClientDataNeedToSeen);
                }
                //DummyClientScreenRefresherDTO dummyClientScreenRefresherDTO = new DummyClientScreenRefresherDTO(clientScreenRefresherDTO, lenderLoansList);
                //usersList.add(new Loan("ido", "aaa", "aaa", 10, 10, 10, 10));
                String json = gson.toJson(clientScreenRefresherDTO);
                out.println(json);
                out.flush();
            }
        }
    }

}
