/**
 * @author RlonRyan
 * @name JImageHandlerX
 * @version 1.0.0
 * @date Dec 17, 2011
 * @info Image handler class.
 *
 */
package JBasicX;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
    private static BufferedImage defaultimage;
    private final HashMap<String, BufferedImage> images;

    /**
     * Retrieves a previously imported image from the internal array based on
     * its <code>name</code>
     * <p/>
     * @param name The name of the image to be retrieved from the internal
     * array.
     * <p/>
     * @return The image held in memory under that name. If no image is found
     * for that name, returns null.
     */
    synchronized public final BufferedImage getImage(String name) {
        if (this.images.containsKey(name)) {
            return this.images.get(name);
        } else {
            System.out.printf("Image %1$ not found. Defaulting...", name);
            return defaultimage;
        }
    }

    /**
     * Retrieves the default image from the internal array. Defaults to
     * <code>"nopic.png"</code>
     * <p/>
     * @return Returns the default image.
     */
    synchronized public final BufferedImage getDefaultImage() {
        return defaultimage;
    }
    
    /**
     * Imports an image from the <code>filename</code> location to the internal
     * array.
     * <p/>
     * @param name The name the retrieved image is to be referenced under.
     */
    synchronized public final void addImage(String name) {
        if (name == null) {
            System.err.println("Null image name and filepath. Aborting image load...");
        } else {
            this.images.put(name, defaultimage);
        }
    }

    /**
     * Imports an image from the <code>filename</code> location to the internal
     * array.
     * <p/>
     * @param filename The path to the file.
     * @param name The name the retrieved image is to be referenced under.
     */
    synchronized public final void addImage(String name, String filename) {
        if (filename == null && name == null) {
            System.err.println("Null image name and filepath. Aborting image load...");
        } else if (filename == null) {
            this.images.put(name, defaultimage);
            return;
        } else {
            addImage(name, new File(filename));
        }
    }

    /**
     * Imports an image from the <code>filename</code> location to the internal
     * array. The image's <code>name</code> is set to match the
     * <code>filename</code>.
     * <p/>
     * @param name The name of the picture.
     * @param file The path to the file.
     */
    synchronized public final void addImage(String name, File file) {
        if (name == null) {
            System.err.println("Null image name and filepath. Aborting image load...");
        }
        try {
            this.images.put(name, ImageIO.read(file));
        } catch (IOException e) {
            System.err.println("Error loading image: " + name + "...\nDefaulting...");
            this.loadDefaultImage();
            this.images.put(name, defaultimage);
        }
    }

    /**
     * Loads the default image. Sets the default image to
     * <code>nopic.png</code>.
     */
    synchronized public final void loadDefaultImage() {
        if (defaultimage == null) {
            try {
                defaultimage = ImageIO.read(JImageHandlerX.class.getResourceAsStream("/Resources/nopic.png"));
            } catch (IOException e) {
                System.err.println("Error loading default image...");
            }
        }
    }

    /**
     * Initializes the image handler. Sets the default image to
     * <code>nopic.png</code>.
     */
    public JImageHandlerX() {
        this.images = new HashMap<>();
        this.loadDefaultImage();
    }
}
