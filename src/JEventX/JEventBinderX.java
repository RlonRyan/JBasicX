/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JEventX;

import JGameEngineX.JGameEngineX;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author RlonRyan
 */
public class JEventBinderX {

    public HashMap<JGameEngineX.GAME_STATUS, HashMap<JEventX, HashMap<Method, Class[]>>> bindings;

    public final void bind(JEventX e, Method meth, Class...args) {
	if (!this.bindings.containsKey(e.mode)) {
	    this.bindings.put(e.mode, new HashMap<JEventX, HashMap<Method, Class[]>>());
	}
	if (!this.bindings.get(e.mode).containsKey(e)) {
	    this.bindings.get(e.mode).put(e, new HashMap<Method, Class[]>());
	}
	this.bindings.get(e.mode).get(e).put(meth, args);
    }

    public final void unbind(JEventX e, Method m) {
	if (this.bindings.containsKey(e.mode) && this.bindings.get(e.mode).containsKey(e)) {
	    this.bindings.get(e.mode).get(e).remove(m);
	}
    }

    public final void fireEvent(JEventX e) {
	if (this.bindings.containsKey(e.mode) && this.bindings.get(e.mode).containsKey(e)) {
	    for (Method m : bindings.get(e.mode).get(e).keySet()) {
		try {
		    if (m.isAccessible()) {
			m.invoke(bindings.get(e.mode).get(e).get(m));
		    }
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		    //???
		}
	    }
	}
    }

}
