package tableViews;

import client.Loan;
import client.Transaction;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import body.BodyController;
import body.CustomerBodyController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    private CustomerBodyController customerBodyController;
    //private BodyController bodyController;
    private ObservableList<Transaction> newTransactions = FXCollections.observableArrayList();

    @FXML
    private TableView<Transaction> transactionsTableView;

    @FXML
    private TableColumn<Transaction, Integer> yaz;

    @FXML
    private TableColumn<Transaction, Integer> amount;

    @FXML
    private TableColumn<Transaction, String> type;

    @FXML
    private TableColumn<Transaction, Integer> preBalance;

    @FXML
    private TableColumn<Transaction, Integer> postBalance;

    @FXML
    private Button chargeButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label withdrawWrongInputLabel;

    public void setCustomerBodyController(CustomerBodyController customerBodyController) {
        this.customerBodyController = customerBodyController;
    }
/*
    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }
*/

    public TableView<Transaction> getTransactionsTableView() {
        return transactionsTableView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yaz.setCellValueFactory(new PropertyValueFactory<>("yaz"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        preBalance.setCellValueFactory(new PropertyValueFactory<>("preBalance"));
        postBalance.setCellValueFactory(new PropertyValueFactory<>("postBalance"));
    }

    public void updateTransactionsInformationTable(List<Transaction> transactions){
        newTransactions = FXCollections.observableArrayList(transactions);
        transactionsTableView.setItems(newTransactions);
        transactionsTableView.refresh();
    }

    @FXML
    void activateAmountTextField(ActionEvent event) {

    }

    @FXML
    void activateChargeButton(ActionEvent event) {
        if(customerBodyController.getIsInRewindMode()){
            if (!amountTextField.getText().isEmpty()){
                amountTextField.setText("");
            }
            return;
        }

       int amount = Integer.parseInt(amountTextField.getText());
       String customerName = customerBodyController.getCurrentCustomerName();

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.CHARGE_MONEY)
                .newBuilder()
                .addQueryParameter("username", customerName)
                .addQueryParameter("amount", Integer.toString(amount))
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        System.out.println("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            System.out.println("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
//                        try {
//                            String jsonOfClientString = response.body().string();
//                            response.body().close();
//                            Transaction[] transactionList = new Gson().fromJson(jsonOfClientString, Transaction[].class);
//                            newTransactions.clear();
//                            newTransactions.addAll(Arrays.asList(transactionList));
//
//                            transactionsTableView.setItems(newTransactions);
//                            transactionsTableView.refresh();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        withdrawWrongInputLabel.setText("");
                        amountTextField.clear();
                    });
                }
            }
        });
    }

    @FXML
    void activateWithdrawButton(ActionEvent event) {
        if(customerBodyController.getIsInRewindMode()){
            if (!amountTextField.getText().isEmpty()){
                amountTextField.setText("");
            }
            return;
        }
        int amount = Integer.parseInt(amountTextField.getText());
        String customerName = customerBodyController.getCurrentCustomerName();

/*
        amountTextField.textProperty().addListener((obs, oldSelection, newSelection) -> {
            amountTextField.requestFocus();
            if (newSelection != null && ( Integer.parseInt(String.valueOf(newSelection)))) {
                withdrawButton.setDisable(true);
            }
            else{
                withdrawButton.setDisable(false);
            }

        });
*/
        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.WITHDRAW_MONEY)
                .newBuilder()
                .addQueryParameter("username", customerName)
                .addQueryParameter("amount", Integer.toString(amount))
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        System.out.println("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            withdrawWrongInputLabel.setText(responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        withdrawWrongInputLabel.setText("");
                        amountTextField.clear();
                    });
                }
            }
        });

    }

}
