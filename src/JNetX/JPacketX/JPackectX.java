/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX.JPacketX;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ryan
 */
public class JPackectX {

    private HashMap<JPacketFieldX, byte[]> data;
    /*
     * Does not include header size...
     */
    private byte size;
    private byte type;
    private byte id;
    
    private InetAddress address;
    private int port;

    /**
     *
     * @return
     */
    public byte getSize() {
	return size;
    }

    /**
     *
     * @return
     */
    public byte getId() {
	return id;
    }

    public InetAddress getAddress() {
	return address;
    }

    public int getPort() {
	return port;
    }

    /**
     *
     * @return
     */
    public JPacketTypeX getType() {
	return JPacketTypeX.getForID(type);
    }
    
    /**
     *
     * @param field
     * @return 
     */
    public byte[] get(JPacketFieldX field) {
	return this.data.get(field);
    }

    /**
     *
     * @param field
     * @param data
     */
    public void set(JPacketFieldX field, byte[] data) {
	this.data.put(field, data);
	this.size += data.length + 1;
    }

    /**
     *
     * @param address
     * @param port
     * @param ack
     * @param acks
     * @param id
     * <p>
     * @return
     */
    public DatagramPacket convert(InetAddress address, int port, byte ack, BitSet acks, Byte id) {
	byte[] bytes = new byte[7 + size];
	bytes[0] = 112;
	bytes[1] = 35;
	bytes[2] = ack;
	bytes[3] = 0; //acks.toByteArray()[0];
	bytes[4] = id;
	bytes[5] = this.size;
	bytes[6] = this.type;
	byte c = 7;
	for (JPacketFieldX field : data.keySet()) {
	    bytes[c] = field.id;
	    for (byte b : data.get(field)) {
		if (c >= 254) {
		    break;
		}
		bytes[c] = b;
		c++;
	    }
	}
	return new DatagramPacket(bytes, c, address, port);
    }

    /**
     *
     * @param type
     */
    public JPackectX(JPacketTypeX type) {
	this.size = 0;
	this.type = type.id;
	this.data = new HashMap<>();
    }

    /**
     *
     * @param packet
     */
    public JPackectX(DatagramPacket packet) {
	this.address = packet.getAddress();
	this.port = packet.getPort();
	this.id = packet.getData()[4];
	this.size = packet.getData()[5];
	this.type = packet.getData()[6];
	this.data = new HashMap<>();
	
	switch (JPacketTypeX.getForID(type)) {
	    case LOGOFF:
		break;
	    case LOGON:
		break;
	    case MESSAGE:
		this.data.put(JPacketFieldX.MESSAGE, Arrays.copyOfRange(packet.getData(), 7, packet.getData().length));
		Logger.getLogger("Network").log(Level.INFO, new String(this.data.get(JPacketFieldX.MESSAGE)));
		break;
	    case TERMINATE:
		break;
	    case UPDATE:
		for (int i = 7; i < packet.getData().length;) {
		    byte size = JPacketFieldX.getForID(packet.getData()[i]).size;
		    this.data.put(JPacketFieldX.getForID(packet.getData()[i]), Arrays.copyOfRange(packet.getData(), i + 1, i + size + 1));
		}
		break;
	    case HEARTBEAT:
		this.data.put(JPacketFieldX.MESSAGE, Arrays.copyOfRange(packet.getData(), 7, packet.getData().length));
		Logger.getLogger("Network").log(Level.INFO, new String(this.data.get(JPacketFieldX.MESSAGE)));
		break;
	    case INVALID:
	    default:
		break;
	}
    }
}
