/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JEventX;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import javafx.util.Pair;

/**
 *
 * @author RlonRyan
 */
public class JEventBinderX {

    private final HashMap<Pair<Integer, Integer>, ArrayList<Consumer<AWTEvent>>> bindings;

    public JEventBinderX() {
	bindings = new HashMap<>();
    }
    
    public final void bind(int trigger, Consumer<AWTEvent> method) {
	bind(new Pair<>(trigger, 0), method);
    }
    
    public final void bind(int trigger, int subtrigger, Consumer<AWTEvent> method) {
	bind(new Pair<>(trigger, subtrigger), method);
    }

    public final void bind(Pair<Integer, Integer> trigger, Consumer<AWTEvent> method) {
	if (!this.bindings.containsKey(trigger)) {
	    this.bindings.put(trigger, new ArrayList<>());
	}
	this.bindings.get(trigger).add(method);
    }

    public final void release(int trigger) {
	release(new Pair<>(trigger, 0));
    }
    
    public final void release(Pair<Integer, Integer> trigger) {
	if (this.bindings.containsKey(trigger)) {
	    this.bindings.remove(trigger);
	}
    }
    
    public final void release(int trigger, Consumer<AWTEvent> method) {
	release(new Pair<>(trigger, 0), method);
    }

    public final void release(Pair<Integer, Integer> trigger, Consumer<AWTEvent> method) {
	if (this.bindings.containsKey(trigger)) {
	    this.bindings.get(trigger).remove(method);
	}
    }
    
    public final void fireEvent(AWTEvent e) {
	Pair<Integer, Integer> id = null;
	if(e instanceof MouseEvent) {
	    id = new Pair<>(e.getID(), ((MouseEvent)e).getButton());
	}
	else if(e instanceof KeyEvent) {
	    id = new Pair<>(e.getID(), ((KeyEvent)e).getKeyCode());
	}
	if(id != null && this.bindings.containsKey(id)){
	    bindings.get(id).stream().forEach((c) -> c.accept(e));
	}
	id = new Pair<>(e.getID(), 0);
	if(this.bindings.containsKey(id)) {
	    bindings.get(id).stream().forEach((c) -> c.accept(e));
	}
    }
    
    public final void fireEvent(AWTEvent e, int subid) {
	Pair<Integer, Integer> id = new Pair<>(e.getID(), subid);
	if(this.bindings.containsKey(id)){
	    bindings.get(id).stream().forEach((c) -> c.accept(e));
	}
    }

}
