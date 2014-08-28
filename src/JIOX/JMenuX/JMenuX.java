/*
 * @author RlonRyan
 * @name JMenuX
 * @version 1.0.0
 * @date Dec 17, 2011
 * @info Menu Interface.
 */
package JIOX.JMenuX;

import JBasicX.JStyleX;
import JIOX.JMenuX.JMenuElementX.JMenuElementX;
import JIOX.JMenuX.JMenuElementX.JMenuTextElementX;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author RlonRyan
 * @name JMenuX
 */
/**
 *
 * @author Ryan
 */
public class JMenuX {

    /*
     * Constants for events
     */
    /**
     *
     */
    public enum JMenuStateX {

	/**
	 *
	 */
	MENU_CHANGED,
	/**
	 *
	 */
	ELEMENT_HIGHLIGHTED,
	/**
	 *
	 */
	ELEMENT_SELECTED;
    }

    /*
     * Menu Properties
     */
    private final String title;
    private final List<JMenuElementX> elements;
    private final Rectangle bounds;
    private final Rectangle header;

    /*
     * Menu Properties
     */
    private int index;
    private JStyleX style;
    private boolean visible;
    private int separator;

    /*
     * Constructors Go Here
     */
    /**
     *
     * @param title
     * @param x
     * @param y
     * @param width
     * @param height
     * @param elements
     */
    public JMenuX(String title, int x, int y, int width, int height, String... elements) {
	this.title = title;
	this.bounds = new Rectangle(x, y, width, height);
	this.header = new Rectangle(x + 5 * width / 100, y, 90 * width / 100, 10 * height / 100);
	this.elements = new ArrayList<>();
	this.style = new JStyleX();
	this.validateStyle();
	for (String e : elements) {
	    this.elements.add(new JMenuTextElementX(e));
	}
	this.separator = 10;
    }

    /*
     * Setters Go Here
     */
    /**
     *
     * @param style
     */
    synchronized public final void setStyle(JStyleX style) {
	this.style = style;
    }

    /**
     *
     * @param name
     * @param element
     */
    synchronized public final void setStyleElement(String name, Object element) {
	this.style.setStyleElement(name, element);
    }

    /*
     * Putters Go Here
     */
    /**
     *
     * @param element
     */
    synchronized public final void addMenuElement(JMenuElementX element) {
	this.elements.add(element);
	this.normalize();
    }

    /**
     *
     * @param position
     * @param element
     */
    synchronized public final void addMenuElement(int index, JMenuElementX element) {
	this.elements.add(index, element);
	this.normalize();
    }

    synchronized public final int findElementContaining(String text) {
	for (int i = 0; i < this.elements.size(); i++) {
	    if (this.elements.get(i) instanceof JMenuTextElementX) {
		if (((JMenuTextElementX) this.elements.get(i)).text.contains(text)) {
		    return i;
		}
	    }
	}
	return -1;
    }

    /*
     * Getters Go Here
     */
    /**
     *
     * @return
     */
    final public String getTitle() {
	return this.title;
    }

    /**
     *
     * @return
     */
    synchronized public final int getIndex() {
	return this.index;
    }

    /**
     *
     * @param idex
     *             <p>
     * @return
     */
    synchronized public final JMenuElementX getMenuElement(int idex) {
	if (index < 0 || index >= this.elements.size()) {
	    return null;
	}
	return this.elements.get(index);
    }

    /*
     * Incrementers Go Here
     */
    /**
     *
     */
    synchronized public final void incrementHighlight() {
	this.incrementHighlight(1);
    }

    /**
     *
     */
    synchronized public final void deincrementHighlight() {
	this.incrementHighlight(-1);
    }

    /**
     *
     * @param increment
     */
    synchronized public final void incrementHighlight(int increment) {
	if (increment == 0) {
	    return;
	}
	while (!this.elements.get(verifyIndex(this.index + increment)).isHighlightable()) {
	    increment += increment < 0 ? -1 : 1;
	}

	highlight(this.index + increment);

    }

    /*
     * Methods Go Here
     */
    /**
     *
     * @param index
     */
    synchronized public final void highlight(int index) {
	this.elements.get(this.index).reset();
	this.index = verifyIndex(index);
	while (!this.elements.get(this.index).isHighlightable()) {
	    this.index = verifyIndex(this.index + 1);

	    if (this.index == index) {
		Logger.getLogger(title).log(Level.WARNING, "No Highlightable elements found!");
		return;
	    }
	}
	this.elements.get(this.index).highlight();
    }

    synchronized public final int verifyIndex(int i) {
	i = i % this.elements.size();
	if (i < 0) {
	    i += this.elements.size();
	}
	return i;
    }

    /**
     *
     * @param p
     */
    synchronized public final void selectMenuElement(Point p) {
	int pos = this.bounds.y + this.header.height + this.separator;
	for (int i = 0; i < this.elements.size(); i++) {
	    if (new Rectangle(this.bounds.x, pos, elements.get(i).getBounds().width, elements.get(i).getBounds().height).contains(p) && elements.get(i).isSelectable()) {
		selectMenuElement(i);
		return;
	    }
	    pos += elements.get(i).getBounds().height + separator;
	}
    }

    /**
     *
     * @param index
     */
    synchronized public final void selectMenuElement(int index) {
	this.elements.get(this.index).reset();
	this.index = index % this.elements.size();
	selectMenuElement();
    }

