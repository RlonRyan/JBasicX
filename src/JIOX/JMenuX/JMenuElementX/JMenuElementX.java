
package JIOX.JMenuX.JMenuElementX;

import java.awt.Graphics2D;
import java.util.HashMap;

/**
 * @author RlonRyan
 * @name JMenuElementX
 * @version 1.0.0
 * @date Jan 9, 2012
 * @info Powered by JBasicX
 */
public interface JMenuElementX {
    public void validate();
    public void resize();
    public void applyStyle(HashMap<String, Object> style);
    public void highlight();
    public void select();
    public void draw(Graphics2D g2d);
}
