/**
 * @author RlonRyan
 * @name JPictureSpriteX
 * @version 1.0.0
 * @date Jan 11, 2012
 * @info Picture-based sprite class.
*
 */
package JSpriteX;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author RlonRyan
 * @name JPictureSpriteX
 */
public final class JPictureSpriteX extends JSpriteX {
    //vars

    private Image picture;
    private int currentframe = 0;
    private int totalframes = 0;
    private int columns = 0;
    private Rectangle frame = new Rectangle();
    //Constructors

    /**
     *
     * @param picture
     * @param x
     * @param y
     */
    public JPictureSpriteX(Image picture, double x, double y) {
        this.setPosition(x, y);
        this.setPicture(picture);
        this.setVisable(true);
        this.updateSize();
    }

    /**
     *
     * @param filename
     * @param x
     * @param y
     */
    public JPictureSpriteX(String filename, double x, double y) {
        this.setPosition(x, y);
        this.loadPicture(filename);
        this.setVisable(true);
        this.updateSize();
    }

    /**
     *
     * @param x
     * @param y
     */
    public JPictureSpriteX(double x, double y) {
        this.setPosition(x, y);
        this.loadPicture(null);
        this.setVisable(true);
        this.updateSize();
    }
    //Acessors

    /**
     *
     * @return
     */
    public final Image getImage() {
        return picture;
    }

    //Mutators
    /**
     *
     * @param picture
     */
    public final void setPicture(Image picture) {
        this.picture = picture;
    }

    /**
     *
     * @param filename
     */
    public final void loadPicture(String filename) {
        if (filename == null) {
            filename = "JBasicX/nopic.png";
        }
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            ClassLoader cl = this.getClass().getClassLoader();
            this.picture = tk.getImage(cl.getResource(filename));
        } catch (Exception e) {
            System.err.printf("Sprite %1$ had issuses loading image %2$.", this.toString(), filename);
        }
    }

    /**
     *
     * @param columns
     */
    public final void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     *
     * @param currentframe
     */
    public final void setCurrentframe(int currentframe) {
        this.currentframe = currentframe;
    }

    /**
     *
     * @param totalframes
     */
    public void setTotalframes(int totalframes) {
        this.totalframes = totalframes;
    }

    /**
     *
     * @param i
     */
    public final void incCurrentframe(int i) {
        this.currentframe += i;
        if (this.currentframe > this.totalframes) {
            this.currentframe = 0;
        } else if (this.currentframe < 0) {
            this.currentframe = this.totalframes;
        }
    }

    /**
     *
     */
    public final void updateSize() {
        if (this.columns != 0) {
            this.updateFrameSize();
            this.setSize(this.frame.width, this.frame.height);
        } else {
            while(this.picture.getWidth(null) == -1 || this.picture.getHeight(null) == -1) {
                System.out.println("A Sprite is waiting to measure it's image.");
            }
            this.setSize(this.picture.getWidth(null), this.picture.getHeight(null));
        }
    }

    /**
     *
     */
    public final void updateFrameSize() {
        this.frame.width = ((int)(this.picture.getWidth(null) / this.columns - 1));
        this.frame.height = ((int)(this.picture.getHeight(null) / (this.totalframes / this.columns) - 1));
        this.frame.x = (this.currentframe % this.columns) * (int) this.frame.width;
        this.frame.y = (this.currentframe / this.columns) * (int) this.frame.height;
    }

    /**
     *
     * @param g2d
     */
    @Override
    public final void draw(Graphics2D g2d) {
        if (!this.isVisable()) {
            return;
        }

        g2d.setTransform(new AffineTransform());
        g2d.translate(this.bounds.getX(), this.bounds.getY());
        g2d.rotate(this.rotation, this.bounds.getWidth() / 2, this.bounds.getHeight()/2);

        if (this.columns != 0) {
            this.updateFrameSize();
            BufferedImage tempimage = new BufferedImage((int) this.bounds.getX(), (int) this.bounds.getY(), BufferedImage.TRANSLUCENT);
            Graphics2D g2dc = tempimage.createGraphics();
            g2dc.drawImage(this.picture, 0, 0, (int) this.frame.getWidth(), (int) this.frame.getHeight(),
                    (int) this.frame.getX(), (int) this.frame.getY(), (int) this.frame.getX() + (int) this.frame.getWidth(), (int) this.frame.getY() + (int) this.frame.getHeight(), null);
            g2d.drawImage(tempimage, 0, 0, null);
        }
        else {
            g2d.drawImage(this.picture, 0, 0, null);
        }
    }
}
