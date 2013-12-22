/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX.JPacketX;

import java.util.Arrays;

/**
 *
 * @author Ryan
 */
public class JPackectX {

    public enum PACKET_TYPE {
        INVALID(0),LOGON(1),UPDATE(2),MESSAGE(3),LOGOFF(4),TERMINATE(99);
        public int id;
        private PACKET_TYPE(int id) {
            this.id = id;
        }
        public static PACKET_TYPE getForID(int id) {
            for(PACKET_TYPE e : values()) {
                if(e.id == id) {
                    return e;
                }
            }
            return INVALID;
        }
    }

    private PACKET_TYPE type;
    private Object[] data;

    public PACKET_TYPE getType() {
        return this.type;
    }

    public Object[] getData() {
        return data;
    }

    public String encode(){
        StringBuilder encoder = new StringBuilder();
        encoder.append(type.id);
        encoder.append(' ');
        for (Object e : data) {
            encoder.append(e);
            encoder.append(' ');
        }
        return encoder.toString();
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

    public JPackectX(PACKET_TYPE type, Object... data) {
        this.type = type;
        this.data = data;
    }

    public JPackectX(String raw) {
        try{
            this.type = PACKET_TYPE.getForID(Integer.decode((String)(this.data = raw.split(" "))[0]));
        }
        catch (NumberFormatException e) {
            this.type = PACKET_TYPE.INVALID;
            this.data = raw.split(" ");
        }
    }


}
