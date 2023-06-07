package main;

import client.*;
import body.BodyController;
import body.CustomerBodyController;
import header.HeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import login.LoginController;
import java.io.IOException;
import java.net.URL;

public class MainController {
    private Stage primaryStage;

    @FXML private BorderPane mainBoarderPane;

    @FXML private AnchorPane header;
    @FXML private HeaderController headerController;
    @FXML private AnchorPane body;
    @FXML private BodyController bodyController;
    @FXML private BorderPane login;
    @FXML private LoginController loginController;
    @FXML private BorderPane customerBody;
    @FXML private CustomerBodyController customerBodyController;

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void initialize() throws IOException {
        if (headerController != null && bodyController != null && loginController != null) {
            headerController.setMainController(this);
            bodyController.setMainController(this);
            loginController.setMainController(this);
            mainBoarderPane.setTop(null);
            mainBoarderPane.setCenter(null);
            //customerBodyController.setMainController(this);
        }
        loadClientScreen();
        setSkinComboBox();
    }

    public void loadClientScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/body/clientBody.fxml");
        fxmlLoader.setLocation(url);
        customerBody = fxmlLoader.load(url.openStream());
        customerBodyController = fxmlLoader.getController();
        customerBodyController.setMainController(this);
    }

    public void clientScreen() throws IOException {
        mainBoarderPane.setCenter(customerBody);
        mainBoarderPane.setTop(header);
        mainBoarderPane.setBottom(null);
        customerBodyController.startListRefresher();
        customerBodyController.startChatListRefresher();
    }

    public void updateHeaderUserName(String userName){
        headerController.updateUserName(userName);
    }
    public String getCurrentCustomerName(){
        return headerController.getCurrentCustomerName();
    }

    public void updateYaz(Yaz yaz){
        headerController.updateYaz(yaz);
    }
    public int getCurrentYaz(){
        return headerController.getCurrentYaz();
    }

    public void setSkinComboBox(){
        headerController.setSkinComboBox();
    }
    public void changeSkin(String skin){
        primaryStage.getScene().getStylesheets().clear();
        if(skin.equals("Sky")){
            primaryStage.getScene().getStylesheets().add("/main/mainScreen1.css");
        }
        else if(skin.equals("Spring")){
            primaryStage.getScene().getStylesheets().add("/main/mainScreen2.css");
        }
        else if(skin.equals("Warm")){
            primaryStage.getScene().getStylesheets().add("/main/mainScreen3.css");
        }
    }

    public void activateRewindModeLabel(){
        headerController.activateRewindModeLabel();
    }
    public void clearRewindModeLabel(){
        headerController.clearRewindModeLabel();
    }

}
