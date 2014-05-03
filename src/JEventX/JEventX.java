/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JEventX;

import JGameEngineX.JGameEngineX;

/**
 *
 * @author RlonRyan
 */
public abstract class JEventX {
    public final JGameEngineX.GAME_STATUS mode;
    
    public JEventX(JGameEngineX.GAME_STATUS mode) {
	this.mode = mode;
    }
}
