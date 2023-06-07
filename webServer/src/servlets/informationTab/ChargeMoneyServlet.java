package servlets.informationTab;

import client.Database;
import client.Loan;
import client.Transaction;
import com.google.gson.Gson;
import utils.ServletUtils;
import utils.SessionUtils;
//import engine.users.UserManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ChargeMoneyServlet", urlPatterns = {"/chargeMoneyServlet"})
public class ChargeMoneyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());

        int amount = Integer.parseInt(request.getParameter("amount"));

        //System.out.println("ChargeMoneyServlet user name: " + usernameFromSession);
        //System.out.println("ChargeMoneyServlet amount: " + amount);

        synchronized (this){
            //add money to user
            database.addMoneyToAccount(amount, usernameFromSession);
//            Loan loan = new Loan(usernameFromSession, "wedding", "wedding", 10000, 10, 10, 10);
//            loan.setStatus(Loan.Status.ACTIVE);
//            database.getClientsMap().get(usernameFromSession).getAccount().addLoansTaken(loan);


//            System.out.println("ChargeMoneyServlet transactions: " + database.getClientsMap().get(usernameFromSession).getAccount().getTransactions());
//            System.out.println("ChargeMoneyServlet: " + database.getClientsMap().values());
//            List<Transaction> transactionList = database.getClientsMap().get(usernameFromSession).getAccount().getTransactions();
//
//            Gson gson = new Gson();
//            String jsonResponse = gson.toJson(transactionList);
//
//            try(PrintWriter out = response.getWriter()){
//                out.print(jsonResponse);
//                out.flush();
//            }

            System.out.println("Request URI is: " + request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }

}