/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JIOX.JMouseX.JMouseEventX;

import JEventX.JEventX;

/**
 *
 * @author RlonRyan
 */
public class JMouseEventX extends JEventX{
    
    public final MouseEvent type;
    
    public static enum MouseEvent {
        MOUSE_MOVED,
	MOUSE_CLICK_LEFT,
	MOUSE_CLICK_CENTER,
	MOUSE_CLICK_RIGHT,
	MOUSE_WHEEL_MOVED;
    }
    
    public JMouseEventX(String mode, MouseEvent type) {
	super(mode);
	this.type = type;
    }

    @Override
    public String toString() {
	return type.toString();
    }
    
}
