/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX.JPacketX;

import java.net.DatagramPacket;
import java.util.Arrays;

/**
 *
 * @author Ryan
 */
public class JPackectX {

    public static enum PACKET_TYPE {

        
        INVALID(0),LOGON(1),UPDATE(2),MESSAGE(3),LOGOFF(4),TERMINATE(5);
        public byte id;
        
        private PACKET_TYPE(int id) {
            this.id = (id < Byte.MAX_VALUE ? (byte)id : 0);
        }
        
        public static PACKET_TYPE getForID(byte id) {
            for(PACKET_TYPE e : values()) {
                if(e.id == id) {
                    return e;
                }
            }
            return INVALID;
        }
    }

    final private PACKET_TYPE type;
    private byte[] data;

    public PACKET_TYPE getType() {
        return this.type;
    }

    public byte[] getData() {
        return data;
    }

    public DatagramPacket encode(){
        return new DatagramPacket(this.data, data.length);
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

    public JPackectX(PACKET_TYPE type, byte... data) {
        this.type = type;
        this.data = data;
    }

    public JPackectX(byte[] data) {
        
        PACKET_TYPE packet_type = PACKET_TYPE.INVALID;
        
        try {
            packet_type = PACKET_TYPE.getForID(data[0]);
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
