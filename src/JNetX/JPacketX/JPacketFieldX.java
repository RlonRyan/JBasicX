/*
 * JBasicX
 * By RlonRyan
 * 
 * All rights reserved. No warrenty on use. User accepts all risks and damages.
 * Class: JPacketField
 * Description: JPacketFieldX
 */

package JNetX.JPacketX;

/**
 *
 * @author Ryan
 */
public enum JPacketFieldX {

    /**
     * 
     */
    INVALID(0, 0),
    /**
     *
     */
    MESSAGE(1, -1),

    /**
     *
     */
    KEY(2, 1),

    /**
     *
     */
    MOUSE(3, 1);

    /**
     *
     */
    public byte id;
    public byte size;

    private JPacketFieldX(int id, int size) {
	this.id = (id < Byte.MAX_VALUE ? (byte) id : 0);
	this.size = (size < Byte.MAX_VALUE ? (byte) id : 0);
    }

    /**
     *
     * @param id
     * @return
     */
    public static JPacketFieldX getForID(byte id) {
	for (JPacketFieldX e : values()) {
	    if (e.id == id) {
		return e;
	    }
	}
	return INVALID;
    }
}
