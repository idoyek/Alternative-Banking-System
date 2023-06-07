package servlets.informationTab;

import DTO.RelevantLoansListDTO;
import client.Database;
import client.Loan;
import client.LoanSeller;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@WebServlet(name = "SaleLoansServlet", urlPatterns = {"/saleLoansServlet"})
public class SaleLoansServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());

        Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String reqBodyAsString = scanner.hasNext() ? scanner.next() : "";

        Loan selectedLoanToSale = new Gson().fromJson(reqBodyAsString, Loan.class);

        synchronized (this){
/*
            for (Loan loan : database.getLoansForSaleMap().keySet()){
                if (loan.getLoanId().equals(selectedLoanToSale.getLoanId())){
                    String errorMessage = "Your loan is already on Sale.";

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getOutputStream().print(errorMessage);
                    return;
                }
            }
*/
            Loan selectedLoanToSaleFromDatabase = database.getLoansMap().get(selectedLoanToSale.getLoanId());

            if(database.getLoansForSaleMap().containsKey(selectedLoanToSale.getLoanId())){
                String errorMessage = "Your loan is already on Sale.";

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print(errorMessage);
                return;
            }

            //database.addToLoansForSaleMap(selectedLoanToSale, usernameFromSession);
            LoanSeller loanSeller = new LoanSeller();
            loanSeller.setLoanSellerName(usernameFromSession);
            loanSeller.setLoanForSale(selectedLoanToSaleFromDatabase);
            database.addToLoansForSaleMap(selectedLoanToSaleFromDatabase.getLoanId(), loanSeller);

            System.out.println("Request URI is: " + request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

}