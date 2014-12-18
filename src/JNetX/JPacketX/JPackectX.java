/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX.JPacketX;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
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

    /*
     * Data type lengths
     */
    public static final int BYTE_LENGTH = 1;
    public static final int SHORT_LENGTH = 2;
    public static final int INT_LENGTH = 4;
    public static final int LONG_LENGTH = 8;

    private HashMap<JPacketFieldX, byte[]> data;
    private byte type;
    private byte id;

    private InetAddress address;
    private int port;

    /**
     *
     * @return
     */
    public int getSize() {
	int size = 10;
	for (byte[] e : this.data.values()) {
	    size += e.length + 1;
	}
	if (this.hasField(JPacketFieldX.MESSAGE)) {
	    size += 4;
	}
	if (this.hasField(JPacketFieldX.CUSTOM)) {
	    size += 4;
	}
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
     *              <p>
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
    }

    public boolean hasField(JPacketFieldX field) {
	return this.data.containsKey(field);
    }

    /**
     *
     * @param address
     * @param port
     * @param ack
     * @param acks
     * @param id
     *                <p>
     * @return
     */
    public DatagramPacket convert(InetAddress address, int port, byte ack, BitSet acks, Byte id) {
	byte[] size = toBytes(this.getSize());
	byte[] bytes = new byte[this.getSize()];
	bytes[0] = 112;
	bytes[1] = 35;
	bytes[2] = ack;
	bytes[3] = 0; //acks.toByteArray()[0];
	bytes[4] = id;
	bytes[5] = size[0];
	bytes[6] = size[1];
	bytes[7] = size[2];
	bytes[8] = size[3];
	bytes[9] = this.type;
	byte c = 10;
	for (JPacketFieldX field : data.keySet()) {
	    bytes[c] = field.id;
	    c++;
	    if (field == JPacketFieldX.MESSAGE || field == JPacketFieldX.CUSTOM) {
		for (byte b : toBytes(data.get(field).length)) {
		    bytes[c] = b;
		    c++;
		}
	    }
	    for (byte b : data.get(field)) {
		if (c >= 254) {
		    break;
		}
		bytes[c] = b;
		c++;
	    }
	}
	//System.out.println(Arrays.toString(bytes));
	return new DatagramPacket(bytes, c, address, port);
    }

    /**
     *
     * @param type
     */
    public JPackectX(JPacketTypeX type) {
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
	int size = getIntFromBytes(Arrays.copyOfRange(packet.getData(), 5, 9));
	if (size > packet.getData().length) {
	    Logger.getLogger("Network").log(Level.WARNING, "Partial packet recieved!");
	}
	this.type = packet.getData()[9];
	Logger.getLogger("Network").log(Level.FINEST, "Packet recieved. Type: ", this.type);
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
		for (int i = 10; i < packet.getData().length;) {
		    int sizef;
		    JPacketFieldX field = JPacketFieldX.getForID(packet.getData()[i]);
		    i = i + 1;
		    if (field == JPacketFieldX.MESSAGE || field == JPacketFieldX.CUSTOM) {
			sizef = JPackectX.getIntFromBytes(packet.getData(), i, i + 4);
			i = i + 4;
			this.data.put(field, Arrays.copyOfRange(packet.getData(), i, i + sizef));
			i = i + sizef;
			Logger.getLogger("Network").log(Level.INFO, "Packet Message: {0}", getStringFromBytes(this.data.get(field)));
		    }
		    else {
			sizef = field.size;
			this.data.put(field, Arrays.copyOfRange(packet.getData(), i, i + sizef));
			i = i + sizef;
		    }
		}
		break;
	    case HEARTBEAT:
		this.data.put(JPacketFieldX.MESSAGE, Arrays.copyOfRange(packet.getData(), 7, packet.getData().length));
		//Logger.getLogger("Network").log(Level.INFO, new String(this.data.get(JPacketFieldX.MESSAGE)));
		break;
	    case INVALID:
	    default:
		break;
	}
    }

    public static final byte[] toBytes(byte b) {
	ByteBuffer buff = ByteBuffer.allocate(BYTE_LENGTH);
	buff.put(b);
	return buff.array();
    }

    public static final byte[] toBytes(short s) {
	ByteBuffer buff = ByteBuffer.allocate(SHORT_LENGTH);
	buff.putShort(s);
	return buff.array();
    }

    public static final byte[] toBytes(int i) {
	ByteBuffer buff = ByteBuffer.allocate(INT_LENGTH);
	buff.putInt(i);
	return buff.array();
    }

    public static final byte[] toBytes(long l) {
	ByteBuffer buff = ByteBuffer.allocate(LONG_LENGTH);
	buff.putLong(l);
	return buff.array();
    }

    public static final byte[] toBytes(String string) {
	int slength = string.getBytes().length;
	byte[] bytes = new byte[4 + slength];
	System.arraycopy(toBytes(slength), 0, bytes, 0, 4);
	System.arraycopy(string.getBytes(), 0, bytes, 0, slength);
	return bytes;
    }

    public static final byte getByteFromBytes(byte[] bytes, int from, int to) {
	return getByteFromBytes(Arrays.copyOfRange(bytes, from, to));
    }

    public static final byte getByteFromBytes(byte[] bytes) {
	return ByteBuffer.wrap(bytes).get();
    }

    public static final short getShortFromBytes(byte[] bytes, int from, int to) {
	return getShortFromBytes(Arrays.copyOfRange(bytes, from, to));
    }

    public static final short getShortFromBytes(byte[] bytes) {
	return ByteBuffer.wrap(bytes).getShort();
    }

    public static final int getIntFromBytes(byte[] bytes, int from, int to) {
	return getIntFromBytes(Arrays.copyOfRange(bytes, from, to));
    }

    public static final int getIntFromBytes(byte[] bytes) {
	return ByteBuffer.wrap(bytes).getInt();
    }

    public static final long getLongFromBytes(byte[] bytes, int from, int to) {
	return getLongFromBytes(Arrays.copyOfRange(bytes, from, to));
    }

    public static final long getLongFromBytes(byte[] bytes) {
	return ByteBuffer.wrap(bytes).getLong();
    }

    public static final String getStringFromBytes(byte[] bytes, int from, int to) {
	return getStringFromBytes(Arrays.copyOfRange(bytes, from, to));
    }
    
    public static final String getStringFromBytes(byte[] bytes) {
	return new String(Arrays.copyOfRange(bytes, 0, bytes.length));
    }

    public static final String packetToString(DatagramPacket p) {
	StringBuilder sb = new StringBuilder();
	sb.append("JPacketX");
	sb.append("\n====================");
	sb.append("\nProtocol: ");
	sb.append(p.getData()[0]);
	sb.append(p.getData()[1]);
	sb.append("\nAck index: ");
	sb.append(p.getData()[2]);
	sb.append("\nAcks: ");
	sb.append(p.getData()[3]);
	sb.append("\nID: ");
	sb.append(p.getData()[4]);
	sb.append("\nSize: ");
	sb.append(getIntFromBytes(p.getData(), 5, 9));
	sb.append("\nType: ");
	sb.append(JPacketTypeX.getForID(p.getData()[9]));
	for (int i = 10; i < p.getData().length;) {
	    int sizef = JPacketFieldX.getForID(p.getData()[i]).size;
	    sb.append("\nField: { Type:\"");
	    sb.append(JPacketFieldX.getForID(p.getData()[i]).toString());
	    sb.append("\" Data: ");
	    sb.append(Arrays.toString(Arrays.copyOfRange(p.getData(), i + 1, i + 2 + sizef)));
	    sb.append("}");
	    i = i + 1 + sizef;
	}
	return sb.toString();
    }
}
