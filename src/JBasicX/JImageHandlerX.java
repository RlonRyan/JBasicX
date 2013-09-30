/**
 * @author  RlonRyan
 * @name    JImageHandlerX
 * @version 1.0.0
 * @date    Dec 17, 2011
 * @info    Image handler class.
**/

package JBasicX;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;


/**
 *
 * @author RlonRyan
 */
public final class JImageHandlerX extends Object {

    /**
     *
     */
    private Image defaultimage;
    private LinkedList<Image> images;
    private LinkedList<String> names;
    private Toolkit tk = Toolkit.getDefaultToolkit();
    private ClassLoader cl = this.getClass().getClassLoader();

    /**
     * Retrieves a previously imported image from the internal array based on its <code>name</code>
     * @param name The name of the image to be retrieved from the internal array.
     * @return The image held in memory under that name. If no image is found for that name, returns null.
     */
    public final Image getPicture(String name) {
        return this.images.get(this.names.indexOf(name));
    }

    /**
     * Retrieves the default image from the internal array.
     * Defaults to <code>"nopic.png"</code>
     * @return Returns the default image.
     */
    public final Image getDefaultImage() {
        return defaultimage;
    }

    /**
     * Imports an image from the <code>filename</code> location to the internal array.
     * The image's <code>name</code> is set to match the <code>filename</code>.
     * @param filename The path to the file.
     */
    public final void addPicture(String filename) {
        if (filename == null) {
            this.images.add(this.defaultimage);
            this.names.add(filename);
        }
        try {
            this.images.add(tk.getImage(cl.getResource(filename)));
            this.names.add(filename);
        }
        catch (Exception e) {
        }
    }

    /**
     * Imports an image from the <code>filename</code> location to the internal array.
     * @param filename The path to the file.
     * @param name  The name the retrieved image is to be referenced under.
     */
    public final void addPicture(String filename, String name) {
        if (filename == null) {
            this.images.add(this.defaultimage);
            this.names.add(name);
        }
        try {
            this.images.add(tk.getImage(cl.getResource(filename)));
            this.names.add(name);
        }
        catch (Exception e) {
        }
    }

    /**
     * Initializes the image handler.
     * Sets the default image to <code>nopic.png</code>.
     */
    public JImageHandlerX() {
        tk = Toolkit.getDefaultToolkit();
        cl = this.getClass().getClassLoader();
        try {
            this.defaultimage = tk.getImage(cl.getResource("JBasicX/nopic.png"));
        }
        catch (Exception e) {
        }
    }
}
