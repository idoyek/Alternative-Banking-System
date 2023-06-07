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

@WebServlet(name = "CategoriesListFromDatabaseServlet", urlPatterns = {"/categoriesListFromDatabaseServlet"})
public class CategoriesListFromDatabaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
/*
        response.setContentType("text/plain;charset=UTF-8");

        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());
        List<String> categoriesList;
        synchronized (this){
            //get categories list
            categoriesList = database.getCategoriesList();
        }
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(categoriesList);

        try(PrintWriter out = response.getWriter()){
            out.print(jsonResponse);
            out.flush();
        }

        System.out.println("Request URI is: " + request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_OK);

*/
    }
}