package org.dorkmaster.comms.message;

import java.util.Map;

public interface MessageStore {
    void pushMessage(String channel, String message);
    String pullMessage(String channel);
    void reset();
    MessageStoreStats stats();
}
