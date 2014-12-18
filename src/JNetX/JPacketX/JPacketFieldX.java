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
    CUSTOM(1, 4),
    
    /**
     *
     */
    MESSAGE(2, 4),

    /**
     *
     */
    KEY(3, 4),

    /**
     *
     */
    MOUSE(4, 4),
    
    XPOS(5, 4),
    
    YPOS(6, 4);

    /**
     *
     */
    public byte id;
    public byte size;

    private JPacketFieldX(int id, int size) {
	this.id = (byte) id;
	this.size = (byte) size;
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
