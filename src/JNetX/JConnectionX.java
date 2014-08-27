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

/**
 *
 * @author Ryan
 */
public class JConnectionX extends Thread {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private JConnectionStateX state;
    private JHostX host;

    /**
     * @param host
     * @param address
     * @param port
     */
    public JConnectionX(JHostX host, InetAddress address, int port) {
	this(host, address, port, 10000);
    }

    /**
     * @param host
     * @param address
     * @param port
     * @param timeout
     */
    public JConnectionX(JHostX host, InetAddress address, int port, int timeout) {
	this.host = host;
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
    
    public InetAddress getAddress() {
	return this.socket.getInetAddress();
    }

    /**
     *
     */
    @Override
    public void run() {
	try {
	    this.socket.receive(packet);
	    this.socket.send(new DatagramPacket(new byte[]{packet.getData()[0]}, MIN_PRIORITY));
	}
	catch (SocketTimeoutException e) {
	    System.err.println("Connection with " + this.socket.getInetAddress() + ":" + this.socket.getPort() + " timed out.");
	    this.state = JConnectionStateX.TIMEDOUT;
	    this.notifyHost(JNetEventTypeX.TIMEOUT);
	    this.close();
	}
	catch (IOException e) {
	    System.err.println("Well that's great...");
	    this.state = JConnectionStateX.INVALID;
	    this.notifyHost(JNetEventTypeX.ERROR);
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
     * @param type
     * @param data
     */
    public void notifyHost(JNetEventTypeX type, Object... data) {
	switch (type) {
	    case PACKET_RECIEVED:
		if (data[0] instanceof JPackectX) {
			host.onPacket((JPackectX) data[0]);
		}
		break;

	    case TIMEOUT:
		    host.onTimeout();
		break;

	    case ERROR:
		    host.onError();
		break;

	    case TERMINATE:
		    host.onTerminate();
		break;

	    default:
		break; // UMMMM?!?!?
	}

    }
}
