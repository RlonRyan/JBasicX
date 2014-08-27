/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX.JPacketX;

/**
 *
 * @author RlonRyan
 */
public enum JPacketTypeX {

    /**
     *
     */
    INVALID(0),

    /**
     *
     */
    LOGON(1),
    
    /**
     *
     */
    HEARTBEAT(2),

    /**
     *
     */
    UPDATE(3),

    /**
     *
     */
    MESSAGE(4),

    /**
     *
     */
    LOGOFF(5),

    /**
     *
     */
    TERMINATE(6);

    /**
     *
     */
    public byte id;

    private JPacketTypeX(int id) {
	this.id = (id < Byte.MAX_VALUE ? (byte) id : 0);
    }

    /**
     *
     * @param id
     * @return
     */
    public static JPacketTypeX getForID(byte id) {
	for (JPacketTypeX e : values()) {
	    if (e.id == id) {
		return e;
	    }
	}
	return INVALID;
    }
}
