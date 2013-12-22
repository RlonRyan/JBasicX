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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author RlonRyan
 * @name JMenuX
 */
public class JMenuX {

    /*
     *   Constants for events
     */
    public enum JMenuStateX {
        MENU_CHANGED, ELEMENT_HIGHLIGHTED, ELEMENT_SELECTED;
    }

    /*
     *   Menu Properties
     */
    private final String title;
    private JStyleX style;
    private List<JMenuElementX> elements;
    private List<JMenuListenerX> listeners;

    /*
     *   Menu Properties
     */
    private int index;
    private int[] dimensions;
    private int[] position;

    /*
     *   Constructors Go Here
     */
    public JMenuX(String title, int x, int y, int width, int height, String... elements) {
        this.title = title;
        this.position = new int[]{x, y};
        this.dimensions = new int[]{width, height};
        this.elements = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.style = new JStyleX();
        this.validateStyle();
        for (String e : elements) {
            this.elements.add(new JMenuTextElementX(e, this.style));
        }
        this.index = this.elements.size();
    }

    public JMenuX(String title, int x, int y, int width, int height, JMenuElementX... elements) {
        this.title = title;
        this.position = new int[]{x, y};
        this.dimensions = new int[]{width, height};
        this.elements = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.style = new JStyleX();
        this.validateStyle();
        this.elements.addAll(Arrays.asList(elements));
        this.index = this.elements.size();
    }

    /*
     *   Setters Go Here
     */
    synchronized public final void setStyle(JStyleX style) {
        this.style = style;
    }

    synchronized public final void setStyleElement(String name, Object element) {
        this.style.setStyleElement(name, element);
    }

    /*
     *   Getters Go Here
     */
    final public String getTitle() {
        return this.title;
    }

    synchronized public final JMenuElementX getMenuElement(int idex) {
        if (index < 0 || index >= this.elements.size()) {
            return null;
        }
        return this.elements.get(index);
    }

    /*
     *   Incrementers Go Here
     */
    synchronized public final void incrementHighlight() {
        /*
         *  Is this lazy? Or is this just maintainablity?
         *
         *   OLD:
         *
         *       this.index = (this.index >= elements.size()) ? 0 : this.index + 1;
         if (this.index < this.elements.size()) {
         this.elements.get(index).highlight();
         }
         fireEvent(ELEMENT_HIGHLIGHTED, index);
         *
         *   NEW:
         */
        this.incrementHighlight(1);
    }

    synchronized public final void deincrementHighlight() {
        /*
         *  This may or may not be bad for the stack.
         */
        this.incrementHighlight(-1);
    }

    synchronized public final void incrementHighlight(int increment) {
        /*
         *   Remove previous highlight.
         */
        if (this.index < this.elements.size()) {
            this.elements.get(index).highlight();
        }

        /*
         *  Increment the index.
         */
        this.index = (this.index + increment) % (this.elements.size());
        if (this.index < 0) {
            this.index = this.index + this.elements.size();
        }

        /*
         *  Highlight the new element... if it exists... dun. Dun. DUN!
         */
        if (this.index < this.elements.size()) {
            this.elements.get(index).highlight();
        }
        fireEvent(JMenuStateX.ELEMENT_HIGHLIGHTED, this.index);
    }

    /*
     *   Methods Go Here
     */
    synchronized public final void selectMenuElement(int index) {
        this.index = index;
        selectMenuElement();
    }

    synchronized public final void selectMenuElement() {
        if (this.index < this.elements.size()) {
            this.elements.get(index).select();
        }
        fireEvent(JMenuStateX.ELEMENT_SELECTED, this.index);
    }

    synchronized public final void highlight(int index) {
        this.index = index;
    }

    synchronized public final void highlight() {
        /*
         *  Check to see if the element is real... it may not be!
         */
        if (this.index < this.elements.size()) {
            this.elements.get(index).highlight();
            fireEvent(JMenuStateX.ELEMENT_HIGHLIGHTED, this.index);
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

    /*
     *   Draw Methods Go Here
     */
    public void draw(Graphics2D g2d) {
        /*
         *   Draw the background & Border
         */
        g2d.setColor(this.style.getColor("background"));
        g2d.fillRoundRect(this.position[0], this.position[1], this.dimensions[0], this.dimensions[1], this.dimensions[0] / 10, this.dimensions[1] / 10);
        g2d.setColor(this.style.getColor("border"));
        g2d.drawRoundRect(this.position[0], this.position[1], this.dimensions[0], this.dimensions[1], this.dimensions[0] / 10, this.dimensions[1] / 10);

        /*
         *   Intitialize the variables for dynamic placement
         */
        int x = this.position[0] + this.dimensions[0] / 100;
        int y = this.position[1] + this.dimensions[1] / 100;
        int width = this.dimensions[0] - (this.dimensions[0] / 50);

        /*
         *   Draw the Title.
         */
        g2d.setColor(this.style.getColor("title"));
        g2d.setFont(this.style.getFont("title"));

        y += g2d.getFontMetrics().getHeight();
        x += g2d.getFontMetrics().getHeight();
        width = width - (g2d.getFontMetrics().getHeight() * 2);

        g2d.drawString(title, x, y);

        y += g2d.getFontMetrics().getHeight();
        x += g2d.getFontMetrics().getHeight();
        width = width - (g2d.getFontMetrics().getHeight() * 2);

        /*
         *   Draw a title separator.
         */
        g2d.setColor(this.style.getColor("border"));
        g2d.drawLine(this.position[0], y, this.position[0] + this.dimensions[0], y);

        /*
         *   Draw menu elements
         */
        for (JMenuElementX e : elements) {
            e.draw(g2d, x, y, width);
            y += g2d.getFontMetrics().getHeight();
        }
    }

    /*
     *   Event Methods Go Way Down Here
     *   Likely will be deprecated or removed, as the elements themselves will get their own events.
     */
    synchronized public final void addEventListener(JMenuListenerX listener) {
        listeners.add(listener);
    }

    synchronized public final void removeEventListener(JMenuListenerX listener) {
        listeners.remove(listener);
    }

    synchronized public void fireEvent(JMenuStateX type, int... data) {
        switch (type) {
            case MENU_CHANGED:
                break;
            case ELEMENT_SELECTED:
                for (JMenuListenerX e : this.listeners) {
                    e.elementSelected(this, data);
                }
                break;
            case ELEMENT_HIGHLIGHTED:
                for (JMenuListenerX e : this.listeners) {
                    e.elementHighlighted(this, data);
                }
                break;
            default:
                // No Event For You!
                break;
        }
    }
}
