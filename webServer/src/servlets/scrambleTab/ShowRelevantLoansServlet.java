package servlets.scrambleTab;

import DTO.RelevantLoansListDTO;
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
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "ShowRelevantLoansServlet", urlPatterns = {"/showRelevantLoansServlet"})
public class ShowRelevantLoansServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());

        Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String reqBodyAsString = scanner.hasNext() ? scanner.next() : "";

        RelevantLoansListDTO relevantLoansListDTO = new Gson().fromJson(reqBodyAsString, RelevantLoansListDTO.class);

        List<Loan> relevantLoansList = new ArrayList<>();
        List<String> categoriesList = new ArrayList<>(relevantLoansListDTO.getUserCategoriesList());
        int minimumInterest = relevantLoansListDTO.getMinimumInterest();
        int minimumYaz = relevantLoansListDTO.getMinimumYaz();
        int maxOpenLoans = relevantLoansListDTO.getMaxOpenLoans();


        synchronized (this){
            for (Loan loan : database.getLoansMap().values()) {
                if(loan.getStatus().equals(Loan.Status.NEW) && database.checkLoanSuitability(usernameFromSession, loan, categoriesList, minimumInterest, minimumYaz,maxOpenLoans)){
                    relevantLoansList.add(loan);
                }
            }
        }

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(relevantLoansList);

        try(PrintWriter out = response.getWriter()){
            out.print(jsonResponse);
            out.flush();
        }

        System.out.println("Request URI is: " + request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_OK);

    }

}