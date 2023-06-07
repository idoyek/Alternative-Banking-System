package servlets.paymentTab;

import DTO.SinglePaymentDTO;
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
import java.util.Scanner;

@WebServlet(name = "CloseEntireLoanServlet", urlPatterns = {"/closeEntireLoanServlet"})
public class CloseEntireLoanServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());

        Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String reqBodyAsString = scanner.hasNext() ? scanner.next() : "";

        String selectedLoanToCloseId = new Gson().fromJson(reqBodyAsString, String.class);
        Loan selectedLoanToClose = database.getLoansMap().get(selectedLoanToCloseId);
        int userTotalMoney;

        synchronized (this){
            userTotalMoney = database.getClientsMap().get(usernameFromSession).getTotalMoney();
            if (selectedLoanToClose.getFundLeftToPay() > userTotalMoney){
                String errorMessage = "You can't pay that amount (" + selectedLoanToClose.getFundLeftToPay() + "). You have " + userTotalMoney + " ILS in your account.";

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print(errorMessage);
            }
            else {
                database.closeEntireLoan(selectedLoanToClose);

                System.out.println("Request URI is: " + request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}