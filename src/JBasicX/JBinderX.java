/*
 * JBasicX
 * By RlonRyan
 * 
 * All rights reserved. No warrenty on use. User accepts all risks and damages.
 * Class: JBinderX
 * Description: JBinderX
 */

package JBasicX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author RlonRyan
 * @param <T> Trigger Type
 * @param <D> Data Type
 */
public class JBinderX <T, D> {

    private final HashMap<T, List<Consumer<D>>> bindings;
    private List<Consumer<D>> defaultActions;

    /**
     * Creates a new event binder.
     * Initializes main hashmap.
     */
    public JBinderX() {
	bindings = new HashMap<>();
	defaultActions = new ArrayList<>();
    }
    
    /**
     * Binds a lambda action to a event. Any previously bound actions to the event are preserved.
     * @param method A Lambda expression. <code>() - {};</code>
     */
    public final void bindDefault(Consumer<D> method) {
	this.defaultActions.clear();
	this.defaultActions.add(method);
    }
    
    /**
     * Binds a lambda action to a event. Any previously bound actions to the event are preserved.
     * @param trigger The event id of the triggering event.
     * @param method A Lambda expression. <code>() - {};</code>
     */
    public final void bind(T trigger, Consumer<D> method) {
	if (!this.bindings.containsKey(trigger)) {
	    this.bindings.put(trigger, new ArrayList<>());
	}
	this.bindings.get(trigger).add(method);
    }
    
    /**
     * Gets a bound lambda triggers.
     * @return An array of the triggers converted to a string;
     */
    public final String[] getTriggers() {
	List<String> keys = new ArrayList<>();
	bindings.keySet().forEach((e)->{keys.add(e.toString());});
	return keys.toArray(new String[]{});
    }
    
    /**
     * Gets a bound lambda action. Returns null if no actions exist.
     * @param trigger The event id of the triggering event.
     */
    public final List<Consumer<D>> get(T trigger) {
	return bindings.get(trigger);
    }
    
    /**
     * Releases all lambda expressions bound to the trigger id and mask.
     * @param trigger
     */
    public final void release(T trigger) {
	if (this.bindings.containsKey(trigger)) {
	    this.bindings.remove(trigger);
	}
    }

    /**
     * Releases a specific lambda expression based on its masked trigger and the lambda expression itself.
     * @param trigger
     * @param method
     */
    public final void release(T trigger, Consumer<D> method) {
	if (this.bindings.containsKey(trigger)) {
	    this.bindings.get(trigger).remove(method);
	}
    }
    
    /**
     * Distributes an event based on its trigger. Calls all lambda expressions with matching id and auto-obtained subid.
     * @param trigger
     * @param data
     */
    public final void trigger(T trigger, D data) {
	bindings.getOrDefault(trigger, defaultActions).stream().forEach((c) -> c.accept(data));
    }

}