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
import java.awt.Point;
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
    private final Rectangle header;

    /*
     * Menu Properties
     */
    private int index;
    private JStyleX style;
    private boolean visible;
    private int separator;

    private boolean drawBounds = false;

    /*
     * Constructors Go Here
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
	if (!this.elements.isEmpty()) {
	    this.separator = width / (10 * this.elements.size());
	}
	else {
	    this.separator = width / 10;
	}
    }

    /*
     * public JMenuX(String title, int x, int y, int width, int height,
     * JMenuElementX... elements) {
     * this.title = title;
     * this.bounds = new Rectangle(x, y, width, height);
     * this.elements = new ArrayList<>();
     * this.listeners = new ArrayList<>();
     * this.style = new JStyleX();
     * this.validateStyle();
     * this.elements.addAll(Arrays.asList(elements));
     * }
     */

    /*
     * Setters Go Here
     */
    synchronized public final void setBoundsVisable(boolean visable) {
	this.drawBounds = visable;
    }

    synchronized public final void setStyle(JStyleX style) {
	this.style = style;
    }

    synchronized public final void setStyleElement(String name, Object element) {
	this.style.setStyleElement(name, element);
    }

    /*
     * Putters Go Here
     */
    synchronized public final void addMenuElement(JMenuElementX element) {
	this.elements.add(element);
	this.separator = this.bounds.width / (10 * this.elements.size());
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

    synchronized public final void selectMenuElement(Point p) {
	int pos = this.bounds.y + this.header.height + this.separator;
	for (int i = 0; i < this.elements.size(); i++) {
	    if (new Rectangle(this.bounds.x, pos, elements.get(i).getBounds().width, elements.get(i).getBounds().height).contains(p)) {
		selectMenuElement(i);
		return;
	    }
	    pos += elements.get(i).getBounds().height + separator;
	}
    }

    synchronized public final void selectMenuElement(int index) {
	this.elements.get(this.index).dehighlight();
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

    public void normalize() {
	int width = (int) (0.80 * this.bounds.width);
	int height = (this.bounds.height) / (this.elements.size() + separator);
	for (JMenuElementX e : elements) {
	    if (e.getBounds().width == 0) {
		e.setWidth(width);
	    }
	    if (e.getBounds().height == 0) {
		e.setHeight(height);
	    }
	}
    }

    public void open() {
	if (this.elements.isEmpty()) {
	    this.elements.add(new JMenuTextElementX("Oops! This menu has yet to be filled!"));
	}
	this.normalize();
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
	    if (drawBounds) {
		e.drawBounds(g2d, style, x, y);
	    }
	    y += e.getBounds().height;
	}
	if (drawBounds) {
	    g2d.setColor(this.style.getColor("border"));
	    g2d.draw(bounds);
	}
    }
}
