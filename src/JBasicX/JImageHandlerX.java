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
     *
     * @return
     */
    public final Image getPicture(String name) {
        return this.images.get(this.names.indexOf(name));
    }

    public final Image getDefaultImage() {
        return defaultimage;
    }

    /**
     *
     * @param filename
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
     *
     * @param filename
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
