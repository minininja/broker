package org.dorkmaster.comms.exception;

import javax.ws.rs.WebApplicationException;

public class MessagePollerTimeoutException extends WebApplicationException {
    public MessagePollerTimeoutException(String message) {
        super(message, 408);
    }
}
