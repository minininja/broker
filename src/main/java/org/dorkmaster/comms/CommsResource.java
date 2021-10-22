package org.dorkmaster.comms;

import io.smallrye.common.annotation.Blocking;
import org.dorkmaster.comms.message.MessageStore;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/v1/comms")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class CommsResource {

    @Inject
    protected MessageStore messageStore;

    @POST
    @Blocking
    public String publish(String content) {
        return publish(UUID.randomUUID().toString(), content);
    }

    @POST
    @Blocking
    @Path("/{channel}")
    public String publish(@PathParam("channel") String channel, String content) {
        messageStore.pushMessage(channel, content);
        return channel;
    }

    @GET
    @Blocking
    @Path("/{channel}")
    public String consume(@PathParam("channel") String channel) {
        String content = messageStore.pullMessage(channel);
        if (null == content) {
            throw new NotFoundException();
        }
        return content;
//        return Uni.createFrom().item(content);
    }

}
