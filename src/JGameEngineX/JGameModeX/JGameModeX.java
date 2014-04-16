/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JGameEngineX.JGameModeX;

import java.awt.Graphics2D;

/*
 * public static enum GAME_STATUS {
 *
 * GAME_STOPPED,
 * GAME_INTIALIZING,
 * GAME_STARTING,
 * GAME_MENU,
 * GAME_RUNNING,
 * GAME_PAUSED;
 * }
 */

/**
 *
 * @author RlonRyan
 */
public abstract class JGameModeX {
    
    public final String name;

    public JGameModeX(String name) {
	this.name = name;
    }
    
    public abstract boolean initialize();
    
    public abstract void start();
    
    public abstract void update();
    
    public abstract void paint(Graphics2D g2d);
    
    public abstract void pause();
    
    public abstract void stop();
    
}
