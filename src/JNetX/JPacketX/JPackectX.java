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

/**
 *
 * @author Ryan
 */
public class JPackectX {

    private HashMap<String, byte[]> data;
    /*
     * Does not include header size...
     */
    private byte size;
    private byte id;
    private byte type;

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

    /**
     *
     * @return
     */
    public JPacketTypeX getType() {
	return JPacketTypeX.getForID(type);
    }

    /**
     *
     * @param key
     * @param data
     */
    public void set(String key, byte... data) {
	size += data.length;
    }

    /**
     *
     * @param ip
     * @param port
     * @param ack
     * @param acks
     * @param id
     * @return
     */
    public DatagramPacket send(InetAddress ip, int port, byte ack, BitSet acks, byte id) {
	byte[] bytes = new byte[7 + size];
	bytes[0] = 112;
	bytes[1] = 35;
	bytes[2] = ack;
	bytes[3] = acks.toByteArray()[0];
	bytes[4] = id;
	bytes[5] = size;
	bytes[6] = type;
	byte c = 7;
	for (byte[] e : data.values()) {
	    for (byte b : e) {
		if (c >= 256) {
		    break;
		}
		bytes[c] = b;
	    }
	}
	return new DatagramPacket(bytes, size + 2, ip, port);
    }

    /**
     *
     * @param type
     */
    public JPackectX(JPacketTypeX type) {
	this.size = 0;
	this.type = type.id;
    }

    /**
     *
     * @param data
     */
    public JPackectX(byte[] data) {
	this.id = data[4];
	this.size = data[5];
	this.type = data[6];
	switch (JPacketTypeX.getForID(type)) {
	    case LOGOFF:
		break;
	    case LOGON:
		break;
	    case MESSAGE:
		this.data.put("message", Arrays.copyOfRange(data, 7, data.length));
		System.out.println(new String(this.data.get("message")));
		break;
	    case TERMINATE:
		break;
	    case UPDATE:
		break;
	    case INVALID:
	    default:
		break;
	}
    }
}
