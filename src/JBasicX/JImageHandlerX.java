/**
 * @author RlonRyan
 * @name JImageHandlerX
 * @version 1.0.0
 * @date Dec 17, 2011
 * @info Image handler class.
 *
 */
package JBasicX;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @author RlonRyan
 * @name JImageHandlerX
 *
 */
public final class JImageHandlerX extends Object {

    /**
     *
     */
    private static Image defaultimage;
    //private HashMap<String, Image> images; ???
    private LinkedList<Image> images;
    private LinkedList<String> names;
    private Toolkit tk = Toolkit.getDefaultToolkit();
    private ClassLoader cl = this.getClass().getClassLoader();

    /**
     * Retrieves a previously imported image from the internal array based on
     * its
     * <code>name</code>
     * <p/>
     * @param name The name of the image to be retrieved from the internal
     *             array.
     * <p/>
     * @return The image held in memory under that name. If no image is found
     *         for that name, returns null.
     */
    synchronized public final Image getPicture(String name) {
        try {
            return this.images.get(this.names.indexOf(name));
        }
        catch (Exception e) {
            System.err.println("Could not find image under name: " + name + ".\nAttempting to autorecover.");
            List<Integer> matches = new ArrayList<Integer>();
            for (int i = 0; i < this.images.size(); i++) {
                if (this.names.get(i).toLowerCase().equals(name.toLowerCase())) {
                    System.err.println("Found lowercase match: " + this.names.get(i));
                    return this.images.get(i);
                }
                else if (this.names.get(i).toLowerCase().contains(name.toLowerCase())) {
                    System.err.println("Found possible match: " + this.names.get(i));
                    matches.add(i);
                }
            }
            if (matches.size() > 0) {
                System.err.println("Matches found. Returning first possible match: " + this.names.get(matches.get(0)) + ".");
                return this.images.get(matches.get(0));
            }
            else {
                System.err.println("Unable to autorecover image.\nDefaulting....");
                return defaultimage;
            }
        }
    }

    /**
     * Retrieves the default image from the internal array.
     * Defaults to
     * <code>"nopic.png"</code>
     * <p/>
     * @return Returns the default image.
     */
    synchronized public final Image getDefaultImage() {
        return defaultimage;
    }

    /**
     * Imports an image from the
     * <code>filename</code> location to the internal array.
     * The image's
     * <code>name</code> is set to match the
     * <code>filename</code>.
     * <p/>
     * @param filename The path to the file.
     */
    synchronized public final void addPicture(String filename) {
        this.addPicture(filename, filename);
    }

    /**
     * Imports an image from the
     * <code>filename</code> location to the internal array.
     * The image's
     * <code>name</code> is set to match the
     * <code>filename</code>.
     * <p/>
     * @param filename The path to the file.
     */
    synchronized public final void addPicture(URL filepath) {
        if(filepath == null) {
            System.err.println("Null URL. Aborting image load.");
            return;
        }
        try {
            this.images.add(ImageIO.read(filepath));
            /*this.images.add(tk.getImage(cl.getResource(filename)));
            this.names.add(name);*/
        }
        catch (Exception e) {
            System.err.println("Error loading image: " + filepath.getFile() + "...\nDefaulting...");
            this.loadDefaultImage();
            this.images.add(defaultimage);
            this.names.add(filepath.getFile());
        }
    }

    /**
     * Imports an image from the
     * <code>filename</code> location to the internal array.
     * <p/>
     * @param filename The path to the file.
     * @param name     The name the retrieved image is to be referenced under.
     */
    synchronized public final void addPicture(String filename, String name) {
        if (filename == null && name == null) {
            System.err.println("Null image name and filepath. Aborting image load...");
        }
        else if (filename == null) {
            this.images.add(defaultimage);
            this.names.add(name);
            return;
        }
        try {
            //this.images.add(ImageIO.read(new URL(filename)));
            this.images.add(tk.getImage(cl.getResource(filename)));
            this.names.add(name);
        }
        catch (Exception e) {
            System.err.println("Error loading image: " + filename + "...\nDefaulting...");
            this.loadDefaultImage();
            this.images.add(defaultimage);
            this.names.add(name);
        }
    }

    /**
     * Loads the default image.
     * Sets the default image to
     * <code>nopic.png</code>.
     */
    synchronized public final void loadDefaultImage() {
        if (defaultimage == null) {
            try {
                defaultimage = tk.getImage(cl.getResource("JBasicX/nopic.png"));
                System.out.println("Default image loaded.");
            }
            catch (Exception e) {
                System.err.println("Error loading default image...");
            }
        }
    }

    /**
     * Initializes the image handler.
     * Sets the default image to
     * <code>nopic.png</code>.
     */
    public JImageHandlerX() {
        this.tk = Toolkit.getDefaultToolkit();
        this.cl = this.getClass().getClassLoader();
        this.images = new LinkedList<Image>();
        this.names = new LinkedList<String>();
        this.loadDefaultImage();
    }
}
