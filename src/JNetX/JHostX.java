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
    
    private JConnectionStateX state;

    public JHostX(int port) throws IOException {
	this.group = new HashMap<>();
	this.bindings = new JBinderX<>();
	this.port = port;
	this.socket = new DatagramSocket(port);
	this.state = JConnectionStateX.ACTIVE;
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
        }, 0, 1000);
	
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
	    DatagramPacket p = new DatagramPacket(new byte[512], 256);
	    JPackectX packet;
	    while (this.state == JConnectionStateX.ACTIVE) {
		this.socket.receive(p);
		packet = new JPackectX(p);
		this.bindings.trigger(packet.getType(), packet);
	    }
	}
	catch (IOException e) {
	    Logger.getLogger("Network").log(Level.SEVERE, "Network Failure.");
	    this.state = JConnectionStateX.IO_EXCEPTION;
	    this.close();
	}
    }
    
    public void close() {
	this.heart.cancel();
	this.socket.close();
    }

}
