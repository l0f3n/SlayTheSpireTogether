package testMod.networking.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.networking.messages.AbstractMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

public class Receiver extends Thread {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    private Socket socket;
    private ObjectInputStream ois;

    public Receiver(Socket socket) {
        this.socket = socket;

        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (socket != null) {
            try {
                handleReceivedObject(ois.readObject());
            } catch (SocketException e) {
                try {
                    socket.close();
                    logger.info("Disconnected from server");
                    socket = null;
                    break;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleReceivedObject(Object object) {
        if (object instanceof AbstractMessage) {
            logger.info("Received MESSAGE from server: " + object.getClass().getSimpleName());
            ((AbstractMessage) object).execute();
        } else {
            logger.warn("Received invalid MESSAGE from server, ignoring...");
        }
    }
}
