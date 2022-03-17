package testMod.networking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.networking.client.Receiver;
import testMod.networking.client.Sender;
import testMod.networking.messages.AbstractMessage;
import testMod.networking.server.ConnectionManager;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Network {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 1234;

    public int numCurrentConnections = 0;

    // Synchronized variables
    public long seed;
    public int numPlayerHasEndedTurn = 0;

    private final LinkedBlockingQueue<Serializable> messages = new LinkedBlockingQueue<>();

    // Server
    private ConnectionManager connectionManager;

    // Client
    private Sender sender;
    private Receiver receiver;

    public void sendMessage(AbstractMessage message) {
        messages.offer(message);
    }

    public Serializable waitForAndGetMessage() throws InterruptedException {
        return messages.take();
    }

    public void startServer(int port) {
        if (isClient()) {
            logger.warn("Can't start server after client");
            return;
        }

        if (connectionManager != null) {
            logger.warn("Can't start server again");
            return;
        }

        connectionManager = new ConnectionManager(port);
        connectionManager.start();
        connectionManager.setName("Connection manager");
    }

    public void startClient(String host, int port) {
        if (receiver == null && sender == null) {
            try {
                Socket socket = new Socket(host, port);
                logger.info("Connected to " + host + ":" + port);

                sender = new Sender(socket);
                sender.start();
                sender.setName("Sender");

                receiver = new Receiver(socket);
                receiver.start();
                receiver.setName("Receiver");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("Attempted to start client again!");
        }
    }

    public boolean isHost() {
        return connectionManager != null;
    }

    public boolean isClient() {
        return sender != null && receiver != null && connectionManager == null;
    }

}
