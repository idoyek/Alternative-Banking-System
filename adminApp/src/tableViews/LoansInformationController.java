package tableViews;

import client.Loan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import body.BodyController;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class LoansInformationController implements Initializable {
    private BodyController bodyController;
    private ObservableList<Loan> loans;
    private ObservableList<Loan> loansList;

    @FXML
    private TableView<Loan> tableViewWindow;

    @FXML
    private TableColumn<Loan, String> loanID;

    @FXML
    private TableColumn<Loan, String> loanerName;

    @FXML
    private TableColumn<Loan, String> loanCategory;

    @FXML
    private TableColumn<Loan, Integer> totalAmount;

    @FXML
    private TableColumn<Loan, Integer> totalTime;

    @FXML
    private TableColumn<Loan, Integer> interest;

    @FXML
    private TableColumn<Loan, Integer> paymentTimeRate;

    @FXML
    private TableColumn<Loan, Loan.Status> status;

    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loanID.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        loanerName.setCellValueFactory(new PropertyValueFactory<>("loanerName"));
        loanCategory.setCellValueFactory(new PropertyValueFactory<>("loanGoal"));
        totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalSumOfLoan"));
        totalTime.setCellValueFactory(new PropertyValueFactory<>("totalYazTime"));
        interest.setCellValueFactory(new PropertyValueFactory<>("interestPerPayment"));
        paymentTimeRate.setCellValueFactory(new PropertyValueFactory<>("paysEveryYaz"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

    }
    public TableView<Loan> getTableViewWindow(){
        return tableViewWindow;
    }
    public TableColumn<Loan, Integer> getTotalAmountColumn(){
        return totalAmount;
    }

    public void updateLoansInformationTables(List<Loan> lenderLoans){
        loansList = FXCollections.observableArrayList(lenderLoans);
        tableViewWindow.setItems(loansList);
        tableViewWindow.refresh();
    }
    public void updateLoansForSaleInformationTable(Set<Loan> lenderLoans){
        loansList = FXCollections.observableArrayList(lenderLoans);
        tableViewWindow.setItems(loansList);
        tableViewWindow.refresh();
    }
    /*public void updateLoanerInformationTable(List<Loan> loanerLoans){
        loanerLoansList = FXCollections.observableArrayList(loanerLoans);
        tableViewWindow.setItems(loanerLoansList);
    }*/


    public void updateLoanerLoansInformationTable(Loan[] loansList){
        ObservableList<Loan> loansTaken = FXCollections.observableArrayList();
        loansTaken.clear();
        loansTaken.addAll(Arrays.asList(loansList));

        tableViewWindow.setItems(loansTaken);
        tableViewWindow.refresh();
    }

}
