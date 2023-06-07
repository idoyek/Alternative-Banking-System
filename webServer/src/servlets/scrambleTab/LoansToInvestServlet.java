package servlets.scrambleTab;

import DTO.ScrambleDTOwebApp;
import client.Database;
import client.Loan;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@WebServlet(name = "LoansToInvestServlet", urlPatterns = {"/loansToInvestServlet"})
public class LoansToInvestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());

        Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String reqBodyAsString = scanner.hasNext() ? scanner.next() : "";

        ScrambleDTOwebApp scrambleDTO = new Gson().fromJson(reqBodyAsString, ScrambleDTOwebApp.class);

        List<String> loansToInvestAsString = new ArrayList<>(scrambleDTO.getLoansToInvestList());
        List<Loan> loansToInvest = new ArrayList<>();
        int maxOwnershipPercentage = scrambleDTO.getMaxOwnershipPercentage();
        int amountToInvest = scrambleDTO.getAmountToInvest();
        int userTotalMoney;

        synchronized (this){
            userTotalMoney = database.getClientsMap().get(usernameFromSession).getTotalMoney();
            if (amountToInvest > userTotalMoney){
                String errorMessage = "You can't invest that amount. You have " + userTotalMoney + " ILS in your account.";

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print(errorMessage);
            }
            else{
                for (String loanId : loansToInvestAsString){
                    loansToInvest.add(database.getLoansMap().get(loanId));
                }
                database.assignInvestmentByMinimalLoan(usernameFromSession, amountToInvest, loansToInvest, maxOwnershipPercentage);
                System.out.println("Request URI is: " + request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_OK);

            }
        }
    }
}