/**
 * @author RlonRyan
 * @name JSoundX
 * @version 1.0.0
 * @date Dec 17, 2011
 * @info Input-Output interface.
 *
 */
package JIOX;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
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

    private List<String> elements = new ArrayList<String>();
    private Point dimensions;
    private Point position;
    private HashMap<String, Color> colorscheme;
    private HashMap<String, Font> fontscheme;
    private int highlighted = 0;

    public void draw(Graphics2D g2d) {
        g2d.drawRoundRect(position.x, position.y, dimensions.x, dimensions.y, dimensions.x / 10, dimensions.y / 10);
        g2d.setColor(new Color(255,255,255,50));
        g2d.fillRoundRect(position.x, position.y, dimensions.x, dimensions.y, dimensions.x / 10, dimensions.y / 10);

        int x = position.x + dimensions.x / 100;
        int y = position.y + dimensions.y / 100;

        g2d.setColor(colorscheme.get("title"));
        g2d.setFont(fontscheme.get("title"));
        y += g2d.getFontMetrics().getHeight();
        g2d.drawString(elements.get(0), x, y);

        x += dimensions.x / 100;
        y += dimensions.y / 100;

        g2d.setColor(colorscheme.get("body"));
        g2d.setFont(fontscheme.get("body"));
        for (int i = 1; i < elements.size(); i++) {
            if (i == highlighted) {
                g2d.setColor(colorscheme.get("highlight"));
                g2d.setFont(fontscheme.get("highlight"));
                y += dimensions.y / 1000 + g2d.getFontMetrics().getHeight();
                g2d.drawString(elements.get(i), x, y);
                g2d.setColor(colorscheme.get("body"));
                g2d.setFont(fontscheme.get("body"));
            }
            else {
                y += dimensions.y / 1000 + g2d.getFontMetrics().getHeight();
                g2d.drawString(elements.get(i), x, y);
            }
        }
    }

    public void validate() {
        /*
         * GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         * Font[] fonts = ge.getAllFonts();
         * for (Font f : fonts) {
         * System.out.println(f.getFontName());
         * }
         */
        if (this.colorscheme == null) {
            this.colorscheme = new HashMap<String, Color>();
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
            this.fontscheme = new HashMap<String, Font>();
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

    public JMenuX(int width, int height, int x, int y, String... elements) {
        this.dimensions = new Point(width, height);
        this.position = new Point(x, y);
        this.elements.addAll(Arrays.asList(elements));
        this.validate();
    }

    public JMenuX(Point dimensions, Point position, String[] elements) {
        this.dimensions = dimensions;
        this.position = position;
        this.elements.addAll(Arrays.asList(elements));
        this.validate();
    }
}
