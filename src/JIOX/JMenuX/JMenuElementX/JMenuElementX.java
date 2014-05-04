package JIOX.JMenuX.JMenuElementX;

import JBasicX.JStyleX;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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
    
    @FunctionalInterface
    interface JMenuElementActionX {
	void act();
    }

    /**
     * Retrieves the state of the element.
     * <p/>
     * @return state
     */
    public JMenuElementStateX getState();
    
    /**
     * Retrieves the element's dimensions.
     * <p/>
     * @return state
     */
    public Rectangle getBounds();
    
    public void setWidth(int width);
    
    public void setHeight(int height);

    /**
     * Checks to see if the element is valid to use.
     */
    public void validate();

    /**
     * Requests that the element be resized.
     */
    public void resize();

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
     * @param style The style to use.
     * @param x     The x-position of the element.
     * @param y     The y-position of the element.
     * @param width The maximum allowable width of the element.
     */
    public void draw(Graphics2D g2d, JStyleX style, int x, int y);
    
    public void drawBounds(Graphics2D g2d, JStyleX style, int x, int y);
}
