
package JMathX;

/**
 *
 * @author Ryan
 */
public class JVector2DX {

    /**
     *
     */
    public double x;

    /**
     *
     */
    public double y;

    /**
     *
     */
    public JVector2DX() {
	this(0, 0);
    }

    /**
     *
     * @param vect
     */
    public JVector2DX(JVector2DX vect) {
	this(vect.x, vect.y);
    }

    /**
     *
     * @param magnitude
     * @param direction
     */
    public JVector2DX(double magnitude, int direction){
	this(magnitude * Math.cos(Math.toRadians(direction)), magnitude * Math.sin(Math.toRadians(direction)));
    }

    /**
     *
     * @param x
     * @param y
     */
    public JVector2DX(double x, double y) {
	this.x = x;
	this.y = y;
    }

    /**
     *
     * @return
     */
    public double abs() {
	return Math.hypot(x, y);
    }

    /**
     *
     * @return
     */
    public double theta() {
	return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     *
     * @param vect
     * @return
     */
    public double dot(JVector2DX vect) {
	return this.x * vect.x + this.y * vect.y;
    }

    /**
     *
     * @param vec1
     * @param vec2
     * @return
     */
    public static JVector2DX cross(JVector2DX vec1, JVector2DX vec2) {
	return new JVector2DX(0, 0);
    }

}
