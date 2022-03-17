package testMod.networking.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.networking.messages.MetadataMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class ConnectionManager extends Thread {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());
    public static final int MAX_NUM_CONNECTIONS = 4;

    private static final AtomicReferenceArray<ObjectOutputStream> clients = new AtomicReferenceArray<>(MAX_NUM_CONNECTIONS);

    private final int port;

    public ConnectionManager(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            logger.info("Hosting server on " + serverSocket.getLocalSocketAddress());

            while (true) {
                logger.info("Waiting for connection...");
                Socket clientSocket = serverSocket.accept();
                logger.info("Established connection with client " + clientSocket.getRemoteSocketAddress());

                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

                clients.set(TestMod.network.numCurrentConnections++, oos);

                Broadcaster broadcaster = new Broadcaster(clientSocket, ois, oos);
                broadcaster.start();
                broadcaster.setName("Broadcaster " + TestMod.network.numCurrentConnections); // TODO: Probably name it IP address of client or something

                for (int i = 0; i < TestMod.network.numCurrentConnections; ++i) {
                    clients.get(i).writeObject(new MetadataMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObjectOutputStream getConnection(int i) {
        return clients.get(i);
    }

    public static void removeConnection(ObjectOutputStream outputStreamToRemove) {
        // TODO: Move the rest of the clients back in the list, so that broadcast can simply loop over numConnections
        //  and never another null
        for (int i = 0; i < MAX_NUM_CONNECTIONS; i++) {
            if (clients.get(i) == outputStreamToRemove) {
                clients.set(i, null);
                break;
            }
        }
    }
}
