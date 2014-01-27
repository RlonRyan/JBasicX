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
public class JHostX extends Thread implements JNetworkListenerX {

    private final int port;
    private final List<JNetworkListenerX> listeners;
    private final List<JConnectionX> connections;

    private int externalport;
    private boolean listening;

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
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Listening on port: " + serverSocket.getLocalPort() + ".");
            this.listening = true;
            while (listening) {
                this.connections.add(new JConnectionX(serverSocket.accept().getInetAddress(), externalport));
                this.connections.get(this.connections.size()).addListener(this);
                this.connections.get(this.connections.size()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + this.port + ".");
            this.listening = false;
        }
    }

    @Override
    public void onPacket(JPackectX packet) {
        // Yay! Who cares? Not this class!
    }

    @Override
    public void onError() {
        // Nothing for now...
    }

    @Override
    public void onTimeout() {
        // Nothing for now...
    }

    @Override
    public void onTerminate() {
        // Nothing for now...
    }

}
