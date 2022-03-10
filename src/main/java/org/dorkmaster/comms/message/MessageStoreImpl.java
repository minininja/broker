package org.dorkmaster.comms.message;

import org.dorkmaster.comms.exception.ChannelNotFoundException;
import org.dorkmaster.comms.exception.MessagePollerTimeoutException;
import org.dorkmaster.comms.util.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class MessageStoreImpl implements MessageStore {

    // TODO make this convfigurable via an environment variable
    public static final int QUEUE_LIMIT = new Param("QUEUE_LIMIT").asInt(10000);

    protected Map<String, BlockingQueue<String>> store = new ConcurrentHashMap<>();
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected AtomicLong published = new AtomicLong(0L);
    protected AtomicLong consumed = new AtomicLong(0L);

    @Override
    public void reset() {
        store.clear();
    }

    @Override
    public void pushMessage(String channel, String message) {
        BlockingQueue<String> queue = store.get(channel);
        if (null == queue) {
            queue = new ArrayBlockingQueue<>(QUEUE_LIMIT);
            store.put(channel, queue);
        }
        logger.info("Adding message on {} channel", channel);
        queue.add(message);
        published.incrementAndGet();
        logger.info("Added message on {} channel", channel);
    }

    @Override
    public String pullMessage(String channel) {
        BlockingQueue<String> queue = store.get(channel);
        if (null != queue) {
            try {
                String mesg = queue.poll(60, TimeUnit.SECONDS);
                consumed.incrementAndGet();
                logger.info("Found message waiting on channel {}", channel);
                return mesg;
            } catch (InterruptedException e) {
                logger.info("Timeout waiting on channel {}", channel);
                throw new MessagePollerTimeoutException(channel);
            }
        }
        logger.info("Channel {} not found", channel);
        throw new ChannelNotFoundException(channel);
    }

    @Override
    public MessageStoreStats stats() {
        MessageStoreStats mss = new MessageStoreStats()
                .setConsumed(consumed.get())
                .setProduced(published.get());
        for (String key : store.keySet()) {
            mss.addChannel(key, store.get(key).size());
        }
        return mss;
    }
}
