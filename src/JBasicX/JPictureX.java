//RlonRyan
//JBasicX JPictureX
package JBasicX;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Ryan
 */
public final class JPictureX extends Object {

    /**
     *
     */
    private Image picture;
    private JPoint2DX size = new JPoint2DX(0, 0);

    /**
     *
     * @return
     */
    public final JPoint2DX getSize() {
        return this.size;
    }

    /**
     *
     * @return
     */
    public final double getWidth() {
        return this.size.getX();
    }

    /**
     *
     * @return
     */
    public final double getHeight() {
        return this.size.getY();
    }

    /**
     *
     * @return
     */
    public final Image getPicture() {
        return this.picture;
    }

    /**
     *
     * @param filename
     */
    public final void setPicture(String filename) {
        if (filename == null) {
            filename = "JBasicX/nopic.png";
        }
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            ClassLoader cl = this.getClass().getClassLoader();
            this.picture = tk.getImage(cl.getResource(filename));
            while (this.size.getX() <= 0 || this.size.getY() <= 0) {
                this.updateSize();
            }
        }
        catch (Exception e) {
        }
    }

    /**
     *
     */
    public final void updateSize() {
        size.setX(picture.getWidth(null));
        size.setY(picture.getHeight(null));
    }

    /**
     *
     *
     */
    public JPictureX() {
        this.setPicture("JBasicX/nopic.png");
    }
}
