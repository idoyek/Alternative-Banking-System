package tableViews;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import body.BodyController;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomersInformationController implements Initializable {
    private BodyController bodyController;
    private ObservableList<Client> clients;

    @FXML
    private TableView<Client> tableViewWindow;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, Integer> totalBalanceColumn;

//    @FXML
//    private TableColumn<Client, String> loanerLoansColumn;
//
//    @FXML
//    private TableColumn<Client, String> lenderLoansColumn;


    public void setBodyController(BodyController adminBodyController) {
        this.bodyController = adminBodyController;
    }
    public TableView<Client> getTableView(){
        return tableViewWindow;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("totalMoney"));
    }

    public void updateClientsInformationTable(){
//        clients = FXCollections.observableArrayList(bodyController.getClientsListFromDatabase());
//        tableViewWindow.setItems(clients);
//        tableViewWindow.refresh();
    }
}
