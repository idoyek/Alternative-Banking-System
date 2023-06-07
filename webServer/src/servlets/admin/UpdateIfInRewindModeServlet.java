package servlets.admin;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "UpdateIfInRewindModeServlet", urlPatterns = {"/updateIfInRewindModeServlet"})
public class UpdateIfInRewindModeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        boolean isInRewindMode = Boolean.parseBoolean(request.getParameter("isInRewindMode"));

        synchronized (this){
            ServletUtils.getHistoryRewind().setIsInRewindMode(isInRewindMode);

            System.out.println("Request URI is: " + request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

}