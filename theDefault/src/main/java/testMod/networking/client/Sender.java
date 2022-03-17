package testMod.networking.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;

public class Sender extends Thread {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    private Socket socket;
    private ObjectOutputStream oos;

    public Sender(Socket socket) {
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (socket != null) {
            try {
                sendMessage(TestMod.network.waitForAndGetMessage());
            } catch (InterruptedException e) {
                logger.warn("Got interrupted while waiting for message");
            }
        }
    }

    private void sendMessage(Serializable o) {
        try {
            logger.info("Sending MESSAGE to server: " + o.getClass().getSimpleName());
            oos.writeObject(o);
            oos.flush(); // TODO: i don't really know what this does or if its necessary
        } catch (SocketException e) {
            try {
                socket.close();
                logger.info("Disconnected from server");
                socket = null;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
