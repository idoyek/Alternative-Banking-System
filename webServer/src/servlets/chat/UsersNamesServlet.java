package servlets.chat;

import chat.ChatManager;
import chat.SingleChatEntry;
import client.Database;
import com.google.gson.Gson;
import constants.Constants;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@WebServlet(name = "UsersNamesServlet", urlPatterns = {"/usersNamesServlet"})
public class UsersNamesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            Database database = ServletUtils.getDatabase(getServletContext());
            Set<String> usersList = database.getClientsMap().keySet();
            String json = gson.toJson(usersList);
            out.println(json);
            out.flush();
        }
    }

}