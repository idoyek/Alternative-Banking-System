package body;

import DTO.AdminScreenRefresherDTO;
import client.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import tableViews.CustomersInformationController;
import tableViews.LoansInformationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.MainController;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.*;

import static util.Constants.GSON_INSTANCE;
import static util.Constants.REFRESH_RATE;

public class BodyController {
    private MainController mainController;
    private boolean isInRewindMode;

    @FXML private AnchorPane loansInformation;
    @FXML private LoansInformationController loansInformationController;
    @FXML private AnchorPane customersInformation;
    @FXML private CustomersInformationController customersInformationController;

    @FXML private Button showListViewButton;
    @FXML private Button increaseYazButton;
    @FXML private Label loansInformationLabel;
    @FXML private Label customersInformationLabel;
    @FXML private ListView<String> customerLoansInformationByStatusListView;
    @FXML private Label invalidXmlFileLabel;
    @FXML Button enterRewindModeButton;
    @FXML Button exitRewindModeButton;
    @FXML ComboBox<Integer> rewindTimeComboBox;

    public BodyController(){
        autoUpdate = new SimpleBooleanProperty();
        totalUsers = new SimpleIntegerProperty();
    }

    public void initialize(){
        rewindTimeComboBox.setDisable(true);
        rewindTimeComboBox.getItems().add(1);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;

        if (loansInformation != null && customersInformation != null) {
            loansInformationController.setBodyController(this);
            customersInformationController.setBodyController(this);
        }
    }

    @FXML
    void activateRewindTimeComboBox(ActionEvent event){
        int yazToMove = rewindTimeComboBox.getSelectionModel().getSelectedItem();

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.REWIND_HISTORY)
                .newBuilder()
                .addQueryParameter("yazToMove", String.valueOf(yazToMove))
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

                    });
                }
            }
        });

    }
    @FXML
    void activateEnterRewindModeButton(ActionEvent event){
        isInRewindMode = true;

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.UPDATE_IF_IN_REWIND_MODE)
                .newBuilder()
                .addQueryParameter("isInRewindMode", String.valueOf(isInRewindMode))
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
                        rewindTimeComboBox.setDisable(false);
                    });
                }
            }
        });

    }
    @FXML
    void activateExitRewindModeButton(ActionEvent event){
        isInRewindMode = false;

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.UPDATE_IF_IN_REWIND_MODE)
                .newBuilder()
                .addQueryParameter("isInRewindMode", String.valueOf(isInRewindMode))
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
                        rewindTimeComboBox.getSelectionModel().select(rewindTimeComboBox.getItems().size());
                        rewindTimeComboBox.setValue(rewindTimeComboBox.getItems().size());
                        rewindTimeComboBox.setDisable(true);
                    });
                }
            }
        });

    }

    @FXML
    void activateShowListViewButton(ActionEvent event) {
        customerLoansInformationByStatusListView.getItems().clear();
        Client client = customersInformationController.getTableView().getSelectionModel().getSelectedItem();
        int newCounter = 0, pendingCounter = 0, activeCounter = 0, riskCounter = 0, finishedCounter = 0;
        String loanerLoansSummary, lenderLoansSummary;

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
        if (isInRewindMode){
            return;
        }
        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.INCREASE_YAZ)
                .newBuilder()
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
                        try{
                            String jsonOfClientString = response.body().string();
                            response.body().close();
                            int currentYaz = GSON_INSTANCE.fromJson(jsonOfClientString, int.class);

                            rewindTimeComboBox.getItems().add(currentYaz);

                            rewindTimeComboBox.getSelectionModel().select(rewindTimeComboBox.getItems().size());
                            rewindTimeComboBox.setValue(rewindTimeComboBox.getItems().size());

                        } catch (IOException e) {
                        e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    private Timer timer;
    private TimerTask listRefresher;
    private final BooleanProperty autoUpdate;
    private final IntegerProperty totalUsers;

    public void startListRefresher(){
        autoUpdate.set(true);
        listRefresher = new AdminScreenRefresher(
                autoUpdate,
                this::updateUsersList);
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);

    }

    private void updateUsersList(AdminScreenRefresherDTO adminScreenRefresherDTO) {
        Platform.runLater(() -> {

            isInRewindMode = adminScreenRefresherDTO.getIsInRewindMode();
            ObservableList<Client> clientsList = FXCollections.observableArrayList(adminScreenRefresherDTO.getClientsList());
            ObservableList<Loan> loansList = FXCollections.observableArrayList(adminScreenRefresherDTO.getLoansList());
            Yaz yaz = adminScreenRefresherDTO.getYaz();
            int index;

            if (isInRewindMode){
                mainController.activateRewindModeLabel();
            }
            else{
                mainController.clearRewindModeLabel();
            }

            index = customersInformationController.getTableView().getSelectionModel().getFocusedIndex();
            customersInformationController.getTableView().setItems(clientsList);
            customersInformationController.getTableView().refresh();
            customersInformationController.getTableView().getSelectionModel().select(index);
            customersInformationController.getTableView().getFocusModel().focus(index);

            index = loansInformationController.getTableViewWindow().getSelectionModel().getFocusedIndex();
            loansInformationController.getTableViewWindow().setItems(loansList);
            loansInformationController.getTableViewWindow().refresh();
            loansInformationController.getTableViewWindow().getSelectionModel().select(index);
            loansInformationController.getTableViewWindow().getFocusModel().focus(index);

            mainController.updateYaz(yaz);
        });
    }
}
