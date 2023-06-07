package header;

import client.Client;
import client.Yaz;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.MainController;

import java.io.IOException;

public class HeaderController {
    private MainController mainController;
    private int currentYaz;
    //private String currentCustomerName;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private final StringProperty currentCustomerName;

    public HeaderController() {
        currentCustomerName = new SimpleStringProperty();
    }

    @FXML
    public void initialize() {
        userGreetingLabel.textProperty().bind(Bindings.concat("Hello ", currentCustomerName));
    }

    public void updateUserName(String userName){
        currentCustomerName.set(userName);
    }


    @FXML private Label userGreetingLabel;

    @FXML private Label rewindModeLabel;

/*
    @FXML
    private SplitMenuButton userType;

    @FXML
    private MenuItem admin;
*/

    @FXML
    private TextField filePathTextField;

    @FXML
    private TextField currentYazTextField;

    @FXML
    private ComboBox<String> skinComboBox;

    @FXML
    void activateCurrentYazTextField(ActionEvent event) {

    }

    @FXML
    void activateSkinComboBox(ActionEvent event) {
        mainController.changeSkin(skinComboBox.getValue());
    }

    @FXML
    void activateFilePathTextField(ActionEvent event) {

    }

    @FXML
    void chooseUserType(ActionEvent event) {

    }

    public String getCurrentCustomerName(){
        return currentCustomerName.toString();
    }

    public void changeFilePathTextField() {
        filePathTextField.setText("Incorrect file type");
        filePathTextField.setStyle("-fx-progress-color: red");
    }

    public void copySucceeded(String pathName) {
        filePathTextField.setText(pathName);
        filePathTextField.setStyle("-fx-progress-color: blue");
        currentYazTextField.setText("Current Yaz: 1");

    }

/*
    public void clearSplitMenuButton(){
        userType.getItems().clear();
        userType.getItems().add(admin);
    }
*/
/*
    public void setSplitMenuButton() {
        //setAdminMenuButton();
        for (Client client : mainController.getClientsListFromDatabase()) {
            MenuItem item = new MenuItem(client.getName());
            item.setId(client.getName());

            //ActionEvent newEvent = new ActionEvent();
            item.setOnAction(event -> {
                try {
                    currentCustomerName = client.getName();
                    activateClientScreen(currentCustomerName);
//                    mainController.updateClientScreen(client.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            userType.getItems().add(item);
        }
    }
*/
    public void setSkinComboBox(){
        ObservableList<String> skinOptions = FXCollections.observableArrayList("Sky", "Spring", "Warm");
        skinComboBox.setItems(skinOptions);
    }
/*
    public void setAdminMenuButton(){
        userType.getItems().get(0).setOnAction(event -> {
            try {
                mainController.switchToAdminScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
*/

    public void updateYaz(Yaz yaz){
        currentYaz = yaz.getYaz();
        currentYazTextField.setText("Current Yaz: " + currentYaz);
    }
    public int getCurrentYaz(){
        return currentYaz;
    }

    public void activateRewindModeLabel(){
        rewindModeLabel.setText("Rewind Mode");
    }
    public void clearRewindModeLabel(){
        rewindModeLabel.setText("");
    }
}
