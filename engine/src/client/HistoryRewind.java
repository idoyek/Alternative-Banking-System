package client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryRewind {
    private List<Database> databasesList;
    private boolean isInRewindMode;

    public HistoryRewind() {
        databasesList = new ArrayList<>();
    }

    public List<Database> getDatabasesList() {
        return databasesList;
    }

    public void addToDatabasesList(Database database) {
        databasesList.add(database);
    }

    public boolean getIsInRewindMode() {
        return isInRewindMode;
    }

    public void setIsInRewindMode(boolean isInRewindMode) {
        this.isInRewindMode = isInRewindMode;
    }
}
