/**
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
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RlonRyan
 * @name JMenuX
 */
public class JMenuX {

    /*
     * Constants for events
     */
    public enum JMenuStateX {

	MENU_CHANGED, ELEMENT_HIGHLIGHTED, ELEMENT_SELECTED;
    }

    /*
     * Menu Properties
     */
    private final String title;
    private final List<JMenuElementX> elements;
    private final Rectangle bounds;

    /*
     * Menu Properties
     */
    private int index;
    private JStyleX style;
    private boolean visible;

    /*
     * Constructors Go Here
     */
    public JMenuX(String title, int x, int y, int width, int height, String... elements) {
	this.title = title;
	this.bounds = new Rectangle(x, y, width, height);
	this.elements = new ArrayList<>();
	this.style = new JStyleX();
	this.validateStyle();
	for (String e : elements) {
	    this.elements.add(new JMenuTextElementX(e));
	}
    }

    /*public JMenuX(String title, int x, int y, int width, int height, JMenuElementX... elements) {
	this.title = title;
	this.bounds = new Rectangle(x, y, width, height);
	this.elements = new ArrayList<>();
	this.listeners = new ArrayList<>();
	this.style = new JStyleX();
	this.validateStyle();
	this.elements.addAll(Arrays.asList(elements));
    }*/

    /*
     * Setters Go Here
     */
    synchronized public final void setStyle(JStyleX style) {
	this.style = style;
    }

    synchronized public final void setStyleElement(String name, Object element) {
	this.style.setStyleElement(name, element);
    }
    
    /*
     * Putters Go Here
     */
    synchronized public final void addMenuElement(JMenuElementX element){
	this.elements.add(element);
    }
    
    /*
     * Getters Go Here
     */
    final public String getTitle() {
	return this.title;
    }

    synchronized public final int getIndex() {
	return this.index;
    }

    synchronized public final JMenuElementX getMenuElement(int idex) {
	if (index < 0 || index >= this.elements.size()) {
	    return null;
	}
	return this.elements.get(index);
    }

    /*
     * Incrementers Go Here
     */
    synchronized public final void incrementHighlight() {
	this.incrementHighlight(1);
    }

    synchronized public final void deincrementHighlight() {
	this.incrementHighlight(-1);
    }

    synchronized public final void incrementHighlight(int increment) {
	highlight(this.index + increment);
    }

    /*
     * Methods Go Here
     */
    synchronized public final void highlight(int index) {
	this.elements.get(this.index).dehighlight();
	this.index = index % this.elements.size();
	if (this.index < 0) {
	    this.index += this.elements.size();
	}
	this.elements.get(this.index).highlight();
    }

    synchronized public final void selectMenuElement(int index) {
	this.index = index;
	selectMenuElement();
    }

    synchronized public final void selectMenuElement() {
	if (this.index < this.elements.size()) {
	    this.elements.get(index).select();
	}
    }

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

    public void open() {
	if(this.elements.isEmpty()) {
	    this.elements.add(new JMenuTextElementX("Oops! This menu has yet to be filled!"));
	}
	this.highlight(0);
	this.visible = true;
    }

    public void close() {
	this.highlight(0);
	this.visible = false;
    }
    /*
     * Draw Methods Go Here
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
	 * Intitialize the variables for dynamic placement
	 */
	int x = this.bounds.x + this.bounds.width / 100;
	int y = this.bounds.y + this.bounds.height / 100;

	/*
	 * Draw the Title.
	 */
	g2d.setColor(this.style.getColor("title"));
	g2d.setFont(this.style.getFont("title"));

	x += g2d.getFontMetrics().getAscent();
	y += g2d.getFontMetrics().getAscent() + this.bounds.height / 100;

	g2d.drawString(title, x, y);

	x += g2d.getFontMetrics().getAscent();
	y += g2d.getFontMetrics().getAscent();

	/*
	 * Draw a title separator.
	 */
	g2d.setColor(this.style.getColor("border"));
	g2d.drawLine(this.bounds.x, y, this.bounds.x + this.bounds.width, y);

	y += g2d.getFontMetrics().getHeight();

	/*
	 * Draw menu elements
	 */
	for (JMenuElementX e : elements) {
	    e.draw(g2d, style, x, y, this.bounds.width - 2 * (x - this.bounds.x));
	    y += g2d.getFontMetrics().getHeight();
	}
    }
}
