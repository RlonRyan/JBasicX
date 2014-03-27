/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX.JPacketX;

/**
 *
 * @author 8000214
 */
public enum JPacketTypeX {

    INVALID(0), LOGON(1), UPDATE(2), MESSAGE(3), LOGOFF(4), TERMINATE(5);
    public byte id;

    private JPacketTypeX(int id) {
	this.id = (id < Byte.MAX_VALUE ? (byte) id : 0);
    }

    public static JPacketTypeX getForID(byte id) {
	for (JPacketTypeX e : values()) {
	    if (e.id == id) {
		return e;
	    }
	}
	return INVALID;
    }
}
