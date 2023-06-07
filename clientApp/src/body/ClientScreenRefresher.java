package body;

import DTO.ClientScreenRefresherDTO;
import DTO.DummyClientScreenRefresherDTO;
import client.Loan;
import util.Constants;
import util.http.HttpClientUtil;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.Constants.GSON_INSTANCE;

public class ClientScreenRefresher extends TimerTask {

    //private final Consumer<String> httpRequestLoggerConsumer;
    private final Consumer<ClientScreenRefresherDTO> usersListConsumer;
    private int requestNumber;
    private final BooleanProperty shouldUpdate;


    public ClientScreenRefresher(BooleanProperty shouldUpdate/*, Consumer<String> httpRequestLoggerConsumer*/, Consumer<ClientScreenRefresherDTO> usersListConsumer) {
        this.shouldUpdate = shouldUpdate;
        //this.httpRequestLoggerConsumer = httpRequestLoggerConsumer;
        this.usersListConsumer = usersListConsumer;
        requestNumber = 0;
    }

    @Override
    public void run() {

        if (!shouldUpdate.get()) {
            return;
        }

        final int finalRequestNumber = ++requestNumber;
        //httpRequestLoggerConsumer.accept("About to invoke: " + Constants.USERS_LIST + " | Users Request # " + finalRequestNumber);
        HttpClientUtil.runAsync(Constants.CLIENT_SCREEN_REFRESH, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");
                System.out.println("hello");

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfUsersNames = response.body().string();
                //httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Response: " + jsonArrayOfUsersNames);

                //Loan[] usersNames = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, Loan[].class);

                ClientScreenRefresherDTO clientScreenRefresherDTO = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, ClientScreenRefresherDTO.class);
                usersListConsumer.accept(clientScreenRefresherDTO);

/*
                DummyClientScreenRefresherDTO dummyClientScreenRefresherDTO = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, DummyClientScreenRefresherDTO.class);
                usersListConsumer.accept(dummyClientScreenRefresherDTO);
*/

            }
        });
    }
}
