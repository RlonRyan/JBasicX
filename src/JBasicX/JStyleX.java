
package JBasicX;

import java.awt.Color;
import java.awt.Font;
//import java.awt.GraphicsEnvironment;
import java.util.HashMap;

/*
 * @author  RlonRyan
 * @name    JStyleX
 * @date    Dec 4, 2013
 */

/**
 *
 * @author Ryan
 */


public class JStyleX {
    
    private HashMap<String, Object> style;
    
    /**
     *
     * @param name
     * @return
     */
    public Color getColor(String name) {
        if(this.style.containsKey(name) && this.style.get(name) != null && this.style.get(name) instanceof Color) {
            return (Color) this.style.get(name);
        }
        else {
            return (Color) this.style.get("default_color");
        }
    }
    
    /**
     *
     * @param name
     * @return
     */
    public Font getFont(String name) {
        if(this.style.containsKey(name) && this.style.get(name) != null && this.style.get(name) instanceof Font) {
            return (Font) this.style.get(name);
        }
        else {
            return (Font) this.style.get("default_font");
        }
    }
    
    /**
     *
     * @param name
     * @param font
     */
    public void setFont(String name, Font font) {
        this.style.put(name, font);
    }
    
    /**
     *
     * @param font
     */
    public void setDefaultFont(Font font) {
        this.style.put("default_font", font);
    }
    
    /**
     *
     * @param name
     * @param color
     */
    public void setColor(String name, Color color) {
        this.style.put(name, color);
    }
    
    /**
     *
     * @param color
     */
    public void setDefaultColor(Color color) {
        this.style.put("default_color", color);
    }
    
    /**
     *
     * @param style
     */
    public void setStyle(HashMap style) {
        this.style = style;
    }
    
    /**
     *
     * @param name
     * @param element
     */
    public void setStyleElement(String name, Object element) {
        this.style.put(name, element);
    }
    
    /**
     *
     * @param name
     * @return
     */
    public boolean hasStyleElement(String name) {
        return this.style.containsKey(name);
    }

    /**
     *
     */
    public JStyleX() {
        this.style = new HashMap<>();
        this.style.put("default_font", Font.getFont(Font.SANS_SERIF));
        this.style.put("default_colot", Color.WHITE);
    }
    
    /**
     *
     * @param font
     * @param color
     */
    public JStyleX(Font font, Color color) {
        this.style = new HashMap<>();
        this.style.put("default_font", font);
        this.style.put("default_colot", color);
    }
    
}
