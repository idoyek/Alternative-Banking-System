package header;

import client.Yaz;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.MainController;

import static util.Constants.JHON_DOE;

public class HeaderController {
    private MainController mainController;
    private int currentYaz;

    @FXML private Label userGreetingLabel;
    @FXML private Label rewindModeLabel;
    @FXML private TextField currentYazTextField;
    @FXML private ComboBox<String> skinComboBox;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private final StringProperty currentCustomerName;

    public HeaderController() {
        currentCustomerName = new SimpleStringProperty(JHON_DOE);
    }

    @FXML
    public void initialize() {
        userGreetingLabel.textProperty().bind(Bindings.concat("Hello ", currentCustomerName));
    }

    public void updateUserName(String userName){
        currentCustomerName.set(userName);
    }

    @FXML void activateCurrentYazTextField(ActionEvent event) {

    }

    @FXML void activateSkinComboBox(ActionEvent event) {
        mainController.changeSkin(skinComboBox.getValue());
    }

    public String getCurrentCustomerName(){
        return currentCustomerName.toString();
    }

    public void setSkinComboBox(){
        ObservableList<String> skinOptions = FXCollections.observableArrayList("Sky", "Spring", "Warm");
        skinComboBox.setItems(skinOptions);
    }

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
