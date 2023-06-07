package chat;

import body.CustomerBodyController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import static util.Constants.CHAT_LINE_FORMATTING;
import static util.Constants.REFRESH_RATE;

public class ChatController {
    private CustomerBodyController customerBodyController;

    //private final IntegerProperty chatVersion;
    private final BooleanProperty autoScroll;
    private final BooleanProperty chatAreaAutoUpdate;
    private ChatAreaRefresher chatAreaRefresher;
    private Timer chatAreaTimer;

    public ChatController() {
        //chatVersion = new SimpleIntegerProperty();
        autoScroll = new SimpleBooleanProperty();
        chatAreaAutoUpdate = new SimpleBooleanProperty();

        usersAutoUpdate = new SimpleBooleanProperty();
        totalUsers = new SimpleIntegerProperty();
    }

    @FXML
    public void initialize() {
        chatUsersLabel.textProperty().bind(Bindings.concat("Chat Users: (", totalUsers.asString(), ")"));
    }

    public void setCustomerBodyController(CustomerBodyController customerBodyController) {
        this.customerBodyController = customerBodyController;
    }

    @FXML private Label chatUsersLabel;
    @FXML private ListView<String> usersListView;
    @FXML private Button sendMessageButton;
    @FXML private TextArea mainChatLinesTextArea;
    @FXML private TextArea chatLineTextArea;


    @FXML void activateSendMessageButton(ActionEvent event){
        if(customerBodyController.getIsInRewindMode()){
            return;
        }
        String chatLine = chatLineTextArea.getText();
        String finalUrl = HttpUrl
                .parse(Constants.SEND_CHAT_LINE)
                .newBuilder()
                .addQueryParameter("userstring", chatLine)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //httpStatusUpdate.updateHttpLine("Attempt to send chat line [" + chatLine + "] request ended with failure...:(");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    //httpStatusUpdate.updateHttpLine("Attempt to send chat line [" + chatLine + "] request ended with failure. Error code: " + response.code());
                }
            }
        });

        chatLineTextArea.clear();

    }

    public void startChatAreaRefresher() {
        chatAreaRefresher = new ChatAreaRefresher(
//                chatVersion,
                chatAreaAutoUpdate,
                this::updateChatLines);
        chatAreaTimer = new Timer();
        chatAreaTimer.schedule(chatAreaRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateChatLines(ChatLinesWithVersion chatLinesWithVersion) {
//        if (chatLinesWithVersion.getVersion() != chatVersion.get()) {
            //IndexRange indexRange = mainChatLinesTextArea.getSelection();

            String chatLines = chatLinesWithVersion
                    .getEntries()
                    .stream()
                    .map(singleChatLine -> {
                        long time = singleChatLine.getTime();
                        return String.format(CHAT_LINE_FORMATTING, time, time, time, singleChatLine.getUsername(), singleChatLine.getChatString());
                    }).collect(Collectors.joining());

            Platform.runLater(() -> {
//                chatVersion.set(chatLinesWithVersion.getVersion());

                if (autoScroll.get()) {
                    mainChatLinesTextArea.setText(chatLines);
                    mainChatLinesTextArea.selectPositionCaret(mainChatLinesTextArea.getLength());
                    mainChatLinesTextArea.deselect();
                } else {
                    int originalCaretPosition = mainChatLinesTextArea.getCaretPosition();
                    mainChatLinesTextArea.setText(chatLines);
                    mainChatLinesTextArea.positionCaret(originalCaretPosition);
                }
            });
//        }
    }

    private Timer usersTimer;
    private TimerTask listRefresher;
    private final BooleanProperty usersAutoUpdate;
    private final IntegerProperty totalUsers;

    public BooleanProperty autoUpdatesProperty() {
        return usersAutoUpdate;
    }

    private void updateUsersList(List<String> usersNames) {
        Platform.runLater(() -> {
            int index = usersListView.getSelectionModel().getSelectedIndex();
            ObservableList<String> items = usersListView.getItems();
            items.clear();
            items.addAll(usersNames);
            usersListView.getSelectionModel().select(index);
            usersListView.getFocusModel().focus(index);

            totalUsers.set(usersNames.size());
        });
    }

    public void startUsersRefresher() {
        listRefresher = new UserListRefresher(
                usersAutoUpdate,
                this::updateUsersList);
        usersTimer = new Timer();
        usersTimer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }

}
