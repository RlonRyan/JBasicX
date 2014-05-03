/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JIOX.JMouseX.JMouseEventX;

import JEventX.JEventX;
import JGameEngineX.JGameEngineX;

/**
 *
 * @author RlonRyan
 */
public class JMouseEventX extends JEventX{
    
    public final MouseEvent type;
    
    public static enum MouseEvent {
        MOUSE_MOVED,
	MOUSE_WHEEL_MOVED;
    }
    
    public JMouseEventX(JGameEngineX.GAME_STATUS mode, MouseEvent type) {
	super(mode);
	this.type = type;
    }
    
}
