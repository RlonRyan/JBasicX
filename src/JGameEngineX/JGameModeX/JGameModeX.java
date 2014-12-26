/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JGameEngineX.JGameModeX;

import JEventX.JEventBinderX;
import JGameEngineX.JGameEngineX;
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
    
    /**
     *
     */
    public final String name;
    
    /**
     *
     */
    public final JEventBinderX bindings;
    
    /**
     *
     */
    public final JGameEngineX holder;

    /**
     * Creates a new game mode for a JGameX.
     * 
     * @param name The name of the mode to be referenced by.
     * @param holder The game engine running the mode.
     */
    public JGameModeX(String name, JGameEngineX holder) {
	this.name = name.toLowerCase();
	this.bindings = new JEventBinderX();
	this.holder = holder;
    }
    
    /**
     * Called during game initialization. Initialize the mode here.
     */
    public abstract boolean init();
    
    /**
     * Called following game initialization. Register all event bindings here.
     * Example:
     * <code>bindings.bind(new Pair(Event.KEY_PRESS, KeyEvent.VK_ESCAPE), (e) - System.exit(0));</code>
     */
    public abstract void registerBindings();
    
    /**
     * Called during game execution, following a switch to the mode. Called once per mode change, this should prepare the mode to take over.
     */
    public abstract void start();
    
    /**
     * Called during the mode's execution. All runtime code should be placed here.
     */
    public abstract void update();
    
    /**
     * Called once per screen update, requests the mode to draw the screen. All of the mode's rendering code should go here.
     * @param g2d
     */
    public abstract void paint(Graphics2D g2d);
    
    /**
     * Called once per screen update, requests the mode's information be drawn to the screen.
     * @param g2d
     */
    public abstract void paintGameData(Graphics2D g2d);
    
    /**
     * Called during a mode change, requests that the mode pauses its execution.
     */
    public abstract void pause();
    
    /**
     * Called during a mode change, request that a mode pauses its execution and reset all data.
     */
    public abstract void stop();
    
}
