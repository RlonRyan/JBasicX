/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX.JPacketX;

import java.net.DatagramPacket;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

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
     * @param field
     * @param data
     */
    public void set(JPacketFieldX field, byte[] data) {
	this.data.put(field, data);
	this.size += data.length + 1;
    }

    /**
     *
     * @param ack
     * @param acks
     * @param id
     * <p>
     * @return
     */
    public DatagramPacket convert(byte ack, BitSet acks, byte id) {
	byte[] bytes = new byte[7 + size];
	bytes[0] = 112;
	bytes[1] = 35;
	bytes[2] = ack;
	bytes[3] = 0; //acks.toByteArray()[0];
	bytes[4] = id;
	bytes[5] = size;
	bytes[6] = type;
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
	return new DatagramPacket(bytes, c + 1);
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
     * @param data
     */
    public JPackectX(byte[] data) {
	this.id = data[4];
	this.size = data[5];
	this.type = data[6];
	this.data = new HashMap<>();
	
	switch (JPacketTypeX.getForID(type)) {
	    case LOGOFF:
		break;
	    case LOGON:
		break;
	    case MESSAGE:
		this.data.put(JPacketFieldX.MESSAGE, Arrays.copyOfRange(data, 7, data.length));
		System.out.println(new String(this.data.get(JPacketFieldX.MESSAGE)));
		break;
	    case TERMINATE:
		break;
	    case UPDATE:
		for (int i = 7; i < data.length;) {
		    byte size = JPacketFieldX.getForID(data[i]).size;
		    this.data.put(JPacketFieldX.getForID(data[i]), Arrays.copyOfRange(data, i + 1, i + size + 1));
		}
		break;
	    case INVALID:
	    default:
		break;
	}
    }
}
