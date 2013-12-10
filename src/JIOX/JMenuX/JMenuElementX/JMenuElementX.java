
package JIOX.JMenuX.JMenuElementX;

import JBasicX.JStyleX;
import java.awt.Graphics2D;
import java.util.HashMap;

/**
 * @author RlonRyan
 * @name JMenuElementX
 * @version 1.0.0
 * @date Jan 9, 2012
 * @info Powered by JBasicX
 */
public interface JMenuElementX {

    /**
     *  Checks to see if the element is valid to use.
     */
    public void validate();

    /**
     *  Requests that the element be resized.
     */
    public void resize();

    /**
     *  Applies a style to the element.
     *  @param style
     */
    public void applyStyle(JStyleX style);

    /**
     *  Highlights the element.
     */
    public void highlight();

    /**
     *  Selects the element.
     */
    public void select();

    /**
     *  Renders the element using the given graphics.
     *  @param g2d      The graphics to be drawn to.
     *  @param x        The x-position of the element.
     *  @param y        The y-position of the element.
     *  @param width    The maximum allowable width of the element.
     */
    public void draw(Graphics2D g2d, int x, int y, int width);
}
