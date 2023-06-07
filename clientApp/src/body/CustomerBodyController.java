package body;

import DTO.*;
import chat.ChatController;
import client.Loan;
import client.Notification;
import client.Transaction;
import client.Yaz;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import tableViews.LoansInformationController;
import tableViews.TransactionsController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.MainController;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static util.Constants.GSON_INSTANCE;
import static util.Constants.REFRESH_RATE;

public class CustomerBodyController {
    private MainController mainController;
    private int amountToInvest;

    private boolean isInRewindMode;

    public boolean getIsInRewindMode() {
        return isInRewindMode;
    }

    @FXML private Label totalRelevantLoansLabel;

    @FXML private AnchorPane loanerLoansInformation;
    @FXML private LoansInformationController loanerLoansInformationController;
    @FXML private AnchorPane investorLoansInformation;
    @FXML private LoansInformationController investorLoansInformationController;
    @FXML private BorderPane transactionsInformation;
    @FXML private TransactionsController transactionsInformationController;
    @FXML private AnchorPane relevantLoans;
    @FXML private LoansInformationController relevantLoansController;
    @FXML private AnchorPane loanerLoans;
    @FXML private LoansInformationController loanerLoansController;
    @FXML private AnchorPane loansForSaleInformation;
    @FXML private LoansInformationController loansForSaleInformationController;
    @FXML private BorderPane chat;
    @FXML private ChatController chatController;


    @FXML private TabPane clientsTabPane;
    @FXML private Tab informationTab;
    @FXML private Tab scrambleTab;
    @FXML private Tab paymentTab;
    @FXML private Tab addLoanTab;

    @FXML private TextField loanIdTextField;
    @FXML private TextField loanGoalTextField;
    @FXML private TextField totalSumOfLoanTextField;
    @FXML private TextField totalYazTimeTextField;
    @FXML private TextField interestPerPaymentTextField;
    @FXML private TextField paysEveryYazTextField;

    @FXML void activateLoanIdTextField(ActionEvent event) {

    }
    @FXML void activateLoanGoalTextField(ActionEvent event) {

    }
    @FXML void activateTotalSumOfLoanTextField(ActionEvent event) {

    }
    @FXML void activateTotalYazTimeTextField(ActionEvent event) {

    }
    @FXML void activateInterestPerPaymentTextField(ActionEvent event) {

    }
    @FXML void activatePaysEveryYazTextField(ActionEvent event) {

    }
    @FXML void activateAmountToInvestTextField(ActionEvent event) {

    }
    @FXML void activateMinimumInterestTextField(ActionEvent event) {

    }
    @FXML void activateMinimumYazTextField(ActionEvent event) {

    }
    @FXML void activateMaxOpenLoansTextField(ActionEvent event) {

    }
    @FXML void activateMaxOwnershipTextField(ActionEvent event) {

    }
    @FXML void activatePayLoanSinglePaymentTextField(ActionEvent event) {

    }

    @FXML private Button uploadLoanButton;
    @FXML private Button loadLoansButton;

    @FXML private TextField amountToInvestTextField;
    @FXML private TextField minimumInterestTextField;
    @FXML private TextField minimumYazTextField;
    @FXML private TextField maxOpenLoansTextField;
    @FXML private TextField maxOwnershipTextField;

    @FXML private Label amountToInvestLabel;
    @FXML private Label minInterestLabel;
    @FXML private Label minYazLabel;
    @FXML private Label maxOpenLoansLabel;
    @FXML private Label maxOwnershipLabel;

    @FXML private Label loanIsAlreadyInListLabel;

    @FXML private Label currentCustomerOwnLoanLabel;

    @FXML private Label invalidXmlFileLabel;

    @FXML private Label invalidManuallyLoanLabel;

    @FXML private Label paymentLabel;

    @FXML private Button showRelevantLoansListButton;

    @FXML private Button activateScrambleButton;

    @FXML private ListView<String> categoriesOptionsListView;

