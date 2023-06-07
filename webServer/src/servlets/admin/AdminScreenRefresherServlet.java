package servlets.admin;

import DTO.AdminScreenRefresherDTO;
import client.*;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdminScreenRefresherServlet", urlPatterns = {"/adminScreenRefresherServlet"})
public class AdminScreenRefresherServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        Database database = ServletUtils.getDatabase(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(request);
        Gson gson = new Gson();
        //Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        synchronized (this){
            try (PrintWriter out = response.getWriter()) {

                List<Client> clientsList = new ArrayList<>(database.getClientsMap().values());
                List<Loan> loansList = new ArrayList<>(database.getLoansMap().values());
                Yaz yaz = database.getYaz();
                boolean isInRewindMode = ServletUtils.getHistoryRewind().getIsInRewindMode();

                AdminScreenRefresherDTO adminScreenRefresherDTO = new AdminScreenRefresherDTO(clientsList, loansList, yaz, isInRewindMode);
                //DummyClientScreenRefresherDTO dummyClientScreenRefresherDTO = new DummyClientScreenRefresherDTO(clientScreenRefresherDTO, lenderLoansList);
                //usersList.add(new Loan("ido", "aaa", "aaa", 10, 10, 10, 10));
                String json = gson.toJson(adminScreenRefresherDTO);
                out.println(json);
                out.flush();
            }
        }
    }

}
