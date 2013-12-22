/*
 * Blah
 */
package JNetworkingX;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RlonRyan
 */
public class JHostX extends Thread {

    private int port;
    private boolean listening;
    private List<JNetworkListenerX> listeners;
    private int id = 0;

    public JHostX(int port) {
        this.listeners = new ArrayList<>();
        this.port = port;
        this.listening = true;
    }

    public void addListener(JNetworkListenerX listener) {
        this.listeners.add(listener);
    }

    public void removeListener(JNetworkListenerX listener) {
        this.listeners.remove(listener);
    }

    public void notifyListeners(String message) {
        for (JNetworkListenerX e : listeners) {
            e.onMessage(message);
        }
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Listening on port: " + serverSocket.getLocalPort() + ".");
            while (listening) {
                new JHostConnectionX(this, serverSocket.accept(), (id = id + 1)).start();
            }
        }
        catch (IOException e) {
            System.err.println("Could not listen on port " + this.port + ".");
            e.printStackTrace();
        }
    }
}
