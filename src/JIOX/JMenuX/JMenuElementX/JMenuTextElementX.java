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
 * @name JMenuTextElementX
 * @date Dec 4, 2013
 *
 */
public class JMenuTextElementX implements JMenuElementX {

    /*
     *   Variable Variables
     */
    private String text;
    private JStyleX style;
    private JMenuElementStateX state;

    @Override
    public JMenuElementStateX getState() {
        return state;
    }

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
        this.state = (this.state == JMenuElementStateX.HIGHLIGHTED) ? JMenuElementStateX.NORMAL:JMenuElementStateX.HIGHLIGHTED;
    }

    @Override
    public void select() {
        this.state = (this.state == JMenuElementStateX.SELECTED) ? JMenuElementStateX.NORMAL:JMenuElementStateX.SELECTED;
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, int width) {
        switch (state) {
            case NORMAL:
                g2d.setColor(this.style.getColor("body"));
                g2d.setFont(this.style.getFont("body"));
                break;
            case HIGHLIGHTED:
                g2d.setColor(this.style.getColor("highlight"));
                g2d.setFont(this.style.getFont("highlight"));
                break;
            default:
                g2d.setColor(this.style.getColor("body"));
                g2d.setFont(this.style.getFont("body"));
                break;
        }
        g2d.setColor(this.style.getColor(text));
        for (String e : wrapToLines(this.text, g2d, width)) {
            y += g2d.getFontMetrics().getHeight();
            g2d.drawString(e, x, y);
        }
    }

    public static final List<String> wrapToLines(String line, Graphics2D context, int length) {
        List<String> lines = new ArrayList<>();
        length = length / context.getFontMetrics().getMaxAdvance();
        for (int i = 0; i < line.length(); i += length) {
            if (line.length() < (i + length)) {
                lines.add(line.substring(i));
            } else {
                lines.add(line.substring(i, i + length));
            }
        }
        return lines;
    }

    /*
     *   Constructor
     */
    public JMenuTextElementX(String text, JStyleX style) {
        this.text = text;
        this.style = style;
        this.state = JMenuElementStateX.NORMAL;
    }

}
