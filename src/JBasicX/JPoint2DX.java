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
     *
     */
    public JPoint2DX() {
        this.x = 0;
        this.y = 0;
    }

    /**
     *
     * @param x
     * @param y
     */
    public JPoint2DX(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x
     * @param y
     */
    public JPoint2DX(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x
     * @param y
     */
    public JPoint2DX(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param point
     */
    public void setPoint(JPoint2DX point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    /**
     *
     * @param point
     */
    public void setPoint(Point2D point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    /**
     *
     * @param x
     * @param y
     */
    public void incPoint(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void incPoint(float x, float y) {
        this.x += x;
        this.y += y;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void incPoint(int x, int y) {
        this.x += x;
        this.y += y;
    }

    /**
     *
     * @param point
     */
    public void incPoint(JPoint2DX point) {
        this.x += point.getX();
        this.y += point.getY();
    }

    /**
     *
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     *
     * @param x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     *
     * @param y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @param x
     */
    public void incX(double x) {
        this.x += x;
    }

    /**
     *
     * @param x
     */
    public void incX(float x) {
        this.x += x;
    }

    /**
     *
     * @param x
     */
    public void incX(int x) {
        this.x += x;
    }

    /**
     *
     * @param y
     */
    public void incY(double y) {
        this.y += y;
    }

    /**
     *
     * @param y
     */
    public void incY(float y) {
        this.y += y;
    }

    /**
     *
     * @param y
     */
    public void incY(int y) {
        this.y += y;
    }

    /**
     *
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ") ";
    }
}