    /**
     *
     */
    synchronized public final void selectMenuElement() {
	if (this.index < this.elements.size() && this.elements.get(index).isSelectable()) {
	    this.elements.get(index).select();
	}
    }

    synchronized public final JMenuElementX removeElement(int index) {
	if (index < 0 || index >= this.elements.size()) {
	    Logger.getLogger(title).log(Level.WARNING, "No menu element of index: {0}", index);
	    return null;
	}
	JMenuElementX result = elements.remove(index);
	if (index <= this.index) {
	    this.incrementHighlight(-1);
	}
	this.normalize();
	return result;
    }

    synchronized public final boolean removeElement(JMenuElementX element) {
	if (index < 0 || index >= this.elements.size()) {
	    Logger.getLogger(title).log(Level.WARNING, "No menu element of index: {0}", index);
	    return false;
	}
	boolean result = elements.remove(element);
	if (index <= this.index) {
	    this.incrementHighlight(-1);
	}
	this.normalize();
	return result;
    }

    /**
     *
     */
    synchronized public final void validateStyle() {
	if (this.style == null) {
	    this.style = new JStyleX();
	}
	if (!this.style.hasStyleElement("background")) {
	    this.style.setColor("background", new Color(255, 255, 255, 100));
	}
	if (!this.style.hasStyleElement("border")) {
	    this.style.setColor("border", Color.WHITE);
	}
	if (!this.style.hasStyleElement("title")) {
	    this.style.setColor("title", Color.WHITE);
	}
	if (!this.style.hasStyleElement("body")) {
	    this.style.setColor("body", Color.LIGHT_GRAY);
	}
	if (!this.style.hasStyleElement("highlight")) {
	    this.style.setColor("highlight", Color.YELLOW);
	}
	if (!this.style.hasStyleElement("selected")) {
	    this.style.setColor("selected", Color.GREEN);
	}
	if (!this.style.hasStyleElement("title")) {
	    this.style.setFont("title", new Font("Monospaced", Font.PLAIN, 16));
	}
	if (!this.style.hasStyleElement("body")) {
	    this.style.setFont("body", new Font("SansSerif", Font.PLAIN, 10));
	}
	if (!this.style.hasStyleElement("highlight")) {
	    this.style.setFont("highlight", new Font("Arial", Font.PLAIN, 10));
	}
    }

    /**
     *
     */
    public void normalize() {
	if(this.elements.size() <= 0) {
	    return;
	}
	
	int width = (int) (0.80 * this.bounds.width);
	int height = (this.bounds.height - this.header.height) / (this.elements.size() + separator);
	for (JMenuElementX e : elements) {
	    if (e.getBounds().width == 0) {
		e.setWidth(width);
	    }
	    if (e.getBounds().height == 0) {
		e.setHeight(height);
	    }
	}
	
    }

    /**
     *
     */
    public void open() {
	if (this.elements.isEmpty()) {
	    this.elements.add(new JMenuTextElementX("Oops! This menu has yet to be filled!"));
	}
	this.normalize();
	this.highlight(0);
	this.visible = true;
    }

    /**
     *
     */
    public void close() {
	this.visible = false;
    }
    /*
     * Draw Methods Go Here
     */

    /**
     *
     * @param g2d
     */
    public void paint(Graphics2D g2d) {
	if (!this.visible) {
	    return;
	}
	/*
	 * Draw the background & Border
	 */
	g2d.setColor(this.style.getColor("background"));
	g2d.fillRoundRect(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height, this.bounds.width / 10, this.bounds.height / 10);
	g2d.setColor(this.style.getColor("border"));
	g2d.drawRoundRect(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height, this.bounds.width / 10, this.bounds.height / 10);

	/*
	 * Vars!
	 */
	int w = g2d.getFontMetrics().getMaxAdvance();
	int h = g2d.getFontMetrics().getAscent();
	int x = this.bounds.x + this.bounds.width / 50;
	int y = this.bounds.y + this.header.height;

	/*
	 * Draw the Title.
	 */
	g2d.setColor(this.style.getColor("title"));
	g2d.setFont(this.style.getFont("title"));

	g2d.drawString(title, this.bounds.x + this.header.width / 50, this.bounds.y + (this.header.height - h) / 2 + h);

	/*
	 * Draw a title separator.
	 */
	g2d.setColor(this.style.getColor("border"));
	g2d.drawLine(this.bounds.x, y, (int) this.bounds.getMaxX(), y);

	/*
	 * Draw menu elements
	 */
	for (JMenuElementX e : elements) {
	    y += separator;
	    e.draw(g2d, style, x, y);
	    y += e.getBounds().height;
	}
    }

    /**
     *
     * @param g2d
     */
    public void paintBounds(Graphics2D g2d) {
	if (!this.visible) {
	    return;
	}
	/*
	 * Draw the background & Border
	 */
	g2d.setColor(this.style.getColor("border"));
	g2d.draw(bounds);

	/*
	 * Vars!
	 */
	int x = this.bounds.x + this.bounds.width / 50;
	int y = this.bounds.y + this.header.height + this.separator;

	/*
	 * Draw menu elements
	 */
	for (JMenuElementX e : elements) {
	    e.drawBounds(g2d, style, x, y);
	    y += e.getBounds().height + separator;
	}
    }
}
