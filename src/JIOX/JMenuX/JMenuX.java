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
import JIOX.JMenuX.JMenuElementX.JMenuTextElement;
import java.awt.Graphics2D;
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
    public final static short MENU_CHANGED = 0;
    public final static short ELEMENT_HIGHLIGHTED = 1;
    public final static short ELEMENT_SELECTED = 2;
    
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
    private int [] dimensions;
    private int [] position;

    /*
    *   Constructors Go Here
    */
    public JMenuX(String title, int x, int y, int width, int height, String... elements) {
        this.title = title;
        this.position = new int[] {x,y};
        this.dimensions = new int[] {width, height};
        for(String e : elements) {
            this.elements.add(new JMenuTextElement(e, new JStyleX()));
        }
    }
    
    public JMenuX(String title, int x, int y, int width, int height, JMenuElementX... elements) {
        this.title = title;
        this.position = new int[] {x,y};
        this.dimensions = new int[] {width, height};
        this.elements.addAll(Arrays.asList(elements));
    }
    
    /*
    *   Setters Go Here
    */
    
    synchronized public final void setStyle(JStyleX style) {
        this.style = style;
    }
    
    synchronized public final void setStyleElement(String name, Object element) {
        this.setStyleElement(name, element);
    }
    
    /*
    *   Getters Go Here
    */
    final public String getTitle() {
        return this.title;
    }

    synchronized public final JMenuElementX getMenuElement(int element) {
        if (element < 0 || element > 0) {
            return null;
        }
        return this.elements.get(element);
    }
    
    /*
    *   Incrementers Go Here
    */
    synchronized public final void incrementHighlight() {
        this.index = (this.index >= elements.size()) ? 0 : this.index + 1;
        fireEvent(ELEMENT_HIGHLIGHTED, index);
    }

    synchronized public final void deincrementHighlight() {
        this.index = (this.index <= 0) ? this.elements.size() - 1 : this.index - 1;
        fireEvent(ELEMENT_HIGHLIGHTED, index);
    }

    synchronized public final void incrementHighlight(int increment) {
        this.index = (this.index + increment) % (this.elements.size());
        if (this.index < 0) {
            this.index = this.index + this.elements.size();
        }
        fireEvent(ELEMENT_HIGHLIGHTED, this.index);
    }

    /*
    *   Methods Go Here
    */
    
    synchronized public final void selectMenuElement() {
        fireEvent(ELEMENT_SELECTED, this.index);
    }

    synchronized public final void selectMenuElement(int index) {
        fireEvent(ELEMENT_SELECTED, index);
    }
    
    synchronized public final void highlight(int index) {
        this.index = index;
        fireEvent(ELEMENT_HIGHLIGHTED, index);
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
        int width = this.dimensions[0] / 50;
        
        /*
        *   Draw the Title.
        */
        g2d.setColor(this.style.getColor("title"));
        g2d.setFont(this.style.getFont("title"));
        
        y += g2d.getFontMetrics().getHeight();
        x += g2d.getFontMetrics().getHeight();
        
        g2d.drawString(title, x, y);
        
        y += g2d.getFontMetrics().getHeight();
        x += g2d.getFontMetrics().getHeight();
        
        /*
        *   Draw a title separator.
        */
        g2d.setColor(this.style.getColor("background"));
        g2d.drawLine(this.position[0], y, this.position[0] + this.dimensions[0], y);
        
        /*
        *   Draw menu elements
        */
        for (JMenuElementX e : elements) {
            e.draw(g2d, x, y, this.dimensions[0] - (2 * x));
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

    synchronized public void fireEvent(int type, int... data) {
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
