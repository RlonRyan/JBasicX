/**
 * @author  RlonRyan
 * @name    JPoint2DX
 * @version 1.0.0
 * @date    Dec 17, 2011
 * @info    Point class. Deprecated.
**/

package JBasicX;

import java.awt.geom.Point2D;

/**
 * @deprecated
 * @author  RlonRyan
 * @name    JPoint2DX 
**/
@Deprecated
public class JPoint2DX {

    private double x;
    private double y;

    /**
     * Creates a point with the coordinates (0,0).
     */
    public JPoint2DX() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Creates a point with the coordinates (<code>x</code>, <code>y</code>).
     * @param x The x-coordinate relative to the origin.
     * @param y The y-coordinate relative to the origin.
     */
    public JPoint2DX(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the point to the coordinates (<code>x</code>, <code>y</code>).
     * @param x The x-coordinate relative to the origin.
     * @param y The y-coordinate relative to the origin.
     */
    public void setPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the point to another point.
     * @param point The coordinates to be set to.
     */
    public void setPoint(JPoint2DX point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    /**
     * Sets the point to another point.
     * @param point The coordinates to be set to.
     */
    public void setPoint(Point2D point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    /**
     * Translates the point by the vector (<code>x</code>, <code>y</code>).
     * @param x Distance to be translated in the x-dimension.
     * @param y Distance to be translated in the y-dimension.
     */
    public void incPoint(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Translates the point by the vector represented by another point.
     * @param point The vector in the xy-plane to translate the point by.
     */
    public void incPoint(JPoint2DX point) {
        this.x += point.getX();
        this.y += point.getY();
    }

    /**
     * Sets the point's x-coordinate.
     * @param x The x coordinate of the point relative to the origin.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the point's y-coordinate.
     * @param y The y coordinate of the point relative to the origin.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Translates the point's x-coordinate by the specified amount.
     * @param x The distance in the x-dimension to translate the point.
     */
    public void incX(double x) {
        this.x += x;
    }

    /**
     * Translates the point's y-coordinate by the specified amount.
     * @param y The distance in the y-dimension to translate the point.
     */
    public void incY(double y) {
        this.y += y;
    }

    /**
     * Returns the point's x-coordinate relative to the origin.
     * @return The point's x-coordinates relative to the origin.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the point's y-coordinate relative to the origin.
     * @return The point's y-coordinates relative to the origin.
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the point as a string in the format "(x,y)".
     * @return Returns the point as a string.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ") ";
    }
}
