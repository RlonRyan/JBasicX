/**
 * @author RlonRyan
 * @name JSoundX
 * @version 1.0.0
 * @date Dec 17, 2011
 * @info Input-Output interface.
 *
 */
package JIOX.JMenuX;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author RlonRyan
 * @name JMenuX
 */
public class JMenuX {

    public final static int MENU_CHANGED = 0;
    public final static int ELEMENT_HIGHLIGHTED = 1;
    public final static int ELEMENT_SELECTED = 2;
    private List<JMenuListenerX> listeners = new ArrayList<>();
    private String title;
    private List<String> elements = new ArrayList<>();
    private String[][] options;
    private Point dimensions;
    private Point position;
    private HashMap<String, Color> colorscheme;
    private HashMap<String, Font> fontscheme;
    private int highlighted = 0;

    final public String getTitle() {
        return this.title;
    }

    synchronized public final String getMenuElement(int element) {
        if (element < 0 || element > 0) {
            return null;
        }
        return this.elements.get(element);
    }

    synchronized public final void setColorScheme(HashMap<String, Color> colorscheme) {
        this.colorscheme = colorscheme;
        this.validate();
    }

    synchronized public final void setColorSchemeElement(String key, Color value) {
        this.colorscheme.put(key, value);
        this.validate();
    }

    synchronized public final void setFontScheme(HashMap<String, Font> fontscheme) {
        this.fontscheme = fontscheme;
        this.validate();
    }

    synchronized public final void setFontSchemeElement(String key, Color value) {
        this.colorscheme.put(key, value);
        this.validate();
    }

    synchronized public final void validate() {
        /*
         * GraphicsEnvironment ge =
         * GraphicsEnvironment.getLocalGraphicsEnvironment();
         * Font[] fonts = ge.getAllFonts();
         * for (Font f : fonts) {
         * System.out.println(f.getFontName());
         * }
         */
        if (this.colorscheme == null) {
            this.colorscheme = new HashMap<>();
        }
        if (!this.colorscheme.containsKey("background") || this.colorscheme.get("background") == null) {
            this.colorscheme.put("background", new Color(255, 255, 255, 100));
        }
        if (!this.colorscheme.containsKey("border") || this.colorscheme.get("background") == null) {
            this.colorscheme.put("border", Color.WHITE);
        }
        if (!this.colorscheme.containsKey("title") || this.colorscheme.get("title") == null) {
            this.colorscheme.put("title", Color.WHITE);
        }
        if (!this.colorscheme.containsKey("body") || this.colorscheme.get("body") == null) {
            this.colorscheme.put("body", Color.LIGHT_GRAY);
        }
        if (!this.colorscheme.containsKey("highlight") || this.colorscheme.get("highlight") == null) {
            this.colorscheme.put("highlight", Color.YELLOW);
        }
        if (this.fontscheme == null) {
            this.fontscheme = new HashMap<>();
        }
        if (!this.fontscheme.containsKey("title") || this.fontscheme.get("title") == null) {
            this.fontscheme.put("title", new Font("Monospaced", Font.PLAIN, 16));
        }
        if (!this.fontscheme.containsKey("body") || this.fontscheme.get("body") == null) {
            this.fontscheme.put("body", new Font("SansSerif", Font.PLAIN, 10));
        }
        if (!this.fontscheme.containsKey("highlight") || this.fontscheme.get("highlight") == null) {
            this.fontscheme.put("highlight", new Font("Arial", Font.PLAIN, 10));
        }
    }

    synchronized public final void highlight(int element) {
        this.highlighted = element;
        fireEvent(ELEMENT_HIGHLIGHTED, highlighted);
    }

    synchronized public final void incrementHighlight() {
        this.highlighted += 1;
        if (this.highlighted >= elements.size()) {
            this.highlighted = 0;
        }
        fireEvent(ELEMENT_HIGHLIGHTED, highlighted);
    }

    synchronized public final void deincrementHighlight() {
        this.highlighted -= 1;
        if (this.highlighted < 0) {
            this.highlighted = this.elements.size() - 1;
        }
        fireEvent(ELEMENT_HIGHLIGHTED, highlighted);
    }

    synchronized public final void incrementHighlight(int increment) {
        this.highlighted += increment;
        this.highlighted %= (this.elements.size());
        if (this.highlighted < 0) {
            this.highlighted += this.elements.size();
        }
        fireEvent(ELEMENT_HIGHLIGHTED, highlighted);
    }

    synchronized public final void selectMenuElement() {
        fireEvent(ELEMENT_SELECTED, highlighted);
    }

    synchronized public final void selectMenuElement(int element) {
        fireEvent(ELEMENT_SELECTED, element);
    }

    synchronized public final void highlightNearest(Graphics2D g2d, int y) {
        this.highlight(getNearest(g2d, y));
    }

