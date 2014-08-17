/*
 * @author RlonRyan
 * @name JSoundX
 * @version 1.0.0
 * @date Dec 17, 2011
 * @info Input-Output interface.
 *
 */
package JIOX.JMenuX;

/*
 * @author RlonRyan
 * @name JMenuX
 * Effectively deprecated, lambda expressions in the menu elements are much better.
 */

/**
 *
 * @author Ryan
 */

public interface JMenuListenerX {

    /**
     *
     * @param source
     * @param data
     */
    public void elementSelected(Object source, int... data);

    /**
     *
     * @param source
     * @param data
     */
    public void elementHighlighted(Object source, int... data);
}
