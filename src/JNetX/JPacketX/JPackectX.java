/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX.JPacketX;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.BitSet;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class JPackectX {

    private HashMap<String, byte[]> data;
    /*
     *   Does not include header size...
     */
    private byte size;
    private byte id;
    private byte type;

    public byte getSize() {
        return size;
    }

    public byte getId() {
        return id;
    }

    public JPacketTypeX getType() {
        return JPacketTypeX.getForID(type);
    }

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

    public JPackectX(JPacketTypeX type) {
        this.size = 0;
        this.type = type.id;
        
    }

}
