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
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author RlonRyan
 * @name JMenuX
 */
public class JMenuX2 {
    
    public final static int MENU_CHANGED = 0;
    public final static int ELEMENT_HIGHLIGHTED = 1;
    public final static int ELEMENT_SELECTED = 2;
    
    private final String title;
    private JStyleX style;
    private List<JMenuElementX> elements;
    private List<JMenuListenerX> listeners;
    
    private int index;
    private int [] dimensions;
    private int [] position;
    
    final public String getTitle() {
        return this.title;
    }

    synchronized public final JMenuElementX getMenuElement(int element) {
        if (element < 0 || element > 0) {
            return null;
        }
        return this.elements.get(element);
    }

    synchronized public final void setStyle(JStyleX style) {
        this.style = style;
    }
    
    synchronized public final void highlight(int index) {
        this.index = index;
        fireEvent(ELEMENT_HIGHLIGHTED, index);
    }

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
        fireEvent(ELEMENT_HIGHLIGHTED, index);
    }

    synchronized public final void selectMenuElement() {
        fireEvent(ELEMENT_SELECTED, index);
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
        int yy = position[1] + dimensions[1] / 100;
        int distance = Math.abs(y - yy);
        g2d.setFont(this.style.getFont("title"));
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

    public JMenuX2(int width, int height, int x, int y, String... elements) {
        this.dimensions = new Point(width, height);
        this.position = new Point(x, y);
        this.elements.addAll(Arrays.asList(elements));
        this.title = this.elements.remove(0);
        this.options = new String[this.elements.size()][10];
        this.validate();
    }

    public JMenuX2(Point dimensions, Point position, String[] elements) {
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
                    e.elementSelected(this, data);
                }
                break;
            case ELEMENT_HIGHLIGHTED:
                for (JMenuListenerX e : this.listeners) {
                    e.elementHighlighted(this, data);
                }
                break;
            default:
                break;
        }
    }
}
