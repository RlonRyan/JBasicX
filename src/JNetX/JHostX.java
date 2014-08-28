/*
 * Blah
 */
package JNetX;

import JBasicX.JBinderX;
import JNetX.JPacketX.JPackectX;
import JNetX.JPacketX.JPacketFieldX;
import JNetX.JPacketX.JPacketTypeX;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RlonRyan
 */
public class JHostX extends Thread {

    private final int port;
    private final Timer heart;
    private final DatagramSocket socket;
    private final HashMap<InetAddress, JConnectionX> group;

    public final JBinderX<JPacketTypeX, JPackectX> bindings;

    private JConnectionStateX connectionState;

    public JHostX(int port) throws IOException {
	this.group = new HashMap<>();
	this.bindings = new JBinderX<>();
	this.port = port;
	this.socket = new DatagramSocket(this.port);
	this.connectionState = JConnectionStateX.ACTIVE;
	this.heart = new Timer();

	this.bindings.bind(JPacketTypeX.LOGON, (packet) -> {
	    Logger.getLogger("Network").log(Level.INFO, "Join: {0}", packet.getAddress());
	    this.group.put(packet.getAddress(), new JConnectionX(packet.getAddress(), packet.getPort()));
	});
	this.bindings.bind(JPacketTypeX.HEARTBEAT, (packet) -> {
	    this.group.get(packet.getAddress()).onHeartbeat();
	});
	this.bindings.bind(JPacketTypeX.LOGOFF, (packet) -> {
	    Logger.getLogger("Network").log(Level.INFO, "Part: {0}", packet.getAddress());
	    this.group.remove(packet.getAddress());
	});

	this.heart.scheduleAtFixedRate(new TimerTask() {
	    @Override
	    public void run() {
		JPackectX heartbeat = new JPackectX(JPacketTypeX.HEARTBEAT);
		heartbeat.set(JPacketFieldX.MESSAGE, ("Time: " + System.currentTimeMillis()).getBytes());
		broadcast(heartbeat);
	    }
	}, 500, 1000);
    }

    public HashMap<InetAddress, JConnectionX> getClients() {
	return group;
    }

    public JConnectionStateX getConnectionState() {
	return connectionState;
    }

    public boolean broadcast(JPackectX packet) {
	try {
	    for (JConnectionX connection : group.values()) {
		this.socket.send(packet.convert(connection.address, connection.port, (byte) 0, null, (byte) 0));
	    }
	    return true;
	}
	catch (IOException e) {
	    Logger.getLogger("Network").log(Level.WARNING, "Network send Failure");
	}
	return false;
    }

    @Override
    public void run() {
	try {
	    while (this.connectionState == JConnectionStateX.ACTIVE) {
		DatagramPacket p = new DatagramPacket(new byte[512], 256);
		this.socket.receive(p);
		JPackectX packet = new JPackectX(p);
		this.bindings.trigger(packet.getType(), packet);
		Logger.getLogger("Network").log(Level.INFO, packet.getType().toString());
		//Logger.getLogger("Network").log(Level.INFO, Arrays.toString(p.getData()));
	    }
	}
	catch (IOException e) {
	    Logger.getLogger("Network").log(Level.SEVERE, "Network Failure.");
	    this.connectionState = JConnectionStateX.IO_EXCEPTION;
	    this.close();
	}
    }

    public void close() {
	this.heart.cancel();
	this.socket.close();
    }

}
