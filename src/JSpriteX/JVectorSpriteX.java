/**
 * @author  RlonRyan
 * @name    JVectorSpriteX
 * @version 1.0.0
 * @date    Dec 18, 2011
 * @info    Vector sprite.
**/

package JSpriteX;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author  RlonRyan
 * @name    JVectorSpriteX
**/
public class JVectorSpriteX extends JSpriteX {

    //Variables
    private Shape shape;
    private Polygon poly;
    private int scale;
    private int[] xpoints;
    private int[] ypoints;
    private Color linecolor;
    private Color fillcolor;
    //Acessors

    /**
     *
     * @return
     */
    public final Shape getShape() {
        return this.shape;
    }

    /**
     *
     * @return
     */
    public final Polygon getPolygon() {
        return this.poly;
    }
    
    /**
     *
     * @return
     */
    public final int getScale() {
        return this.scale;
    }

    /**
     *
     * @return
     */
    public final int[] getXPoints() {
        return this.xpoints;
    }

    /**
     *
     * @return
     */
    public final int[] getYPoints() {
        return this.ypoints;
    }

    /**
     *
     * @return
     */
    public final Color getLineColor() {
        return this.linecolor;
    }

    /**
     *
     * @return
     */
    public final Color getFillColor() {
        return this.fillcolor;
    }
    //Mutators

    /**
     *
     * @param shape
     */
    public final void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     *
     * @param polygon
     */
    public final void setPolygon(Polygon polygon) {
        this.poly = polygon;
    }
    
    /**
     *
     * @param scale
     */
    public final void setPolygon(int scale) {
        this.scale = scale;
    }

    /**
     *
     * @param xpoints
     * @param ypoints
     */
    public final void setPoints(int[] xpoints, int[] ypoints) {
        this.xpoints = xpoints;
        this.ypoints = ypoints;
        this.setPolygon(new Polygon(xpoints, ypoints, xpoints.length));
        this.setSize((int) this.poly.getBounds2D().getWidth(), (int) this.poly.getBounds2D().getHeight());
    }

    /**
     *
     */
    public final void updatesize() {
        this.setSize((int) (this.poly.getBounds2D().getWidth() * this.scale), (int) (this.poly.getBounds2D().getHeight() * this.scale));
    }

    /**
     *
     * @param color
     */
    public final void setColors(Color color) {
        this.fillcolor = color;
        this.linecolor = color;
    }

    /**
     *
     * @param LineColor
     * @param FillColor
     */
    public final void setColors(Color LineColor, Color FillColor) {
        this.linecolor = LineColor;
        this.fillcolor = FillColor;
    }

    /**
     *
     * @param LineColor
     */
    public final void setLineColor(Color LineColor) {
        this.linecolor = LineColor;
    }

    /**
     *
     * @param FillColor
     */
    public final void setFillColor(Color FillColor) {
        this.fillcolor = FillColor;
    }
    //Functions

    /**
     *
     * @param g2d 
     */
    @Override
    public final void draw(Graphics2D g2d) {
        if (!this.visable) {
            return;
        }
        
        g2d.setTransform(new AffineTransform());
        g2d.translate(this.bounds.x, this.bounds.y);
        g2d.rotate(Math.toRadians(this.rotation));
        
        if (this.poly != null) {
            if (this.linecolor == null) {
                g2d.setColor(Color.red);
                g2d.drawPolygon(this.poly);
            }
            else {
                g2d.setColor(this.linecolor);
                g2d.drawPolygon(this.poly);
            }
            if (this.fillcolor == null) {
                g2d.setColor(Color.pink);
                g2d.fillPolygon(this.poly);
            }
            else {
                g2d.setColor(this.fillcolor);
                g2d.fillPolygon(this.poly);
            }
        }
        else if (this.shape != null) {
            if (this.linecolor == null) {
                g2d.setColor(Color.red);
                g2d.draw(this.shape);
            }
            else {
                g2d.setColor(this.linecolor);
                g2d.draw(this.shape);
            }
            if (this.fillcolor == null) {
                g2d.setColor(Color.pink);
                g2d.draw(this.shape);
            }
            else {
                g2d.setColor(this.fillcolor);
                g2d.draw(this.shape);
            }
        }
    }

    /**
     *
     */
    public JVectorSpriteX() {
        this.setColors(null);
        this.setShape(null);
        this.setPolygon(null);
        this.scale = 1;
    }
}
