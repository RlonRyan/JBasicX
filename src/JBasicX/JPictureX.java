/**
 * @author  RlonRyan
 * @name    JPictureX
 * @version 1.0.0
 * @date    Dec 17, 2011
 * @info    Image class.
**/

package JBasicX;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * @author  RlonRyan
 * @name    JPictureX
**/
public final class JPictureX extends Object {

    /**
     *
     */
    private Image picture;
    private JPoint2DX size = new JPoint2DX(0, 0);

    /**
     *
     * @return The <code>size<code> dimension of the image.
     */
    public final JPoint2DX getSize() {
        return this.size;
    }

    /**
     *
     * @return The width of the image.
     */
    public final double getWidth() {
        return this.size.getX();
    }

    /**
     *
     * @return The height of the image.
     */
    public final double getHeight() {
        return this.size.getY();
    }

    /**
     *
     * @return The internal <code>Image</code> object.
     */
    public final Image getPicture() {
        return this.picture;
    }

    /**
     *
     * @param filename The location to load the internal <code>Image</code> from.
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
     * Updates the <code>size</code> to the picture's size.
     */
    public final void updateSize() {
        size.setX(picture.getWidth(null));
        size.setY(picture.getHeight(null));
    }

    /**
     * Initializes the picture using the default <code>"JBasicX/nopic.png"</code> file.
     */
    public JPictureX() {
        this.setPicture("JBasicX/nopic.png");
    }
}
