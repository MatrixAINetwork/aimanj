package org.aimanj.protocol.websocket;

import java.io.IOException;

/**
 * A listener used to notify about about new WebSocket messages.
 */
public interface WebSocketListener {

    /**
     * Called when a new WebSocket message is delivered.
     *
     * @param message new WebSocket message
     * @throws IOException thrown if an observer failed to process the message
     */
    void onMessage(String message) throws IOException;

    void onError(Exception e);

    void onClose();
}
