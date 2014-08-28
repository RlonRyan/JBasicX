package JIOX.JMenuX.JMenuElementX;

import JBasicX.JStyleX;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
 * @author RlonRyan
 * @name JMenuElementX
 * @version 1.0.0
 * @date Jan 9, 2012
 * @info Powered by JBasicX
 */

/**
 *
 * @author Ryan
 */

public interface JMenuElementX {

    /**
     *
     */
    public enum JMenuElementStateX {

	/**
	 *
	 */
	STATELESS,

	/**
	 *
	 */
	NORMAL,

	/**
	 *
	 */
	HIGHLIGHTED,

	/**
	 *
	 */
	SELECTED;
    }
    
    /**
     *
     */
    @FunctionalInterface
    interface JMenuElementActionX {

	/**
	 *
	 */
	void act();
    }
    
    public boolean isHighlightable();
    
    public boolean isSelectable();

    /**
     * Retrieves the state of the element.
     * @return state
     */
    public JMenuElementStateX getState();
    
    /**
     * Retrieves the element's dimensions.
     * @return state
     */
    public Rectangle getBounds();
    
    /**
     *
     * @param width
     */
    public void setWidth(int width);
    
    /**
     *
     * @param height
     */
    public void setHeight(int height);

    public void setHighlightable(boolean isHighlightable);
    
    public void setSelectable(boolean isSelectable);
    
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
     * Selects the element.
     */
    public void select();

    /**
     * Resets the element.
     */
    public void reset();

    /**
     * Renders the element using the given graphics.
     * @param g2d   The graphics to be drawn to.
     * @param style The style to use.
     * @param x     The x-position of the element.
     * @param y     The y-position of the element.
     */
    public void draw(Graphics2D g2d, JStyleX style, int x, int y);
    
    /**
     *
     * @param g2d
     * @param style
     * @param x
     * @param y
     */
    public void drawBounds(Graphics2D g2d, JStyleX style, int x, int y);
}
