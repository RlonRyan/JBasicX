/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JGameEngineX.JGameEventX;

import JGameEngineX.JGameModeX.JGameModeX;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author RlonRyan
 */
public class JGameEventBinderX {

    public HashMap<JGameModeX, HashMap<JGameEventX, List<Method>>> bindings;

    public final void bind(Method meth, JGameEventX e, JGameModeX mode) {
	if (!this.bindings.containsKey(mode)) {
	    this.bindings.put(mode, new HashMap<JGameEventX, List<Method>>());
	}
	if (!this.bindings.get(mode).containsKey(e)) {
	    this.bindings.get(mode).put(e, new ArrayList<Method>());
	}
	this.bindings.get(mode).get(e).add(meth);
    }

    public final void unbind(Method m, JGameEventX e, JGameModeX mode) {
	if (this.bindings.containsKey(mode) && this.bindings.get(mode).containsKey(e)) {
	    this.bindings.get(mode).get(e).remove(m);
	}
    }

    public final void fireEvent(JGameEventX e, JGameModeX mode) {
	if (this.bindings.containsKey(mode) && this.bindings.get(mode).containsKey(e)) {
	    for (Method m : bindings.get(mode).get(e)) {
		try {
		    if (m.isAccessible()) {
			m.invoke(e);
		    }
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		    //???
		}
	    }
	}
    }

}
