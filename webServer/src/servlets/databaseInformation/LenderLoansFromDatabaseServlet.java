package servlets.databaseInformation;

import client.Database;
import client.Loan;
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

@WebServlet(name = "LenderLoansFromDatabaseServlet", urlPatterns = {"/lenderLoansFromDatabaseServlet"})
public class LenderLoansFromDatabaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
/*
        response.setContentType("text/plain;charset=UTF-8");

        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());
        List<Loan> lenderLoansList;

        synchronized (this) {
            //get lender loans
            lenderLoansList = database.getClientsMap().get(usernameFromSession).getAccount().getMyInvestmentLoans();
        }

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(lenderLoansList);

        try(PrintWriter out = response.getWriter()){
            out.print(jsonResponse);
            out.flush();
        }

        System.out.println("Request URI is: " + request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_OK);

 */
    }
}