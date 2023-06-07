package servlets.databaseInformation;

import client.Database;
import client.Loan;
import client.Transaction;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "LoanerLoansFromDatabaseServlet", urlPatterns = {"/loanerLoansFromDatabaseServlet"})
public class LoanerLoansFromDatabaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
/*
        response.setContentType("text/plain;charset=UTF-8");

        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());

        synchronized (this){
            //get loaner loans
            List<Loan> loanerLoansList = database.getClientsMap().get(usernameFromSession).getAccount().getLoansTaken();

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(loanerLoansList);

            try(PrintWriter out = response.getWriter()){
                out.print(jsonResponse);
                out.flush();
            }

            System.out.println("Request URI is: " + request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_OK);
        }

*/
    }
}