package servlets.informationTab;

import client.Database;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;

@WebServlet(name = "WithdrawMoneyServlet", urlPatterns = {"/withdrawMoneyServlet"})
public class WithdrawMoneyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());
        int amount = Integer.parseInt(request.getParameter("amount"));

        System.out.println("ChargeMoneyServlet user name: " + usernameFromSession);
        System.out.println("ChargeMoneyServlet amount: " + amount);

        synchronized (this){
            int userTotalMoney = database.getClientsMap().get(usernameFromSession).getTotalMoney();
            if (userTotalMoney  < amount){
                String errorMessage = "Yoy don't have enough money.";

                // stands for unauthorized as the user doesn't have enough money
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print(errorMessage);
            }
            else {
                //withdraw money to user
                database.withdrawMoney(amount, usernameFromSession);
                response.setStatus(HttpServletResponse.SC_OK);
            }
            System.out.println("Request URI is: " + request.getRequestURI());

        }

    }

}