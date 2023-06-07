package body;

import client.Client;
import client.Loan;
import client.Transaction;
import javafx.scene.control.ListView;
import tableViews.CustomersInformationController;
import tableViews.LoansInformationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.MainController;
import tableViews.TransactionsController;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class BodyController {
    private MainController mainController;

    @FXML private AnchorPane loansInformation;
    @FXML private LoansInformationController loansInformationController;
    @FXML private AnchorPane customersInformation;
    @FXML private CustomersInformationController customersInformationController;

    //@FXML private BorderPane transactionsInformation;
    //@FXML private TransactionsController transactionsInformationController;


/*
    public void setCustomersInformationController(CustomersInformationController customersInformationController) {
        this.customersInformationController = customersInformationController;
        customersInformationController.setAdminBodyController(this);
    }
*/

    public void setMainController(MainController mainController) {
        this.mainController = mainController;

        if (loansInformation != null && customersInformation != null /*&& transactionsInformation != null*/) {
            loansInformationController.setBodyController(this);
            customersInformationController.setBodyController(this);
            //transactionsInformationController.setBodyController(this);
        }
    }

    @FXML
    private Button showListViewButton;
    @FXML
    private Button increaseYazButton;
    @FXML
    private Button loadFileButton;
    @FXML
    private Label loansInformationLabel;
    @FXML
    private Label customersInformationLabel;
    @FXML
    private ListView<String> customerLoansInformationByStatusListView;
    @FXML
    private Label invalidXmlFileLabel;

    public Label getInvalidXmlFileLabel() {
        return invalidXmlFileLabel;
    }

    @FXML
    void activateShowListViewButton(ActionEvent event) {
        customerLoansInformationByStatusListView.getItems().clear();
        Client client = customersInformationController.getTableView().getSelectionModel().getSelectedItem();
        int newCounter = 0, pendingCounter = 0, activeCounter = 0, riskCounter = 0, finishedCounter = 0;
        String loanerLoansSummary, lenderLoansSummary;
//        client.getAccount().getLoansTaken().stream()
//                .filter(c -> c.getStatus().equals(Loan.Status.PENDING))
//                        .count();
        for(Loan loan : client.getAccount().getLoansTaken()){
            if(loan.getStatus().equals(Loan.Status.NEW)){
                newCounter++;
            }
            else if (loan.getStatus().equals(Loan.Status.PENDING)){
                pendingCounter++;
            }
            else if (loan.getStatus().equals(Loan.Status.ACTIVE)){
                activeCounter++;
            }
            else if (loan.getStatus().equals(Loan.Status.RISK)){
                riskCounter++;
            }
            else if (loan.getStatus().equals(Loan.Status.FINISHED)){
                finishedCounter++;
            }
        }
        loanerLoansSummary = "* Loaner Loans: New - " + newCounter + ", Pending - " + pendingCounter + ", Active - " + activeCounter + ", Risk - " + riskCounter + ", Finished - " + finishedCounter + ".";
        customerLoansInformationByStatusListView.getItems().add(loanerLoansSummary);
        newCounter = 0; pendingCounter = 0; activeCounter = 0; riskCounter = 0; finishedCounter = 0;
        for(Loan loan : client.getAccount().getMyInvestmentLoans()){
            if(loan.getStatus().equals(Loan.Status.NEW)){
                newCounter++;
            }
            else if (loan.getStatus().equals(Loan.Status.PENDING)){
                pendingCounter++;
            }
            else if (loan.getStatus().equals(Loan.Status.ACTIVE)){
                activeCounter++;
            }
            else if (loan.getStatus().equals(Loan.Status.RISK)){
                riskCounter++;
            }
            else if (loan.getStatus().equals(Loan.Status.FINISHED)){
                finishedCounter++;
            }
        }
        lenderLoansSummary = "* Lender Loans: New - " + newCounter + ", Pending - " + pendingCounter + ", Active - " + activeCounter + ", Risk - " + riskCounter + ", Finished - " + finishedCounter + ".";
        customerLoansInformationByStatusListView.getItems().add(lenderLoansSummary);

    }
    @FXML
    void activateIncreaseYazButton(ActionEvent event) {
//        //mainController.updateYaz();
//        mainController.setYazTextField();
//        //mainController.checkIfLoanTurnIntoRisk();
//        mainController.addNotifications();
//        mainController.updateAdminScreen();
    }


    @FXML
    void activateLoadFileButton(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("XML files", "*.xml");
//        fileChooser.getExtensionFilters().add(fileExtension);
//        fileChooser.setTitle("Choose an XML file");
//        File xmlFile = fileChooser.showOpenDialog(new Stage());
//        if (xmlFile != null) {
//            //System.out.println("NOT NULL");
//            if(mainController.getXmlFile(xmlFile)){
//                updateAdminScreen();
//                //invalidXmlFileLabel.setText("");
//                //mainController.clearSplitMenuButton();
//                //mainController.setSplitMenuButton();
//                mainController.setSkinComboBox();
//            }
//            else{
//                updateAdminScreen();
//                invalidXmlFileLabel.setText("Invalid XML file.");
//            }
//        }
    }

    public void updateAdminScreen(){
        loansInformationController.updateLoansInformationTable();
        customersInformationController.updateClientsInformationTable();
        customerLoansInformationByStatusListView.getItems().clear();
        invalidXmlFileLabel.setText("");
    }
//    public Collection<Loan> getLoansListFromDatabase(){
//        return mainController.getLoansListFromDatabase();
//    }
//    public Collection<Client> getClientsListFromDatabase(){
//        return mainController.getClientsListFromDatabase();
//    }

    public void updateTransactionsInformationTable(List<Transaction> transactions){
        //transactionsInformationController.updateTransactionsInformationTable(transactions);
    }
    /*public void chargeMoneyToAccount(int amount, String clientName){
        mainController.chargeMoneyToAccount(amount, clientName);
    }*/
}
