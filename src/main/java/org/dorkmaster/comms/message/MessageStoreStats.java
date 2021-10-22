package org.dorkmaster.comms.message;

import java.util.HashMap;
import java.util.Map;

public class MessageStoreStats {

    public long produced = 0L;
    public long consumed = 0L;
    public Map<String, Integer> channels = new HashMap<>();

    public long getProduced() {
        return produced;
    }

    public MessageStoreStats setProduced(long produced) {
        this.produced = produced;
        return this;
    }

    public long getConsumed() {
        return consumed;
    }

    public MessageStoreStats setConsumed(long consumed) {
        this.consumed = consumed;
        return this;
    }

    public MessageStoreStats addChannel(String name, Integer size) {
        channels.put(name, size);
        return this;
    }
}
