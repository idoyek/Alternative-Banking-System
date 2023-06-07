package servlets.addLoans;

import client.Database;
import client.Transaction;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "LoadXmlFileServlet", urlPatterns = {"/loadXmlFileServlet"})
public class LoadXmlFileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameFromSession = SessionUtils.getUsername(request);
        Database database = ServletUtils.getDatabase(getServletContext());

        Collection<Part> parts = request.getParts();

        //out.println("Total parts : " + parts.size());

        StringBuilder fileContent = new StringBuilder();

        for (Part part : parts) {
            //to write the content of the file to a string
            fileContent.append(readFromInputStream(part.getInputStream()));
        }
        InputStream file = new ByteArrayInputStream(fileContent.toString().getBytes(StandardCharsets.UTF_8));

        synchronized (this){
            try {
                if (database.createNewLoansFromInputStream(file, usernameFromSession)){
                    response.setStatus(HttpServletResponse.SC_OK);
                }
                else {
                    if(!database.getXml().getCheckYaz()){
                        response.getOutputStream().print("Invalid xml file, there is a loan with an incorrect time division. Please try again.");
                    }
                    else if (!database.getXml().getCheckDuplicateLoans()){
                        response.getOutputStream().print("Invalid xml file, there is a loan with a taken loan ID. Please try again.");
                    }
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }catch (JAXBException | IOException e){
                response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }

}