package servlets.informationTab;

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
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

@WebServlet(name = "BuyLoansServlet", urlPatterns = {"/buyLoansServlet"})
public class BuyLoansServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());

        Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String reqBodyAsString = scanner.hasNext() ? scanner.next() : "";

        Loan selectedLoanToBuy = new Gson().fromJson(reqBodyAsString, Loan.class);

        synchronized (this){
            if(database.getLoansForSaleMap().get(selectedLoanToBuy.getLoanId()).getLoanSellerName().equals(usernameFromSession)){
                String errorMessage = "You can't buy your own loan.";

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print(errorMessage);
                return;
            }

            database.changeClientsInLoanInvestorsList(selectedLoanToBuy, database.getLoansForSaleMap().get(selectedLoanToBuy.getLoanId()).getLoanSellerName(), usernameFromSession);
            database.swapLoanFromClientsInvestmentsList(selectedLoanToBuy, database.getLoansForSaleMap().get(selectedLoanToBuy.getLoanId()).getLoanSellerName(), usernameFromSession);
            database.getLoansForSaleMap().remove(selectedLoanToBuy.getLoanId());

            System.out.println("Request URI is: " + request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }
/*
    private String getSellerNameFromLoansToSaleMap(Map<Loan, String> loansForSaleMap, Loan selectedLoanToBuy){
        for (Loan loan : loansForSaleFromMap){
            if (loan.getLoanId().equals(selectedLoanToBuy.getLoanId())){
                return loan.getLoanId();
            }
        }
    }
*/

}