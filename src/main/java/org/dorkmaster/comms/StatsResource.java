package org.dorkmaster.comms;

import org.dorkmaster.comms.message.MessageStore;
import org.dorkmaster.comms.message.MessageStoreStats;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.nio.channels.Channel;
import java.util.Map;

@Path("/v1/stats")
@Produces(MediaType.APPLICATION_JSON)
public class StatsResource {
    @Inject
    protected MessageStore messageStore;

    @GET
    public MessageStoreStats stats() {
        return messageStore.stats();
    }
}
