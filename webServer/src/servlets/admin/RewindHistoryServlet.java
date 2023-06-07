package servlets.admin;

import client.Database;
import client.DatabaseDeepCopy;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "RewindHistoryServlet", urlPatterns = {"/rewindHistoryServlet"})
public class RewindHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int databaseIndex = Integer.parseInt(request.getParameter("yazToMove")) - 1;

        synchronized (this){
            ServletUtils.changeToSliderRelevantDatabase(getServletContext(), databaseIndex);

            System.out.println("Request URI is: " + request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

}