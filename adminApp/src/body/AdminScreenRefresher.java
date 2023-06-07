package body;

import DTO.AdminScreenRefresherDTO;
import util.Constants;
import util.http.HttpClientUtil;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.Constants.GSON_INSTANCE;

public class AdminScreenRefresher extends TimerTask {

    private final Consumer<AdminScreenRefresherDTO> usersListConsumer;
    private int requestNumber;
    private final BooleanProperty shouldUpdate;


    public AdminScreenRefresher(BooleanProperty shouldUpdate/*, Consumer<String> httpRequestLoggerConsumer*/, Consumer<AdminScreenRefresherDTO> usersListConsumer) {
        this.shouldUpdate = shouldUpdate;
        this.usersListConsumer = usersListConsumer;
        requestNumber = 0;
    }

    @Override
    public void run() {

        if (!shouldUpdate.get()) {
            return;
        }

        final int finalRequestNumber = ++requestNumber;
        HttpClientUtil.runAsync(Constants.ADMIN_SCREEN_REFRESH, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("hello");

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfUsersNames = response.body().string();
                AdminScreenRefresherDTO adminScreenRefresherDTO = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, AdminScreenRefresherDTO.class);
                usersListConsumer.accept(adminScreenRefresherDTO);
            }
        });
    }
}
