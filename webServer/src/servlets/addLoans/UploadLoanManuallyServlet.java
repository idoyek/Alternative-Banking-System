package servlets.addLoans;

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

@WebServlet(name = "UploadLoanManuallyServlet", urlPatterns = {"/uploadLoanManually"})
public class UploadLoanManuallyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());
        String loanId = request.getParameter("loanId");
        String loanGoal = request.getParameter("loanGoal");
        int totalSumOfLoan = Integer.parseInt(request.getParameter("totalSumOfLoan"));
        int totalYazTime = Integer.parseInt(request.getParameter("totalYazTime"));
        int interestPerPayment = Integer.parseInt(request.getParameter("interestPerPayment"));
        int paysEveryYaz = Integer.parseInt(request.getParameter("paysEveryYaz"));
        double yazDivision = (double) totalYazTime / paysEveryYaz;

        synchronized (this){
            if (database.getLoansMap().containsKey(loanId)){
                String errorMessage = "The loan ID '" + loanId + "' already exists. Please enter a different one.";

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print(errorMessage);
            }
            else if (!database.getCategoriesList().contains(loanGoal)){
                String errorMessage = "The category '" + loanGoal + "' doesn't exist in the system. Please enter a different one.";

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print(errorMessage);
            }
            else if (yazDivision % 1 != 0){
                String errorMessage = "The total yaz time and payment time rate you entered don't divide properly.";

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print(errorMessage);
            }
            else { //create and add loan to user
                Loan loan = new Loan(usernameFromSession, loanId, loanGoal, totalSumOfLoan, totalYazTime, interestPerPayment, paysEveryYaz);
                database.addLoanToMap(loan);
                database.getClientsMap().get(usernameFromSession).getAccount().addLoansTaken(loan);

                System.out.println("Request URI is: " + request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_OK);
            }

//            System.out.println("UploadLoanManuallyServlet loans: " + database.getLoansMap());
//
//            List<Loan> loansTakenList = database.getClientsMap().get(usernameFromSession).getAccount().getLoansTaken();
//
//            Gson gson = new Gson();
//            String jsonResponse = gson.toJson(loansTakenList);
//
//            try(PrintWriter out = response.getWriter()){
//                out.print(jsonResponse);
//                out.flush();
//            }

        }

    }

}