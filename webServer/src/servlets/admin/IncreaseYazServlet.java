package servlets.admin;

import client.Database;
import client.DatabaseDeepCopy;
import client.HistoryRewind;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "IncreaseYazServlet", urlPatterns = {"/increaseYazServlet"})
public class IncreaseYazServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        DatabaseDeepCopy databaseDeepCopy = new DatabaseDeepCopy();
        Database databaseToCopy = ServletUtils.getDatabase(getServletContext());
        databaseDeepCopy.writePersonsToFile(databaseToCopy);
        Database newDatabase = null;
        try {
            newDatabase = databaseDeepCopy.readPersonsFromFile();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ServletUtils.getHistoryRewind().addToDatabasesList(newDatabase);
        ServletUtils.changeToRelevantCurrentDatabase(getServletContext());
        Database database = ServletUtils.getDatabase(getServletContext());

        Gson gson = new Gson();

        synchronized (this){
            database.getYaz().setYaz(database.getYaz().getYaz() + 1);
            database.checkIfLoanTurnIntoRisk();
            database.addNotifications();

            String jsonResponse = gson.toJson(database.getYaz().getYaz());
            try(PrintWriter out = response.getWriter()){
                out.print(jsonResponse);
                out.flush();
            }

            System.out.println("Request URI is: " + request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

}