/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JIOX;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  RlonRyan
 * @name    JKeyX
 * @date    Jan 10, 2014
 **/

public class JKeyboardX implements KeyListener {

    @SuppressWarnings("FieldMayBeFinal")
    private List<JInputOutputX> listeners;
    private boolean[] keys;
    
    public JKeyboardX() {
        this.listeners = new ArrayList<>();
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
    
    @Override
    public void keyTyped(KeyEvent k) {
    }

    @Override
    public void keyPressed(KeyEvent k) {
        this.keys[k.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(KeyEvent k) {
        this.keys[k.getKeyCode()] = false;
    }
    
    /*
     * Event Methods Go Way Down Here
     * Likely will be deprecated or removed, as the elements themselves will get
     * their own events.
     */
    synchronized public final void addEventListener(JInputOutputX listener) {
        listeners.add(listener);
    }

    synchronized public final void removeEventListener(JInputOutputX listener) {
        listeners.remove(listener);
    }

    synchronized public void fireEvent() {
        //fill!
    }
    
}
