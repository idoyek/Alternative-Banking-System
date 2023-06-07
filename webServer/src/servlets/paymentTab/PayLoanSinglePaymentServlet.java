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

@WebServlet(name = "PayLoanSinglePaymentServlet", urlPatterns = {"/paySinglePaymentServlet"})
public class PayLoanSinglePaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());

        Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String reqBodyAsString = scanner.hasNext() ? scanner.next() : "";

        SinglePaymentDTO singlePaymentDTO = new Gson().fromJson(reqBodyAsString, SinglePaymentDTO.class);
        String selectedLoanToPayId = singlePaymentDTO.getLoanToPayId();
        Loan selectedLoanToPay = database.getLoansMap().get(selectedLoanToPayId);
        int amountToPay = singlePaymentDTO.getAmountToPay();
        int userTotalMoney;

        synchronized (this){
            userTotalMoney = database.getClientsMap().get(usernameFromSession).getTotalMoney();
            if (amountToPay > userTotalMoney){
                String errorMessage = "You can't pay that amount. You have " + userTotalMoney + " ILS in your account.";

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print(errorMessage);
            }
            else{
                if (selectedLoanToPay.getStatus().equals(Loan.Status.RISK)) {
                    database.loanRepaymentInRisk(selectedLoanToPay, amountToPay);
                }
                else {
                    database.loansRepayment(selectedLoanToPay);
                }
                System.out.println("Request URI is: " + request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}