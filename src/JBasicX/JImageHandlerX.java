/*
 * @author RlonRyan
 * @name JImageHandlerX
 * @version 1.0.0
 * @date Dec 17, 2011
 * @info Image handler class.
 *
 */
package JBasicX;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/*
 * @author RlonRyan
 * @name JImageHandlerX
 */

/**
 *
 * @author Ryan
 */

public final class JImageHandlerX extends Object {

    /**
     *
     */
    private static BufferedImage defaultimage;
    private final HashMap<String, BufferedImage> images;
    private final Class holder;

    /**
     * Retrieves a previously imported image from the internal array based on
     * its
     * <code>name</code>
     * <p>
     * @param name The name of the image to be retrieved from the internal
     *             array.
     * <p>
     * @return The image held in memory under that name. If no image is found
     *         for that name, returns null.
     */
    synchronized public final BufferedImage getImage(String name) {
	if (this.images.containsKey(name)) {
	    return this.images.get(name);
	}
	else {
	    System.out.println("Image " + name + " not found. Defaulting...");
	    return defaultimage;
	}
    }

    /**
     * Retrieves the default image from the internal array. Defaults to
     * <code>"nopic.png"</code>
     * <p>
     * @return Returns the default image.
     */
    synchronized public final BufferedImage getDefaultImage() {
	return defaultimage;
    }

    /**
     * Imports an image from the
     * <code>filename</code> location to the internal
     * array.
     * <p>
     * @param name The name the retrieved image is to be referenced under.
     * <p>
     * @see addImage
     */
    synchronized public final void addImage(String name) {
	this.addImage(name, null);
    }

    /**
     * Imports an image from the
     * <code>filename</code> location to the internal
     * array.
     * <p>
     * @param path The path to the file.
     * @param name     The name the retrieved image is to be referenced under.
     */
    synchronized public final void addImage(String name, String path) {
	if (path == null && name == null) {
	    System.err.println("The image was not added as both the filepath and name of the image were null.");
	}
	else if (path == null) {
	    System.err.printf("A null filepath was provided for the image: %1$s. The default image will be used instead.");
	    this.images.put(name, defaultimage);
	}
	else {
	    try {
		this.images.put(name, ImageIO.read(holder.getResource(path)));
	    }
	    catch (Exception e) {
		System.err.printf("A %1$s was thrown while attempting to load the image: \"%2$s\".\n", e.getClass().getSimpleName(), name);
		System.err.printf("\tThe error reports as follows:\n\t%1$s\n", e.getLocalizedMessage());
		System.err.printf("\tThe default image will be used instead.\n");
		this.images.put(name, defaultimage);
	    }
	}
    }

    /**
     * Loads the default image. Sets the default image to
     * <code>nopic.png</code>.
     */
    synchronized public final void loadDefaultImage() {
	while (defaultimage == null) {
	    try {
		defaultimage = ImageIO.read(JImageHandlerX.class.getResourceAsStream("/Resources/Default_Image.png"));
	    }
	    catch (IOException e) {
		System.err.println("Error loading default image...");
	    }
	}
    }

    /**
     * Initializes the image handler. Sets the default image to
     * <code>nopic.png</code>.
     * @param holder
     */
    public JImageHandlerX(Class holder) {
	this.images = new HashMap<>();
	this.loadDefaultImage();
	this.holder = holder;
    }
}
