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
    
    private final List<JConnectionX> connections;
    private final List<JNetworkListenerX> listeners;
    
    private int externalport;
    private boolean isActive;
    
    private ServerSocket socket;

    /**
     *
     * @param port
     */
    public JHostX(int port) {
	this.listeners = new ArrayList<>();
	this.connections = new ArrayList<>();
	this.port = port;
	this.isActive = false;
    }

    /**
     *
     * @return
     */
    public boolean isListening() {
	return isActive;
    }

    /**
     *
     * @param listener
     */
    public void addListener(JNetworkListenerX listener) {
	this.listeners.add(listener);
    }

    /**
     *
     * @param listener
     */
    public void removeListener(JNetworkListenerX listener) {
	this.listeners.remove(listener);
    }

    /**
     *
     * @param packet
     */
    public void notifyListeners(JPackectX packet) {
	for (JNetworkListenerX e : listeners) {
	    e.onPacket(packet);
	}
    }

    /**
     *
     * @param packet
     */
    public void broadcastPacket(JPackectX packet) {
	if(this.isActive) {
	}
    }

    /**
     *
     */
    @Override
    public void run() {
	try {
	    this.socket = new ServerSocket(this.port);
	    
	    System.out.println("Listening on port: " + this.socket.getLocalPort() + ".");
	    this.isActive = true;
	    while (isActive) {
		this.connections.add(new JConnectionX(this, this.socket.accept().getInetAddress(), this.port));
		this.connections.get(this.connections.size()).start();
	    }
	}
	catch (IOException e) {
	    System.err.println("Could not listen on port " + this.port + ".");
	    this.isActive = false;
	}
    }

    /**
     *
     * @param packet
     */
    @Override
    public void onPacket(JPackectX packet) {
	// Yay! Who cares? Not this class!
	System.out.println();
    }

    /**
     *
     */
    @Override
    public void onError() {
	// Nothing for now...
    }

    /**
     *
     */
    @Override
    public void onTimeout() {
	// Nothing for now...
    }

    /**
     *
     */
    @Override
    public void onTerminate() {
	// Nothing for now...
    }
}