    synchronized public final void selectNearest(Graphics2D g2d, int y) {
        this.selectMenuElement(getNearest(g2d, y));
    }

    synchronized public final int getNearest(Graphics2D g2d, int y) {
        Font temp = g2d.getFont();
        int yy = position.y + dimensions.y / 100;
        int distance = Math.abs(y - yy);
        g2d.setFont(fontscheme.get("title"));
        yy += g2d.getFontMetrics().getHeight() * 2;
        int i;
        g2d.setFont(fontscheme.get("body"));
        for (i = 0; i < elements.size(); i++) {
            if (distance >= Math.abs(y - yy) || i == 0) {
                distance = Math.abs(y - yy);
            } else {
                g2d.setFont(temp);
                return i - 1;
            }
            if (i == highlighted) {
                g2d.setFont(fontscheme.get("highlight"));
                yy += g2d.getFontMetrics().getHeight() * wrapToLines(elements.get(i), g2d, dimensions.x).size();
                g2d.setFont(fontscheme.get("body"));
            } else {
                yy += g2d.getFontMetrics().getHeight() * wrapToLines(elements.get(i), g2d, dimensions.x).size();
            }
        }
        g2d.setFont(temp);
        return this.elements.size() - 1;
    }

    public static final List<String> wrapToLines(String line, Graphics2D context, int length) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < context.getFontMetrics().stringWidth(line); i += length) {
            int temp = length;
            if (line.length() < length + i) {
                temp = line.length();
            }
            lines.add(line.substring(i, i + temp));
        }
        return lines;
    }

    synchronized public final void wrap(Graphics2D context) {
        for (int i = 0; i < elements.size(); i++) {
            String line = this.elements.get(i);
            int length = context.getFontMetrics().stringWidth(line);
            for (int ii = 0; ii < context.getFontMetrics().stringWidth(line); ii += this.dimensions.x) {
                if (length < line.length() + ii) {
                    length = this.dimensions.x + 2 - ii;
                }
                options[i][ii] = (line.substring(ii, ii + length - 1));
            }
        }
    }

    public JMenuX(int width, int height, int x, int y, String... elements) {
        this.dimensions = new Point(width, height);
        this.position = new Point(x, y);
        this.elements.addAll(Arrays.asList(elements));
        this.title = this.elements.remove(0);
        this.options = new String[this.elements.size()][10];
        this.validate();
    }

    public JMenuX(Point dimensions, Point position, String[] elements) {
        this.dimensions = dimensions;
        this.position = position;
        this.elements.addAll(Arrays.asList(elements));
        this.title = this.elements.remove(0);
        this.options = new String[this.elements.size()][10];
        this.validate();
    }

    synchronized public void draw(Graphics2D g2d) {
        g2d.setColor(colorscheme.get("background"));
        g2d.fillRoundRect(position.x, position.y, dimensions.x, dimensions.y, dimensions.x / 10, dimensions.y / 10);
        g2d.setColor(colorscheme.get("border"));
        g2d.drawRoundRect(position.x, position.y, dimensions.x, dimensions.y, dimensions.x / 10, dimensions.y / 10);

        int x = position.x + dimensions.x / 100;
        int y = position.y + dimensions.y / 100;

        g2d.setColor(colorscheme.get("title"));
        g2d.setFont(fontscheme.get("title"));
        y += g2d.getFontMetrics().getHeight();
        x += g2d.getFontMetrics().getHeight();
        g2d.drawString(title, x, y);

        y += g2d.getFontMetrics().getHeight();
        x += g2d.getFontMetrics().getHeight();

        g2d.setColor(colorscheme.get("body"));
        g2d.setFont(fontscheme.get("body"));
        for (int i = 0; i < elements.size(); i++) {
            if (i == highlighted) {
                g2d.setColor(colorscheme.get("highlight"));
                g2d.setFont(fontscheme.get("highlight"));
                for (String e : wrapToLines(elements.get(i), g2d, dimensions.x - 2 * (dimensions.x / 100))) {
                    y += g2d.getFontMetrics().getHeight();
                    g2d.drawString(e, x, y);
                }
                g2d.setColor(colorscheme.get("body"));
                g2d.setFont(fontscheme.get("body"));
            } else {
                for (String e : wrapToLines(elements.get(i), g2d, dimensions.x - (2 * (dimensions.x / 100)))) {
                    y += g2d.getFontMetrics().getHeight();
                    g2d.drawString(e, x, y);
                }
            }
        }
    }

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
                    e.elementSelected(this, data[0]);
                }
                break;
            case ELEMENT_HIGHLIGHTED:
                for (JMenuListenerX e : this.listeners) {
                    e.elementHighlighted(this, data[0]);
                }
                break;
            default:
                break;
        }
    }
}
