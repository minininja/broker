package org.dorkmaster.comms;

import org.dorkmaster.comms.message.MessageStore;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/v1/reset")
public class ResetResource {
    @Inject
    protected MessageStore messageStore;

    @GET
    public void reset() {
        messageStore.reset();
    }
}
