/*
 * Blah
 */
package JNetX;

import JNetX.JPacketX.JPackectX;
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
    private List<JHostConnectionX> connections;

    public JHostX(int port) {
        this.listeners = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.port = port;
        this.listening = false;
    }

    public boolean isListening() {
        return listening;
    }

    public void addListener(JNetworkListenerX listener) {
        this.listeners.add(listener);
    }

    public void removeListener(JNetworkListenerX listener) {
        this.listeners.remove(listener);
    }

    public void notifyListeners(JPackectX packet) {
        for (JNetworkListenerX e : listeners) {
            e.onPacket(packet);
        }
    }

    public void sendPacket(JPackectX packet) {
        for(JHostConnectionX e: connections) {
            e.queuePacket(packet);
        }
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Listening on port: " + serverSocket.getLocalPort() + ".");
            this.listening = true;
            while (listening) {
                new JHostConnectionX(this, serverSocket.accept()).start();
            }
        }
        catch (IOException e) {
            System.err.println("Could not listen on port " + this.port + ".");
            this.listening = false;
        }
    }
}
