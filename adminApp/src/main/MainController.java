package main;

import client.*;
import body.BodyController;
import header.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import login.LoginController;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class MainController {
    private Stage primaryStage;

    @FXML private BorderPane mainBoarderPane;

    @FXML private AnchorPane header;
    @FXML private HeaderController headerController;
    @FXML private AnchorPane body;
    @FXML private BodyController bodyController;
    @FXML private BorderPane login;
    @FXML private LoginController loginController;

    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }


    public void initialize() {
        if (headerController != null && bodyController != null && loginController != null) {
            headerController.setMainController(this);
            bodyController.setMainController(this);
            loginController.setMainController(this);
            mainBoarderPane.setTop(null);
            mainBoarderPane.setCenter(null);
            //customerBodyController.setMainController(this);
        }
        setSkinComboBox();
    }

    public void adminScreen() throws IOException {
        mainBoarderPane.setCenter(body);
        mainBoarderPane.setTop(header);
        mainBoarderPane.setBottom(null);
        bodyController.startListRefresher();
    }

    public int getCurrentYaz(){
        return headerController.getCurrentYaz();
    }
    public void updateYaz(Yaz yaz){
        headerController.updateYaz(yaz);
    }

    public void updateHeaderUserName(String userName){
        headerController.updateUserName(userName);
    }
    public String getCurrentCustomerName(){
        return headerController.getCurrentCustomerName();
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