    @FXML private ListView<String> userChoiceCategoriesListView;

    @FXML private ListView<Notification> notificationsListView;

    @FXML private Button forwardCategoriesButton;

    @FXML private Button backwardCategoriesButton;

    @FXML private Button closeEntireLoanButton;
    @FXML private Button payLoanSinglePaymentButton;
    @FXML private TextField payLoanSinglePaymentTextField;

    @FXML private Button saleLoanButton;
    @FXML private Button buyLoanButton;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;

        if (loanerLoansInformation != null && investorLoansInformation != null && transactionsInformation != null && relevantLoans != null && loanerLoans != null && loansForSaleInformation != null && chat != null) {
            loanerLoansInformationController.setCustomerBodyController(this);
            investorLoansInformationController.setCustomerBodyController(this);
            transactionsInformationController.setCustomerBodyController(this);
            relevantLoansController.setCustomerBodyController(this);
            loanerLoansController.setCustomerBodyController(this);
            loansForSaleInformationController.setCustomerBodyController(this);
            chatController.setCustomerBodyController(this);
        }
    }

    public CustomerBodyController() {
        autoUpdate = new SimpleBooleanProperty();
        totalUsers = new SimpleIntegerProperty();
    }

    public void initialize() {
        loansForSaleInformationController.getTotalAmountColumn().setText("Fund Left To Pay");
        loansForSaleInformationController.getTotalAmountColumn().setCellValueFactory(new PropertyValueFactory<>("fundLeftToPayWithoutInterest"));
        saleLoanButton.setDisable(true);
        buyLoanButton.setDisable(true);
        payLoanSinglePaymentButton.setDisable(true);
        payLoanSinglePaymentTextField.setDisable(true);
        closeEntireLoanButton.setDisable(true);

        investorLoansInformationController.getTableViewWindow().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (newSelection.getStatus().equals(Loan.Status.ACTIVE)) {
                    saleLoanButton.setDisable(false);
                }
                else {
                    saleLoanButton.setDisable(true);
                }
            }
        });
        loansForSaleInformationController.getTableViewWindow().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (newSelection.getStatus().equals(Loan.Status.ACTIVE)) {
                    buyLoanButton.setDisable(false);
                }
                else {
                    buyLoanButton.setDisable(true);
                }
            }
        });

        loanerLoansController.getTableViewWindow().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                if ((newSelection.getStatus().equals(Loan.Status.ACTIVE) && mainController.getCurrentYaz() == newSelection.getNextYazOfPayment()) || (newSelection.getStatus().equals(Loan.Status.RISK))) {
                    payLoanSinglePaymentButton.setDisable(false);
                }
                else{
                    payLoanSinglePaymentButton.setDisable(true);
                }
            }
            else{
                payLoanSinglePaymentButton.setDisable(true);
            }

        });
        loanerLoansController.getTableViewWindow().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && (newSelection.getStatus().equals(Loan.Status.RISK)) ) {
                payLoanSinglePaymentTextField.setDisable(false);
            }
            else{
                payLoanSinglePaymentTextField.setDisable(true);
            }
        });

        loanerLoansController.getTableViewWindow().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            closeEntireLoanButton.setDisable(newSelection == null);
        });
    }

    private Timer timer;
    private TimerTask listRefresher;
    private final BooleanProperty autoUpdate;
    private final IntegerProperty totalUsers;

    public void startListRefresher(){
        autoUpdate.set(true);
        listRefresher = new ClientScreenRefresher(
                autoUpdate,
                this::updateUsersList);
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateUsersList(ClientScreenRefresherDTO clientScreenRefresherDTO) {
        Platform.runLater(() -> {

            Yaz yaz = clientScreenRefresherDTO.getYaz();
            mainController.updateYaz(yaz);
            isInRewindMode = clientScreenRefresherDTO.getIsInRewindMode();
            boolean isClientDataNeedToSeen = clientScreenRefresherDTO.getIsClientDataNeedToSeen();
            if (isInRewindMode){
                mainController.activateRewindModeLabel();
            }
            else{
                mainController.clearRewindModeLabel();
            }
            if (isClientDataNeedToSeen){
                loanerLoansInformationController.getTableViewWindow().getItems().clear();
                investorLoansInformationController.getTableViewWindow().getItems().clear();
                transactionsInformationController.getTransactionsTableView().getItems().clear();
                loansForSaleInformationController.getTableViewWindow().getItems().clear();
                categoriesOptionsListView.getItems().clear();
                loanerLoansController.getTableViewWindow().getItems().clear();
                notificationsListView.getItems().clear();

                return;
            }
            ObservableList<Loan> loanerLoansList = FXCollections.observableArrayList(clientScreenRefresherDTO.getLoanerLoansList());
            ObservableList<Loan> lenderLoansList = FXCollections.observableArrayList(clientScreenRefresherDTO.getLenderLoansList());
            ObservableList<Transaction> transactionsList = FXCollections.observableArrayList(clientScreenRefresherDTO.getTransactionsList());
            ObservableList<Loan> loansForSaleList = FXCollections.observableArrayList(clientScreenRefresherDTO.getLoansForSaleList());
            ObservableList<String> categoriesList = FXCollections.observableArrayList(clientScreenRefresherDTO.getCategoriesList());
            ObservableList<Loan> loanerLoansPaymentList = FXCollections.observableArrayList(clientScreenRefresherDTO.getLoanerLoansPaymentList());
            ObservableList<Notification> notificationsList = FXCollections.observableArrayList(clientScreenRefresherDTO.getNotificationsList());
            int index;

            index = loanerLoansInformationController.getTableViewWindow().getSelectionModel().getFocusedIndex();
            loanerLoansInformationController.getTableViewWindow().setItems(loanerLoansList);
            loanerLoansInformationController.getTableViewWindow().refresh();
            loanerLoansInformationController.getTableViewWindow().getSelectionModel().select(index);
            loanerLoansInformationController.getTableViewWindow().getFocusModel().focus(index);

            index = investorLoansInformationController.getTableViewWindow().getSelectionModel().getFocusedIndex();
            investorLoansInformationController.getTableViewWindow().setItems(lenderLoansList);
            investorLoansInformationController.getTableViewWindow().refresh();
            investorLoansInformationController.getTableViewWindow().getSelectionModel().select(index);
            investorLoansInformationController.getTableViewWindow().getFocusModel().focus(index);

            index = transactionsInformationController.getTransactionsTableView().getSelectionModel().getFocusedIndex();
            transactionsInformationController.getTransactionsTableView().setItems(transactionsList);
            transactionsInformationController.getTransactionsTableView().refresh();
            transactionsInformationController.getTransactionsTableView().getSelectionModel().select(index);
            transactionsInformationController.getTransactionsTableView().getFocusModel().focus(index);

            index = loansForSaleInformationController.getTableViewWindow().getSelectionModel().getFocusedIndex();
            loansForSaleInformationController.getTableViewWindow().setItems(loansForSaleList);
            loansForSaleInformationController.getTableViewWindow().refresh();
            loansForSaleInformationController.getTableViewWindow().getSelectionModel().select(index);
            loansForSaleInformationController.getTableViewWindow().getFocusModel().focus(index);

            Set<String> userCategoriesChoices = new HashSet<>(userChoiceCategoriesListView.getItems());
            categoriesList.removeIf(userCategoriesChoices::contains);
            index = categoriesOptionsListView.getSelectionModel().getSelectedIndex();
            categoriesOptionsListView.setItems(categoriesList);
            categoriesOptionsListView.refresh();
            categoriesOptionsListView.getSelectionModel().select(index);
            categoriesOptionsListView.getFocusModel().focus(index);

            index = loanerLoansController.getTableViewWindow().getSelectionModel().getFocusedIndex();
            loanerLoansController.getTableViewWindow().setItems(loanerLoansPaymentList);
            loanerLoansController.getTableViewWindow().refresh();
            loanerLoansController.getTableViewWindow().getSelectionModel().select(index);
            loanerLoansController.getTableViewWindow().getFocusModel().focus(index);

            index = notificationsListView.getSelectionModel().getSelectedIndex();
            notificationsListView.setItems(notificationsList);
            notificationsListView.refresh();
            notificationsListView.getSelectionModel().select(index);
            notificationsListView.getFocusModel().focus(index);

        });
    }

    public void startChatListRefresher(){
        chatController.startChatAreaRefresher();
        chatController.startUsersRefresher();
    }

    public String getCurrentCustomerName(){
        return mainController.getCurrentCustomerName();
    }

    @FXML
    void activateLoadLoansButton(ActionEvent event) {
        if(isInRewindMode){
            return;
        }

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("XML files", "*.xml");
        fileChooser.getExtensionFilters().add(fileExtension);
        fileChooser.setTitle("Choose an XML file");
        File xmlFile = fileChooser.showOpenDialog(new Stage());

        if (xmlFile == null)
            return;

        String fullPathName = xmlFile.getAbsolutePath();
        File f = new File(fullPathName);

        if (!checkFileType(fullPathName)) {
            invalidXmlFileLabel.setText("Invalid file. You need to choose an XML file.");
            return;
        }

        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/xml")))
                        .build();

        String finalUrl = HttpUrl
                .parse(Constants.LOAD_XML_FILE)
                .newBuilder()
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .post(body)
                .build();

        HttpClientUtil.runAsync2(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Unknown Error");
                    alert.showAndWait();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != 200){
                    Platform.runLater(() ->
                    {
                        try {
                            invalidXmlFileLabel.setText(response.body().string());
                            response.body().close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else{
                    Platform.runLater(() ->
                    {
                        try {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, response.body().string());
                            response.body().close();
                            alert.showAndWait();
                            invalidXmlFileLabel.setText("");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        });
    }

    public boolean checkFileType(String pathName) {
        return getFileType(pathName).equals("xml");
    }
    public String getFileType(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    @FXML
    void activateUploadLoanButton(ActionEvent event) {
        if(isInRewindMode){
            return;
        }
        String loanId = loanIdTextField.getText();
        String loanGoal = loanGoalTextField.getText();
        String totalSumOfLoan = totalSumOfLoanTextField.getText();
        String totalYazTime = totalYazTimeTextField.getText();
        String interestPerPayment = interestPerPaymentTextField.getText();
        String paysEveryYaz = paysEveryYazTextField.getText();

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.UPLOAD_LOAN_MANUALLY)
                .newBuilder()
                .addQueryParameter("username", getCurrentCustomerName())
                .addQueryParameter("loanId", loanId)
                .addQueryParameter("loanGoal", loanGoal)
                .addQueryParameter("totalSumOfLoan", totalSumOfLoan)
                .addQueryParameter("totalYazTime", totalYazTime)
                .addQueryParameter("interestPerPayment", interestPerPayment)
                .addQueryParameter("paysEveryYaz", paysEveryYaz)
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
                    {
                        invalidManuallyLoanLabel.setText(responseBody);
                        response.body().close();
                    });
                } else {
                    Platform.runLater(() -> {
                        clearAddLoanLabels();
                    });
                }
            }
        });
    }

    public void clearAddLoanLabels(){
        loanIdTextField.setText("");
        loanGoalTextField.setText("");
        totalSumOfLoanTextField.setText("");
        totalYazTimeTextField.setText("");
        interestPerPaymentTextField.setText("");
        paysEveryYazTextField.setText("");
        invalidManuallyLoanLabel.setText("");
    }

    @FXML
    public void activateSaleLoanButton(ActionEvent event) {
        if(isInRewindMode){
            return;
        }
        Loan selectedLoanToSale = investorLoansInformationController.getTableViewWindow().getSelectionModel().getSelectedItem();

        String jsonSelectedCategories = GSON_INSTANCE.toJson(selectedLoanToSale, Loan.class);
        RequestBody body = RequestBody.create(jsonSelectedCategories, MediaType.parse("application/json charset=UTF-8"));

        String finalUrl = HttpUrl
                .parse(Constants.SALE_LOANS)
                .newBuilder()
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .post(body)
                .build();

        HttpClientUtil.runAsync2(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Unknown Error");
                    alert.showAndWait();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != 200){
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            loanIsAlreadyInListLabel.setText(responseBody)
                    );

                } else{
                    Platform.runLater(() ->
                    {
                        loansForSaleInformationController.getTableViewWindow().getItems().add(selectedLoanToSale);
                        loanIsAlreadyInListLabel.setText("");
                    });
                }
            }
        });
    }
    @FXML
    public void activateBuyLoanButton(ActionEvent event) {
        if(isInRewindMode){
            return;
        }
        Loan selectedLoanToBuy = loansForSaleInformationController.getTableViewWindow().getSelectionModel().getSelectedItem();
        if(selectedLoanToBuy.getLoanerName().equals(getCurrentCustomerName())){
            currentCustomerOwnLoanLabel.setText("You can't buy your own loan");
            return;
        }

        String jsonSelectedCategories = GSON_INSTANCE.toJson(selectedLoanToBuy, Loan.class);
        RequestBody body = RequestBody.create(jsonSelectedCategories, MediaType.parse("application/json charset=UTF-8"));

        String finalUrl = HttpUrl
                .parse(Constants.BUY_LOANS)
                .newBuilder()
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .post(body)
                .build();

        HttpClientUtil.runAsync2(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Unknown Error");
                    alert.showAndWait();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != 200){
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                        currentCustomerOwnLoanLabel.setText(responseBody)
                    );
                } else{
                    Platform.runLater(() ->
                    {
                        currentCustomerOwnLoanLabel.setText("");
                    });
                }
            }
        });
    }

    @FXML
    void activatePayLoanSinglePaymentButton(ActionEvent event){
        if(isInRewindMode){
            if (!payLoanSinglePaymentTextField.getText().isEmpty()){
                payLoanSinglePaymentTextField.setText("");
            }
            return;
        }
        Loan selectedLoanToPay = loanerLoansController.getTableViewWindow().getSelectionModel().getSelectedItem();
        String selectedLoanToPayId;
        int amountToPay;

        if (selectedLoanToPay == null){
            return;
        }
        else{
            selectedLoanToPayId = selectedLoanToPay.getLoanId();
        }
        if (payLoanSinglePaymentTextField.disableProperty().get() || payLoanSinglePaymentTextField.getText().isEmpty()){
            amountToPay = selectedLoanToPay.getSinglePayment();
        }
        else{
            amountToPay = Integer.parseInt(payLoanSinglePaymentTextField.getText());
        }

        SinglePaymentDTO singlePaymentDTO = new SinglePaymentDTO(selectedLoanToPayId, amountToPay);

        String jsonSelectedCategories = GSON_INSTANCE.toJson(singlePaymentDTO, SinglePaymentDTO.class);
        RequestBody body = RequestBody.create(jsonSelectedCategories, MediaType.parse("application/json charset=UTF-8"));

        String finalUrl = HttpUrl
                .parse(Constants.PAY_SINGLE_PAYMENT)
                .newBuilder()
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .post(body)
                .build();

        HttpClientUtil.runAsync2(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Unknown Error");
                    alert.showAndWait();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != 200){
                    Platform.runLater(() ->
                    {
                        try {
                            paymentLabel.setText(response.body().string());
                            response.body().close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else{
                    Platform.runLater(() ->
                    {
                        try {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, response.body().string());
                            response.body().close();
                            alert.showAndWait();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        payLoanSinglePaymentTextField.setDisable(true);
                        payLoanSinglePaymentTextField.setText("");
                        paymentLabel.setText("");
                    });
                }
            }
        });
    }

    @FXML
    void activateCloseEntireLoanButton(ActionEvent event){
        if(isInRewindMode){
            if (!payLoanSinglePaymentTextField.getText().isEmpty()){
                payLoanSinglePaymentTextField.setText("");
            }
            return;
        }
        Loan selectedLoanToClose = loanerLoansController.getTableViewWindow().getSelectionModel().getSelectedItem();
        String selectedLoanToCloseId;
        if (selectedLoanToClose == null){
            return;
        }
        else{
            selectedLoanToCloseId = selectedLoanToClose.getLoanId();
        }

        String jsonSelectedCategories = GSON_INSTANCE.toJson(selectedLoanToCloseId, String.class);
        RequestBody body = RequestBody.create(jsonSelectedCategories, MediaType.parse("application/json charset=UTF-8"));

        String finalUrl = HttpUrl
                .parse(Constants.CLOSE_ENTIRE_LOAN)
                .newBuilder()
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .post(body)
                .build();

        HttpClientUtil.runAsync2(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Unknown Error");
                    alert.showAndWait();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != 200){
                    Platform.runLater(() ->
                    {
                        try {
                            paymentLabel.setText(response.body().string());
                            response.body().close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else{
                    Platform.runLater(() ->
                    {
                        try {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, response.body().string());
                            response.body().close();
                            alert.showAndWait();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        paymentLabel.setText("");
                    });
                }
            }
        });
    }
/*
    public void updateLoanerLoansInformationTable(){
        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.GET_LOANER_LOANS_INFORMATION)
                .newBuilder()
                .addQueryParameter("username", getCurrentCustomerName())
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
                        try {
                            String jsonOfClientString = response.body().string();
                            response.body().close();
                            Loan[] loansList = GSON_INSTANCE.fromJson(jsonOfClientString, Loan[].class);
                            ObservableList<Loan> loans = FXCollections.observableArrayList();
                            loans.clear();
                            loans.addAll(Arrays.asList(loansList));

                            loanerLoansInformationController.getTableViewWindow().setItems(loans);
                            loanerLoansInformationController.getTableViewWindow().refresh();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //customerBodyController.updateClientScreen(customerName);
                        //amountTextField.clear();
                    });
                }
            }
        });

    }

    public void updateLenderLoansInformationTable(){
        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.GET_LENDER_LOANS_INFORMATION)
                .newBuilder()
                .addQueryParameter("username", getCurrentCustomerName())
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
                    response.body().close();
                    Platform.runLater(() ->
                            System.out.println("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        try {
                            String jsonOfClientString = response.body().string();
                            response.body().close();
                            Loan[] loansList = GSON_INSTANCE.fromJson(jsonOfClientString, Loan[].class);
                            ObservableList<Loan> loans = FXCollections.observableArrayList();
                            loans.clear();
                            loans.addAll(Arrays.asList(loansList));
                            System.out.println(loans);

                            investorLoansInformationController.getTableViewWindow().setItems(loans);
                            investorLoansInformationController.getTableViewWindow().refresh();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //customerBodyController.updateClientScreen(customerName);
                        //amountTextField.clear();
                    });
                }
            }
        });

    }
*/

    public void resetScrambleTabScreen(){

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.GET_CATEGORIES_LIST)
                .newBuilder()
                .addQueryParameter("username", getCurrentCustomerName())
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
                    response.body().close();
                    Platform.runLater(() ->
                            System.out.println("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        try {
                            String jsonOfClientString = response.body().string();
                            response.body().close();
                            String[] categoriesList = GSON_INSTANCE.fromJson(jsonOfClientString, String[].class);
                            ObservableList<String> categories = FXCollections.observableArrayList();
                            categories.clear();
                            categories.addAll(Arrays.asList(categoriesList));

                            //categoriesOptionsListView.setItems(categories);
                            //categoriesOptionsListView.refresh();

                            userChoiceCategoriesListView.getItems().clear();
                            amountToInvestTextField.clear();
                            minimumInterestTextField.clear();
                            minimumYazTextField.clear();
                            maxOwnershipTextField.clear();
                            maxOpenLoansTextField.clear();
                            relevantLoansController.getTableViewWindow().getItems().clear();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }
/*
    public void turnCategoriesListViewsToMultipleSelection(){
        categoriesOptionsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userChoiceCategoriesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
*/

    @FXML
    void activateForwardCategoriesButton(ActionEvent event) {
        if(isInRewindMode){
            return;
        }
        List<String> selectedCategories = categoriesOptionsListView.getSelectionModel().getSelectedItems();
        userChoiceCategoriesListView.getItems().addAll(selectedCategories);
        categoriesOptionsListView.getItems().removeAll(selectedCategories);
    }

    @FXML
    void activateBackwardCategoriesButton(ActionEvent event) {
        if(isInRewindMode){
            return;
        }
        List<String> selectedCategories = userChoiceCategoriesListView.getSelectionModel().getSelectedItems();
        categoriesOptionsListView.getItems().addAll(selectedCategories);
        userChoiceCategoriesListView.getItems().removeAll(selectedCategories);
    }

    public void resetScrambleLabels(){
        userChoiceCategoriesListView.getItems().clear();
        amountToInvestTextField.clear();
        minimumInterestTextField.clear();
        minimumYazTextField.clear();
        maxOwnershipTextField.clear();
        maxOpenLoansTextField.clear();
        amountToInvestLabel.setText("");
        relevantLoansController.getTableViewWindow().getItems().clear();
    }

    @FXML
    void activateShowRelevantLoansListButton(ActionEvent event) {
        if(isInRewindMode){
            return;
        }
        List<String> userCategoriesList = userChoiceCategoriesListView.getItems();
        int minimumInterest, minimumYaz, maxOpenLoans;

        if(userCategoriesList.isEmpty()) {
            userCategoriesList.addAll(categoriesOptionsListView.getItems());
            userCategoriesList = userChoiceCategoriesListView.getItems();
        }

        minimumInterest = (minimumInterestTextField.getText().isEmpty()) ? 0 : (Integer.parseInt(minimumInterestTextField.getText()));
        minimumYaz = (minimumYazTextField.getText().isEmpty()) ? 0 : (Integer.parseInt(minimumYazTextField.getText()));
        maxOpenLoans = (maxOpenLoansTextField.getText().isEmpty()) ? Integer.MAX_VALUE : (Integer.parseInt(maxOpenLoansTextField.getText()));

        RelevantLoansListDTO relevantLoansListDTO = new RelevantLoansListDTO(getCurrentCustomerName(), userCategoriesList, minimumInterest, minimumYaz, maxOpenLoans);
        String jsonSelectedCategories = GSON_INSTANCE.toJson(relevantLoansListDTO, RelevantLoansListDTO.class);
        RequestBody body = RequestBody.create(jsonSelectedCategories, MediaType.parse("application/json charset=UTF-8"));

        String finalUrl = HttpUrl
                .parse(Constants.SHOW_RELEVANT_LOANS)
                .newBuilder()
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .post(body)
                .build();

        HttpClientUtil.runAsync2(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Unknown Error");
                    alert.showAndWait();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != 200){
                    Platform.runLater(() ->
                    {
                        try {

                            Alert alert = new Alert(Alert.AlertType.ERROR,response.body().string());
                            alert.showAndWait();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else{
                    Platform.runLater(() ->
                    {
                        try {
//                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,response.body().string());
//                            alert.showAndWait();
                            String jsonOfClientString = response.body().string();
                            response.body().close();
                            Loan[] relevantLoansArray = GSON_INSTANCE.fromJson(jsonOfClientString, Loan[].class);
                            ObservableList<Loan> relevantLoansList = FXCollections.observableArrayList();
                            relevantLoansList.clear();
                            relevantLoansList.addAll(Arrays.asList(relevantLoansArray));

                            relevantLoansController.updateLoansInformationTables(relevantLoansList);
                            relevantLoansController.getTableViewWindow().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                            clearWrongInputLabels();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        });
    }

    public void clearWrongInputLabels(){
        amountToInvestLabel.setText("");
        minInterestLabel.setText("");
        minYazLabel.setText("");
        maxOpenLoansLabel.setText("");
        maxOwnershipLabel.setText("");
    }

    @FXML
    void activateActivateScrambleButton(ActionEvent event) {
        if(isInRewindMode){
            return;
        }
        List<Loan> loansToInvest = new ArrayList<>(relevantLoansController.getTableViewWindow().getSelectionModel().getSelectedItems());
        List<String> loansToInvestAsStrings = new ArrayList<>();
        int maxOwnershipPercentage = (maxOwnershipTextField.getText().isEmpty()) ? 100 : (Integer.parseInt(maxOwnershipTextField.getText()));
        int amountToInvest;

        if (amountToInvestTextField.getText().isEmpty()){
            amountToInvestLabel.setText("Please enter an amount to invest.");
            return;
        }
        else {
            amountToInvest = Integer.parseInt(amountToInvestTextField.getText());
        }

        for (Loan loan : loansToInvest){
            loansToInvestAsStrings.add(loan.getLoanId());
        }
        ScrambleDTO scrambleDTO = new ScrambleDTO(getCurrentCustomerName(), loansToInvestAsStrings, amountToInvest, maxOwnershipPercentage);

        String jsonLoansToInvest = GSON_INSTANCE.toJson(scrambleDTO, ScrambleDTO.class);
        RequestBody body = RequestBody.create(jsonLoansToInvest, MediaType.parse("application/json charset=UTF-8"));

        String finalUrl = HttpUrl
                .parse(Constants.LOANS_TO_INVEST)
                .newBuilder()
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .post(body)
                .build();

        HttpClientUtil.runAsync2(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Unknown Error");
                    alert.showAndWait();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != 200){
                    Platform.runLater(() ->
                    {
                        try {
                            amountToInvestLabel.setText(response.body().string());
                            response.body().close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else{
                    Platform.runLater(() ->
                    {
                        try {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, response.body().string());
                            response.body().close();
                            alert.showAndWait();
                            relevantLoansController.getTableViewWindow().getItems().clear();
                            relevantLoansController.getTableViewWindow().refresh();
                            resetScrambleLabels();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        });

    }
/*
    public void updateSaleLoanButton(){
        if (investorLoansInformationController.getTableViewWindow().getSelectionModel().getSelectedItem() == null) {
            saleLoanButton.setDisable(true);
        }
    }
*/
/*    public void updatePaymentsScreen(){
        if (loanerLoansController.getTableViewWindow().getSelectionModel().getSelectedItem() == null) {
            payLoanSinglePaymentButton.setDisable(true);
            payLoanSinglePaymentTextField.setDisable(true);
        }
        ObservableList<Loan> loanerLoansToPay = FXCollections.observableArrayList();
        ObservableList<Notification> notificationsList = FXCollections.observableArrayList(mainController.getClientNotificationsListFromDatabase(getCurrentCustomerName()));
        for (Loan loan: mainController.getClientLoansTakenListFromDatabase(getCurrentCustomerName())) {
            if(loan.getStatus().equals(Loan.Status.ACTIVE) || loan.getStatus().equals(Loan.Status.RISK)){
                loanerLoansToPay.add(loan);
            }
        }
        //loanerLoansController.getTableViewWindow().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        notificationsListView.setItems(notificationsList);
        loanerLoansController.getTableViewWindow().setItems(loanerLoansToPay);
        loanerLoansController.getTableViewWindow().refresh();
        payLoanSinglePaymentTextField.clear();
    }*/
}
