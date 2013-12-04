
package JBasicX;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;

/**
 * @author  RlonRyan
 * @name    JStyleX
 * @date    Dec 4, 2013
 **/

public class JStyleX {
    
    private HashMap<String, Object> style;
    
    public Color getColor(String name) {
        if(this.style.containsKey(name) && this.style.get(name) != null && this.style.get(name) instanceof Color) {
            return (Color) this.style.get(name);
        }
        else {
            return (Color) this.style.get("default_color");
        }
    }
    
    public Font getFont(String name) {
        if(this.style.containsKey(name) && this.style.get(name) != null && this.style.get(name) instanceof Font) {
            return (Font) this.style.get(name);
        }
        else {
            return (Font) this.style.get("default_font");
        }
    }
    
    public void setFont(String name, Font font) {
        this.style.put(name, font);
    }
    
    public void setDefaultFont(Font font) {
        this.style.put("default_font", font);
    }
    
    public void setColor(String name, Color color) {
        this.style.put(name, color);
    }
    
    public void setDefaultColor(Color color) {
        this.style.put("default_coltor", color);
    }
    
    public void setStyle(HashMap style) {
        this.style = style;
    }
    
    /*@Deprecated
    public void validateStyle() {
        if (this.style == null) {
            this.style = new HashMap<>();
        }
        if (!this.style.containsKey("background") || this.style.get("background") == null) {
            this.style.put("background", new Color(255, 255, 255, 100));
        }
        if (!this.style.containsKey("border") || this.style.get("background") == null) {
            this.style.put("border", Color.WHITE);
        }
        if (!this.style.containsKey("title") || this.style.get("title") == null) {
            this.style.put("title", Color.WHITE);
        }
        if (!this.style.containsKey("body") || this.style.get("body") == null) {
            this.style.put("body", Color.LIGHT_GRAY);
        }
        if (!this.style.containsKey("highlight") || this.style.get("highlight") == null) {
            this.style.put("highlight", Color.YELLOW);
        }
        if (this.style == null) {
            this.style = new HashMap<>();
        }
        if (!this.style.containsKey("title") || this.style.get("title") == null) {
            this.style.put("title", new Font("Monospaced", Font.PLAIN, 16));
        }
        if (!this.style.containsKey("body") || this.style.get("body") == null) {
            this.style.put("body", new Font("SansSerif", Font.PLAIN, 10));
        }
        if (!this.style.containsKey("highlight") || this.style.get("highlight") == null) {
            this.style.put("highlight", new Font("Arial", Font.PLAIN, 10));
        }
    }*/

    public JStyleX() {
        this.style = new HashMap<>();
        this.style.put("default_font", Font.SANS_SERIF);
        this.style.put("default_colot", Color.WHITE);
    }
    
    public JStyleX(Font font, Color color) {
        this.style = new HashMap<>();
        this.style.put("default_font", font);
        this.style.put("default_colot", color);
    }
    
}
