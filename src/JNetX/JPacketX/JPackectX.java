/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX.JPacketX;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class JPackectX {

    private final JPacketTypeX type;
    private long timestamp;
    
    private byte[] data;
    private short size;

    private HashMap<String, Object> packet;
    
    public JPacketTypeX getType() {
        return this.type;
    }
    
    public int getId() {
        return this.id;
    }

    public byte[] getData() {
        return data;
    }
    
    public int getSize() {
        if(this.size != 0) {
            return size;
        }
        else {
            return this.data.length;
        }
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }

    public void timestamp() {
        if (this.timestamp == 0) {
            this.timestamp = System.currentTimeMillis();
        }
    }
    
    public DatagramPacket encode(InetAddress ip, int port){
        this.timestamp();
        return new DatagramPacket(this.data, data.length, ip, port);
    }
        
    @Override
    public String toString() {
        if(this.type != null && this.data != null) {
            return this.type.toString() + ": " + Arrays.toString(data);
        }
        else {
            return "Invalid packet.";
        }
    }

    public JPackectX() {
        this.type = null;
        this.timestamp = 0;
    }

    public JPackectX(JPacketTypeX type, byte... data) {
        this.type = type;
        this.data = data;
        this.packet = new HashMap<>();
    }

    public JPackectX(byte[] data) {
        
        JPacketTypeX packet_type = JPacketTypeX.INVALID;
        
        try {
            packet_type = JPacketTypeX.getForID(data[0]);
            this.data = Arrays.copyOfRange(data, 1, data.length);
        }
        catch(Exception e) {
            System.err.println("Malformed Packet Recieved!");
        }
        finally {
            this.type = packet_type;
        }
    }


}
