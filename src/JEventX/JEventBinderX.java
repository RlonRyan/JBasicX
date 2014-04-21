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

    public HashMap<String, HashMap<String, HashMap<Method, Object[]>>> bindings;

    public JEventBinderX() {
	bindings = new HashMap<>();
    }

    public final void bind(String mode, String event, Method meth, Object... args) {
	if (!this.bindings.containsKey(mode)) {
	    this.bindings.put(mode, new HashMap<String, HashMap<Method, Object[]>>());
	}
	if (!this.bindings.get(mode).containsKey(event)) {
	    this.bindings.get(mode).put(event, new HashMap<Method, Object[]>());
	}
	this.bindings.get(mode).get(event).put(meth, args);
    }

    public final void unbind(String mode, String event, Method m) {
	if (this.bindings.containsKey(mode) && this.bindings.get(mode).containsKey(event)) {
	    this.bindings.get(mode).get(event).remove(m);
	}
    }

    public final void fireEvent(String mode, Event e) {
	if (this.bindings.containsKey(mode) && this.bindings.get(mode).containsKey(e.toString())) {
	    for (Method m : bindings.get(mode).get(e.toString()).keySet()) {
		try {
		    m.invoke(bindings.get(mode).get(e.toString()).get(m)[0], Arrays.copyOfRange(bindings.get(mode).get(e.toString()).get(m), 1, bindings.get(mode).get(e.toString()).get(m).length));
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		    System.err.println(ex.toString());
		}
	    }
	}
    }
    
    public final void fireEvent(String mode, AWTEvent e) {
	if (this.bindings.containsKey(mode) && this.bindings.get(mode).containsKey(e.toString())) {
	    for (Method m : bindings.get(mode).get(e.toString()).keySet()) {
		try {
		    m.invoke(bindings.get(mode).get(e.toString()).get(m)[0], Arrays.copyOfRange(bindings.get(mode).get(e.toString()).get(m), 1, bindings.get(mode).get(e.toString()).get(m).length));
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		    System.err.println(ex.toString());
		}
	    }
	}
    }

}
