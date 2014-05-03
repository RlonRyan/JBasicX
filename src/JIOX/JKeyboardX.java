/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JIOX;

import JEventX.JEventBinderX;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author RlonRyan
 * @name JKeyX
 * @date Jan 10, 2014
 *
 */
public class JKeyboardX implements KeyListener {

    private JEventBinderX bindings;
    private boolean[] keys;

    public JKeyboardX() {
	this.keys = keys = new boolean[526];
    }

    /**
     * Returns the currently active keys.
     * <p/>
     * @return The active key array.
     */
    public final boolean[] getKeys() {
	return this.keys;
    }

    /**
     * Returns the currently active keys as a string.
     * <p/>
     * @return The active key array as a string.
     */
    public final String getKeysDownString() {
	String keysdown = "Keys: ";
	for (int i = 1; i < 525; i++) {
	    if (this.keys[i] == true) {
		keysdown += " " + KeyEvent.getKeyText(i) + ";";
	    }
	}
	return keysdown;
    }

    /**
     * Tests a key to see if it is currently active.
     * <p/>
     * @param keycode The key to test for its the state.
     * <p/>
     * @return
     */
    public final boolean isKeyDown(int keycode) {
	return this.keys[keycode];
    }

    /**
     * Tests a key to see if it is currently active and if it is, removes it
     * from the active key array.
     * <p/>
     * @param keycode The key to test for its the state.
     * <p/>
     * @return
     */
    public final boolean isKeyDownAndRemove(int keycode) {
	if (this.keys[keycode]) {
	    this.keys[keycode] = false;
	    return true;
	}
	return false;
    }
    
    public void setBindings(JEventBinderX bindings) {
	this.bindings = bindings;
    }

    @Override
    public void keyTyped(KeyEvent k) {
	fireEvent(k);
    }

    @Override
    public void keyPressed(KeyEvent k) {
	this.keys[k.getKeyCode()] = true;
	fireEvent(k);
    }

    @Override
    public void keyReleased(KeyEvent k) {
	this.keys[k.getKeyCode()] = false;
	fireEvent(k);
    }

    /*
     * Event Methods Go Way Down Here
     * Likely will be deprecated or removed, as the elements themselves will get
     * their own events.
     */
    public void fireEvent(KeyEvent k) {
	if (bindings != null) {
	    this.bindings.fireEvent(k);
	}
    }
}
