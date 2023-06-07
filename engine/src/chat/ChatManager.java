package chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatManager implements Serializable {
    private final List<SingleChatEntry> chatDataList = new ArrayList();

    public synchronized void addChatString(String chatString, String username) {
        this.chatDataList.add(new SingleChatEntry(chatString, username));
    }

    public synchronized List<SingleChatEntry> getChatEntries() {
        return chatDataList;
    }

//    public int getVersion() {
//        return this.chatDataList.size();
//    }

}
