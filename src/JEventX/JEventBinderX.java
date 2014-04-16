/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JEventX;

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

    public final void bind(JEventX e, Method meth, Object... args) {
	if (!this.bindings.containsKey(e.mode)) {
	    this.bindings.put(e.mode, new HashMap<String, HashMap<Method, Object[]>>());
	}
	if (!this.bindings.get(e.mode).containsKey(e.toString())) {
	    this.bindings.get(e.mode).put(e.toString(), new HashMap<Method, Object[]>());
	}
	this.bindings.get(e.mode).get(e.toString()).put(meth, args);
    }

    public final void unbind(JEventX e, Method m) {
	if (this.bindings.containsKey(e.mode) && this.bindings.get(e.mode).containsKey(e.toString())) {
	    this.bindings.get(e.mode).get(e.toString()).remove(m);
	}
    }

    public final void fireEvent(JEventX e) {
	if (this.bindings.containsKey(e.mode) && this.bindings.get(e.mode).containsKey(e.toString())) {
	    for (Method m : bindings.get(e.mode).get(e.toString()).keySet()) {
		try {
		    m.invoke(bindings.get(e.mode).get(e.toString()).get(m)[0], Arrays.copyOfRange(bindings.get(e.mode).get(e.toString()).get(m), 1, bindings.get(e.mode).get(e.toString()).get(m).length));
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		    System.err.println(ex.toString());
		}
	    }
	}
    }

}
