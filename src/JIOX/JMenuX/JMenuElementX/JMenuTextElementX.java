/*
 *  Blah...
 */
package JIOX.JMenuX.JMenuElementX;

import JBasicX.JStyleX;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
 * @author RlonRyan
 * @name JMenuTextElementX
 * @date Dec 4, 2013
 *
 */

/**
 *
 * @author Ryan
 */

public class JMenuTextElementX implements JMenuElementX {

    /*
     * Variable Variables
     */
    private boolean isHighlightable = true;
    private boolean isSelectable = true;
    
    public String text;
    private JMenuElementStateX state;
    
    private final JMenuElementActionX action;
    private final Rectangle bounds;

    /*
     * Constructor
     */

    /**
     *
     * @param text
     */
    
    public JMenuTextElementX(String text) {
	this.text = text;
	this.state = JMenuElementStateX.NORMAL;
	this.action = () -> {};
	this.bounds = new Rectangle(0, 0);
    }

    /**
     *
     * @param text
     * @param width
     * @param height
     */
    public JMenuTextElementX(String text, int width, int height) {
	this.text = text;
	this.state = JMenuElementStateX.NORMAL;
	this.action = () -> {};
	this.bounds = new Rectangle(width, height);
    }

    /**
     *
     * @param text
     * @param action
     */
    public JMenuTextElementX(String text, JMenuElementActionX action) {
	this.text = text;
	this.state = JMenuElementStateX.NORMAL;
	this.action = action;
	this.bounds = new Rectangle(0, 0);
    }

    /**
     *
     * @param text
     * @param action
     * @param width
     * @param height
     */
    public JMenuTextElementX(String text, JMenuElementActionX action, int width, int height) {
	this.text = text;
	this.state = JMenuElementStateX.NORMAL;
	this.action = action;
	this.bounds = new Rectangle(width, height);
    }

    @Override
    public boolean isHighlightable() {
	return isHighlightable;
    }

    @Override
    public boolean isSelectable() {
	return isSelectable;
    }
    
    @Override
    public JMenuElementStateX getState() {
	return state;
    }

    @Override
    public Rectangle getBounds() {
	return bounds;
    }

    @Override
    public void setHighlightable(boolean isHighlightable) {
	this.isHighlightable = isHighlightable;
    }
    
    @Override
    public void setSelectable(boolean isSelectable) {
	this.isSelectable = isSelectable;
    }
    
    /**
     *
     * @param width
     */
    @Override
    public void setWidth(int width) {
	this.bounds.setRect(0, 0, width, this.bounds.getHeight());
    }

    /**
     *
     * @param height
     */
    @Override
    public void setHeight(int height) {
	this.bounds.setRect(0, 0, this.bounds.getWidth(), height);
    }

    @Override
    public void validate() {
	throw new UnsupportedOperationException("Not supported yet. As far as I care, this element is valid.");
    }

    @Override
    public void resize() {
	throw new UnsupportedOperationException("Not supported yet. This element will not resize."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void highlight() {
	this.state = JMenuElementStateX.HIGHLIGHTED;
    }

    @Override
    public void select() {
	this.state = JMenuElementStateX.SELECTED;
	this.action.act();
	this.state = JMenuElementStateX.HIGHLIGHTED;
    }

    @Override
    public void reset() {
	this.state = JMenuElementStateX.NORMAL;
    }

    @Override
    public void draw(Graphics2D g2d, JStyleX style, int x, int y) {
	switch (state) {
	    case NORMAL:
		g2d.setColor(style.getColor("body"));
		g2d.setFont(style.getFont("body"));
		break;
	    case HIGHLIGHTED:
		g2d.setColor(style.getColor("highlight"));
		g2d.setFont(style.getFont("highlight"));
		break;
	    case SELECTED:
		g2d.setColor(style.getColor("selected"));
		g2d.setFont(style.getFont("selected"));
		break;
	    default:
		g2d.setColor(style.getColor("body"));
		g2d.setFont(style.getFont("body"));
		break;
	}
	g2d.setColor(style.getColor(text));
	g2d.drawString(this.text, x, y + (int)(this.bounds.getCenterY() + g2d.getFontMetrics().getAscent() / 2));
    }

    /**
     *
     * @param g2d
     * @param style
     * @param x
     * @param y
     */
    @Override
    public void drawBounds(Graphics2D g2d, JStyleX style, int x, int y) {
	g2d.drawRect(x, y, bounds.width, bounds.height);
    }

}
