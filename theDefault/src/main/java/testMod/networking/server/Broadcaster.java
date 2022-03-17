package testMod.networking.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.networking.messages.AbstractMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Broadcaster extends Thread {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public Broadcaster(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object object = inputStream.readObject();
                if (object instanceof AbstractMessage) {
                    broadcast((AbstractMessage) object);
                } else {
                    logger.warn("Received invalid message from client");
                }
            } catch (SocketException e) {
                try {
                    socket.close();
                    ConnectionManager.removeConnection(outputStream);
                    logger.info("Closed socket: " + socket.getRemoteSocketAddress());
                    break;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            catch (ClassNotFoundException | IOException e) {
                logger.warn("Received invalid action from client");
            }
        }
    }

    private void broadcast(AbstractMessage message) throws IOException {
        logger.info("Sending " + message.getClass().getSimpleName() + " to " + (message.broadcast ? "ALL" : "OTHER") + " clients: " + message.getClass().getSimpleName());
        for (int i = 0; i < ConnectionManager.MAX_NUM_CONNECTIONS; i++) { // TODO: Make this only loop through numConnections, needs to move things back if someone disconnects
            ObjectOutputStream oos = ConnectionManager.getConnection(i);
            if (message.broadcast || (oos != null && oos != outputStream)) { // TODO: If we do what it says above we don't need to check to see if its null
                oos.writeObject(message);
                oos.flush(); // TODO: Is flush necessary?
            }
        }
    }
}
