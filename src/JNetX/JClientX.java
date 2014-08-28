/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX;

import JBasicX.JBinderX;
import JNetX.JPacketX.JPackectX;
import JNetX.JPacketX.JPacketTypeX;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.BitSet;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ryan
 */
public class JClientX extends Thread {

    public final JBinderX<JPacketTypeX, JPackectX> bindings;

    private final HashMap<Integer, JPackectX> sent;

    private DatagramSocket socket;
    private JConnectionStateX state;
    private byte id;
    private BitSet acks;
    private byte ack;

    public final InetAddress address;
    public final int port;

    /**
     *
     * @param address
     * @param port
     * <p>
     * @throws java.io.IOException
     */
    public JClientX(String address, int port) throws IOException {
	this(address, port, 10000);
    }

    /**
     *
     * @param address
     * @param port
     * @param timeout
     * <p>
     * @throws java.io.IOException
     */
    public JClientX(String address, int port, int timeout) throws IOException {

	try {
	    URL checkip = new URL("http://checkip.amazonaws.com/");
	    BufferedReader in = new BufferedReader(new InputStreamReader(checkip.openStream()));
	    String desired = InetAddress.getByName("rlonryan.selfip.com").getHostAddress();
	    String external = in.readLine();
	    Logger.getLogger("Network").log(Level.INFO, "Desired: {0}\nThis Computer: {1}", new Object[]{desired, external});
	    this.address = (desired.equalsIgnoreCase(external) ? InetAddress.getLoopbackAddress() : InetAddress.getByName(desired));
	    Logger.getLogger("Network").log(Level.INFO, "Using: {0}", this.address.getHostAddress());
	}
	catch (IOException e) {
	    Logger.getLogger("Network").log(Level.SEVERE, "Unable to resolve host address!");
	    throw e;
	}

	this.port = port;
	this.bindings = new JBinderX<>();
	this.sent = new HashMap<>();

	try {
	    this.state = JConnectionStateX.INVALID;
	    this.socket = new DatagramSocket();
	    this.socket.connect(this.address, this.port);
	    Logger.getLogger("Network").log(Level.INFO, "Created Socket.\nConnection Status: {0}", this.socket.isConnected() ? "Connected" : "Disconnected");
	    this.socket.setSoTimeout(timeout);
	    this.acks = new BitSet(256);
	    this.ack = 0;
	    this.state = JConnectionStateX.ACTIVE;
	}
	catch (SocketException e) {
	    Logger.getLogger("Network").log(Level.SEVERE, "Unable to create socket!");
	    throw e;
	}

    }

    public void incID() {
	this.id = (byte) ((this.id + 1) < Byte.MAX_VALUE ? (this.id + 1) : (0));
    }

    /**
     *
     * @param packet
     * <p>
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
	    DatagramPacket packet;
	    JPackectX p;
	    this.sendPacket(new JPackectX(JPacketTypeX.LOGON));
	    while (this.state == JConnectionStateX.ACTIVE) {
		packet = new DatagramPacket(new byte[512], 256);
		this.socket.receive(packet);
		p = new JPackectX(packet);
		this.acks.set(packet.getData()[4], true);
		this.bindings.trigger(p.getType(), p);
	    }
	    this.sendPacket(new JPackectX(JPacketTypeX.LOGOFF));
	}
	catch (SocketTimeoutException e) {
	    System.err.println("Connection with " + this.socket.getInetAddress() + ":" + this.socket.getPort() + " timed out.");
	    this.state = JConnectionStateX.TIMEDOUT;
	    this.bindings.trigger(JPacketTypeX.INVALID, null);
	    this.close();
	}
	catch (IOException e) {
	    e.printStackTrace();
	    Logger.getLogger("Network").log(Level.WARNING, "IOException: {0}", e.getLocalizedMessage());
	    this.state = JConnectionStateX.INVALID;
	    this.bindings.trigger(JPacketTypeX.INVALID, null);
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

}
