package servlets.chat;

import chat.ChatManager;
import client.Database;
import constants.Constants;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;

@WebServlet(name = "SendChatServlet", urlPatterns = {"/sendChatServlet"})
public class SendChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //ChatManager chatManager = ServletUtils.getChatManager(getServletContext());
        Database database = ServletUtils.getDatabase(getServletContext());
        String username = SessionUtils.getUsername(request);

        if (username == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        String userChatString = request.getParameter(Constants.CHAT_PARAMETER);
        if (userChatString != null && !userChatString.isEmpty()) {
            logServerMessage("Adding chat string from " + username + ": " + userChatString);
            synchronized (getServletContext()) {
                database.getChatManager().addChatString(userChatString, username);
            }
        }
    }

    private void logServerMessage(String message) {
        System.out.println(message);
    }

}