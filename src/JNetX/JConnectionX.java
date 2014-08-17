/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX;

import JNetX.JNetEventX.JNetEventTypeX;
import JNetX.JPacketX.JPackectX;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 *
 * @author Ryan
 */
public class JConnectionX extends Thread {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private JConnectionStateX state;
    private List<JNetworkListenerX> listeners;

    /**
     *
     * @param address
     * @param port
     */
    public JConnectionX(InetAddress address, int port) {
	this(address, port, 10000);
    }

    /**
     *
     * @param address
     * @param port
     * @param timeout
     */
    public JConnectionX(InetAddress address, int port, int timeout) {
	try {
	    this.state = JConnectionStateX.INVALID;
	    this.socket = new DatagramSocket(port, address);
	    this.socket.setSoTimeout(timeout);
	    this.state = JConnectionStateX.ACTIVE;
	}
	catch (SocketException e) {
	    System.err.println("Well that's great...");
	}
    }

    /**
     *
     * @return
     */
    public JConnectionStateX getConnectionState() {
	return this.state;
    }

    /**
     *
     */
    @Override
    public void run() {
	try {
	    this.socket.receive(packet);
	}
	catch (SocketTimeoutException e) {
	    System.err.println("Connection with " + this.socket.getInetAddress() + ":" + this.socket.getPort() + " timed out.");
	    this.state = JConnectionStateX.TIMEDOUT;
	    this.notifyListeners(JNetEventTypeX.TIMEOUT);
	    this.close();
	}
	catch (IOException e) {
	    System.err.println("Well that's great...");
	    this.state = JConnectionStateX.INVALID;
	    this.notifyListeners(JNetEventTypeX.ERROR);
	    this.close();
	}
    }

    /**
     *
     */
    public void close() {
	this.state = JConnectionStateX.TERMINATED;
	this.socket.close();
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
     * @param type
     * @param data
     */
    public void notifyListeners(JNetEventTypeX type, Object... data) {
	switch (type) {
	    case PACKET_RECIEVED:
		if (data[0] instanceof JPackectX) {
		    for (JNetworkListenerX e : listeners) {
			e.onPacket((JPackectX) data[0]);
		    }
		}
		break;

	    case TIMEOUT:
		for (JNetworkListenerX e : listeners) {
		    e.onTimeout();
		}
		break;

	    case ERROR:
		for (JNetworkListenerX e : listeners) {
		    e.onError();
		}
		break;

	    case TERMINATE:
		for (JNetworkListenerX e : listeners) {
		    e.onTerminate();
		}
		break;

	    default:
		break; // UMMMM?!?!?
	}

    }
}
