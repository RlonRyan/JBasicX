/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JGameHolderX;

import JIOX.JMouseX.JMouseX;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;

/**
 *
 * @author Ryan
 */
public class JDummyGameHolderX implements JGameHolderX {

    private BufferedImage dummyScreen;
    private Graphics2D g2d;
    private Color background_color;
    private Color draw_color;
    private String title;
    private int framenum;
    private File folder;

    public JDummyGameHolderX(String title, int winw, int winh) {
        this.title = title;
        this.dummyScreen = new BufferedImage(winw, winh, BufferedImage.TYPE_INT_ARGB);
        this.g2d = (Graphics2D) this.dummyScreen.getGraphics();
        this.background_color = Color.BLACK;
        this.draw_color = Color.WHITE;
        this.framenum = 0;

        try {
            this.folder = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath(), "Output");
            if (this.folder.exists()) {
                this.folder.delete();
            }
            this.folder.mkdir();
        } catch (URISyntaxException e) {
            // Oh noes...
            this.folder = null;
        }
    }

    @Override
    public Graphics2D getGraphics() {
        return g2d;
    }

    @Override
    public void resetGraphics() {
        this.g2d.setTransform(new AffineTransform());
        this.g2d.setBackground(background_color);
        this.g2d.setColor(draw_color);
    }

    @Override
    public void setBackgroundColor(Color color) {
        this.background_color = color;
    }

    @Override
    public void clearBackbuffer() {
        this.resetGraphics();
        g2d.setPaint(background_color);
        g2d.fillRect(0, 0, this.dummyScreen.getWidth(), this.dummyScreen.getHeight());
    }

    @Override
    public void flip() {

        this.framenum++;

        String header = this.title + " Frame: " + this.framenum;

        int width = g2d.getFontMetrics().stringWidth(header);
        int height = g2d.getFontMetrics().getHeight();
        int xpos = (this.dummyScreen.getWidth() - width) / 2;

        this.g2d.setColor(new Color(100, 100, 100, 100));
        this.g2d.fillRoundRect(xpos - 5, -5, width + 10, height + 10, 5, 5);
        this.g2d.setColor(Color.WHITE);
        this.g2d.drawRoundRect(xpos - 5, -5, width + 10, height + 10, 5, 5);

        this.g2d.setColor(draw_color);
        this.g2d.drawString(header, xpos, height);

        // Where to?
        File file = new File(this.folder, this.title + "Frame" + this.framenum + ".png");

        try {
            ImageIO.write(dummyScreen, "PNG", file);
        } catch (IOException e) {
            // Oh well, It's probably the enviorment.
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public void resize(int winw, int winh) {
        this.dummyScreen = new BufferedImage(winw, winh, BufferedImage.TYPE_INT_ARGB);
        this.g2d = (Graphics2D) this.dummyScreen.getGraphics();
        this.g2d.setBackground(background_color);
        this.g2d.setColor(draw_color);
    }

    @Override
    public void addKeyListener(KeyListener listener) {
        // Pretend to do something here...
    }

    @Override
    public void addMouseListener(JMouseX listener) {
        // Pretend to do something here...
    }

}
