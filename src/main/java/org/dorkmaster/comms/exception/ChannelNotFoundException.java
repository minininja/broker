package org.dorkmaster.comms.exception;

import javax.ws.rs.WebApplicationException;

public class ChannelNotFoundException extends WebApplicationException  {
    public ChannelNotFoundException(String message) {
        super(message, 410);
    }
}
