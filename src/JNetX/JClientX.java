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
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ryan
 */
public class JClientX extends Thread {

    private DatagramSocket socket;
    
    private final List<JNetworkListenerX> listeners;
    private final HashMap<Integer, JPackectX> sent;
    
    private JConnectionStateX state;
    private byte id;
    private BitSet acks;
    private byte ack;

    /**
     *
     */
    public final InetAddress address;

    /**
     *
     */
    public final int port;

    /**
     *
     * @param address
     * @param port
     */
    public JClientX(InetAddress address, int port) {
	this(address, port, 10000);
    }

    /**
     *
     * @param address
     * @param port
     * @param timeout
     */
    public JClientX(InetAddress address, int port, int timeout) {
	this.address = address;
	this.port = port;
	this.listeners = new ArrayList<>();
	this.sent = new HashMap<>();

	try {
	    this.state = JConnectionStateX.INVALID;
	    this.socket = new DatagramSocket(port, address);
	    this.socket.connect(address, port);
	    System.out.println(socket.isConnected());
	    this.socket.setSoTimeout(timeout);
	    this.acks = new BitSet(256);
	    this.ack = 0;
	    this.state = JConnectionStateX.ACTIVE;
	}
	catch (SocketException e) {
	    e.printStackTrace();
	    System.err.println("Well that's great...");
	}

    }
    
    public void incID() {
	this.id = (byte) ((this.id + 1) < Byte.MAX_VALUE ? (this.id + 1) : (0));
    }

    /**
     *
     * @param packet
     * @return
     */
    public boolean sendPacket(JPackectX packet) {
	try {
	    Logger.getLogger("Network").log(Level.INFO, "Send packet: {0}", packet.getSize());
	    this.socket.send(packet.convert(this.address, this.port, ack, acks, id));
	    this.incID();
	}
	catch (IOException e) {
	    return false;
	}
	return true;
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
	    DatagramPacket packet = new DatagramPacket(new byte[512], 256);
	    this.socket.receive(packet);
	    this.acks.set(packet.getData()[4], true);
	    System.out.println(this.ack + ": " + this.acks.toString());
	}
	catch (SocketTimeoutException e) {
	    System.err.println("Connection with " + this.socket.getInetAddress() + ":" + this.socket.getPort() + " timed out.");
	    this.state = JConnectionStateX.TIMEDOUT;
	    this.notifyListeners(JNetEventTypeX.TIMEOUT);
	    this.close();
	}
	catch (IOException e) {
	    e.printStackTrace();
	    Logger.getLogger("Network").log(Level.WARNING, "IOException: {0}", e.getLocalizedMessage());
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
