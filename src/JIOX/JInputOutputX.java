/**
 * @author RlonRyan
 * @name JSoundX
 * @version 1.0.0
 * @date Dec 17, 2011
 * @info Input-Output interface.
 *
 */
package JIOX;

import java.awt.AWTEvent;
import java.util.EventObject;

/*
 * @author RlonRyan
 * @name JInputOutputX
 */

/**
 *
 * @author Ryan
 */

public interface JInputOutputX {
    
    /**
     *
     * @param e
     */
    public void updateIO(AWTEvent e);

    /**
     *
     * @param e
     */
    public void lostFocus(EventObject e);
}
