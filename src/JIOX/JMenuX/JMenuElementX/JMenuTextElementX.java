/*
 *  Blah...
 */
package JIOX.JMenuX.JMenuElementX;

import JBasicX.JStyleX;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RlonRyan
 * @name JMenuTextElementX
 * @date Dec 4, 2013
 *
 */
public class JMenuTextElementX implements JMenuElementX {

    /*
     * Variable Variables
     */
    private String text;
    private JMenuElementStateX state;
    private JMenuElementActionX action;
    
    /*
     * Constructor
     */
    public JMenuTextElementX(String text) {
	this.text = text;
	this.state = JMenuElementStateX.NORMAL;
	this.action = () -> {};
    }
    
    public JMenuTextElementX(String text, JMenuElementActionX action) {
	this.text = text;
	this.state = JMenuElementStateX.NORMAL;
	this.action = action;
    }

    @Override
    public JMenuElementStateX getState() {
	return state;
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
	//this.state = (this.state == JMenuElementStateX.HIGHLIGHTED) ? JMenuElementStateX.NORMAL:JMenuElementStateX.HIGHLIGHTED;
	this.state = JMenuElementStateX.HIGHLIGHTED;
    }

    @Override
    public void dehighlight() {
	this.state = JMenuElementStateX.NORMAL;
    }

    @Override
    public void select() {
	this.state = JMenuElementStateX.SELECTED;
	this.action.act();
	this.state = JMenuElementStateX.HIGHLIGHTED;
    }

    @Override
    public void deselect() {
	this.state = JMenuElementStateX.NORMAL;
    }

    @Override
    public void draw(Graphics2D g2d, JStyleX style, int x, int y, int width) {
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
	for (String e : wrapToLines(this.text, g2d, width)) {
	    y += g2d.getFontMetrics().getHeight();
	    g2d.drawString(e, x, y);
	}
    }

    public static final List<String> wrapToLines(String line, Graphics2D context, int length) {
	List<String> lines = new ArrayList<>();
	length = length * context.getFontMetrics().getMaxAdvance();
	for (int i = 0; i < line.length(); i += length) {
	    if (line.length() < (i + length)) {
		lines.add(line.substring(i));
	    }
	    else {
		lines.add(line.substring(i, i + length));
	    }
	}
	return lines;
    }

}
