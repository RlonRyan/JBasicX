package JIOX.JMenuX.JMenuElementX;

import JBasicX.JStyleX;
import java.awt.Graphics2D;

/**
 * @author RlonRyan
 * @name JMenuElementX
 * @version 1.0.0
 * @date Jan 9, 2012
 * @info Powered by JBasicX
 */
public interface JMenuElementX {

    public enum JMenuElementStateX {

        STATELESS,
        NORMAL,
        HIGHLIGHTED,
        SELECTED;
    }

    /**
     * Retrieves the state of the element.
     * <p/>
     * @return state
     */
    public JMenuElementStateX getState();

    /**
     * Checks to see if the element is valid to use.
     */
    public void validate();

    /**
     * Requests that the element be resized.
     */
    public void resize();

    /**
     * Applies a style to the element.
     * <p/>
     * @param style
     */
    public void applyStyle(JStyleX style);

    /**
     * Highlights the element.
     */
    public void highlight();

    /**
     * Removes highlighting from the element.
     */
    public void dehighlight();

    /**
     * Selects the element.
     */
    public void select();

    /**
     * Deselects the element.
     */
    public void deselect();

    /**
     * Renders the element using the given graphics.
     * <p/>
     * @param g2d   The graphics to be drawn to.
     * @param x     The x-position of the element.
     * @param y     The y-position of the element.
     * @param width The maximum allowable width of the element.
     */
    public void draw(Graphics2D g2d, int x, int y, int width);
}
