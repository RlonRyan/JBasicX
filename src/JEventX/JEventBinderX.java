/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JEventX;

import java.awt.AWTEvent;
import java.awt.Event;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author RlonRyan
 */
public class JEventBinderX {

    public HashMap<String, HashMap<Integer, HashMap<Integer, HashMap<Method, Object[]>>>> bindings;

    public JEventBinderX() {
	bindings = new HashMap<>();
    }

    public final void bind(String mode, int eventid, Method meth, Object... args) {
	bind(mode, eventid, 0, meth, args);
    }
    
    public final void bind(String mode, int eventid, int meta, Method meth, Object... args) {
	if (!this.bindings.containsKey(mode)) {
	    this.bindings.put(mode, new HashMap<Integer, HashMap<Integer, HashMap<Method, Object[]>>>());
	}
	if (!this.bindings.get(mode).containsKey(eventid)) {
	    this.bindings.get(mode).put(eventid, new HashMap<Integer, HashMap<Method, Object[]>>());
	}
	if (!this.bindings.get(mode).containsKey(meta)) {
	    this.bindings.get(mode).get(eventid).put(meta, new HashMap<Method, Object[]>());
	}
	this.bindings.get(mode).get(eventid).get(meta).put(meth, args);
    }

    public final void unbind(String mode, int eventid, Method m) {
	unbind(mode, eventid, 0, m);
    }
    
    public final void unbind(String mode, int eventid, int meta, Method m) {
	if (this.bindings.containsKey(mode) && this.bindings.get(mode).containsKey(eventid) && this.bindings.get(mode).get(eventid).containsKey(meta)) {
	    this.bindings.get(mode).get(eventid).get(meta).remove(m);
	}
    }
    
    public final void fireEvent(String mode, Event e) {
	fireEvent(mode, e, 0);
    }
    
    public final void fireEvent(String mode, AWTEvent e) {
	fireEvent(mode, e, 0);
    }

    public final void fireEvent(String mode, Event e, int meta) {
	if (this.bindings.containsKey(mode) && this.bindings.get(mode).containsKey(e.id) && this.bindings.get(mode).get(e.id).containsKey(meta)) {
	    for (Method m : bindings.get(mode).get(e.id).get(meta).keySet()) {
		try {
		    m.invoke(bindings.get(mode).get(e.id).get(meta).get(m)[0], Arrays.copyOfRange(bindings.get(mode).get(e.id).get(meta).get(m), 1, bindings.get(mode).get(e.id).get(meta).get(m).length));
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		    System.err.println(ex.toString());
		}
	    }
	}
    }
    
    public final void fireEvent(String mode, AWTEvent e, int meta) {
	if (this.bindings.containsKey(mode) && this.bindings.get(mode).containsKey(e.getID()) && this.bindings.get(mode).get(e.getID()).containsKey(meta)) {
	    for (Method m : bindings.get(mode).get(e.getID()).get(meta).keySet()) {
		try {
		    m.invoke(bindings.get(mode).get(e.getID()).get(meta).get(m)[0], Arrays.copyOfRange(bindings.get(mode).get(e.getID()).get(meta).get(m), 1, bindings.get(mode).get(e.getID()).get(meta).get(m).length));
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		    System.err.println(ex.toString());
		}
	    }
	}
    }

}
