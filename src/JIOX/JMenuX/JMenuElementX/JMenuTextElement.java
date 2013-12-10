/*
 *  Blah...
 */
package JIOX.JMenuX.JMenuElementX;

import JBasicX.JStyleX;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RlonRyan
 * @name JMenuTextElement
 * @date Dec 4, 2013
 *
 */
public class JMenuTextElement implements JMenuElementX {

    /*
     *   State Constants
     */
    public static short NO_STATE = 0;
    public static short NORMAL = 1;
    public static short HIGHLIGHTED = 2;
    public static short SELECTED = 3;

    /*
     *   Variable Variables
     */
    private String text;
    private JStyleX style;
    private short state;

    @Override
    public void validate() {
        throw new UnsupportedOperationException("Not supported yet. As far as I care, this element is valid.");
    }

    @Override
    public void resize() {
        throw new UnsupportedOperationException("Not supported yet. This element will not resize."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void applyStyle(JStyleX style) {
        this.style = style;
    }

    @Override
    public void highlight() {
        this.state = HIGHLIGHTED;
    }

    @Override
    public void select() {
        this.state = SELECTED;
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, int width) {
        for (String e : wrapToLines(this.text, g2d, width)) {
            y += g2d.getFontMetrics().getHeight();
            g2d.drawString(e, x, y);
        }
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
    
    /*
     *   Constructor
     */
    public JMenuTextElement(String text, JStyleX style) {
        this.text = text;
        this.style = style;
        this.state = 1;
    }
    
}
