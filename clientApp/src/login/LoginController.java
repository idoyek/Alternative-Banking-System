package login;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.MainController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;

import static util.Constants.CLIENT_SCREEN;

public class LoginController {
    private MainController mainController;
    private final StringProperty screenTypeName;
    private final StringProperty errorMessageProperty = new SimpleStringProperty();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        helloUserLabel.textProperty().bind(Bindings.concat(screenTypeName));
        errorMessageLabel.textProperty().bind(errorMessageProperty);
    }

    public LoginController(){
        screenTypeName = new SimpleStringProperty(CLIENT_SCREEN);
    }

    @FXML private Label helloUserLabel;
    @FXML public Label errorMessageLabel;
    @FXML private TextField userNameTextField;

    @FXML void activateUserNameTextField(ActionEvent event) {

    }

    @FXML
    void activateLoginButton(ActionEvent event) {

        String userName = userNameTextField.getText();
        if (userName.isEmpty()) {
            errorMessageProperty.set("User name is empty. You can't login with empty user name.");
            return;
        }

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.LOGIN_PAGE)
                .newBuilder()
                .addQueryParameter("username", userName)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            errorMessageProperty.set(responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        try {
                            mainController.updateHeaderUserName(userName);
                            mainController.clientScreen();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }
}
