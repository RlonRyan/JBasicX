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
import javax.swing.JApplet;

/**
 *
 * @author RlonRyan
 */
public class JAppletHolderX implements JGameHolderX {

    JApplet frame;
    Graphics2D g2d;
    BufferedImage buffer;
    Color background_color;
    Color draw_color;

    @Override
    public Graphics2D getGraphics() {
	return g2d;
    }

    @Override
    public final void clearBackbuffer() {
	g2d.setPaint(background_color);
	g2d.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());
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
    public void flip() {
	this.frame.getGraphics().drawImage(this.buffer, 0, 0, this.frame);
    }

    @Override
    public void resize(int winw, int winh) {
	this.frame.setSize(winw, winh);
	this.frame.setVisible(true);
	this.buffer = new BufferedImage(winw, winh, BufferedImage.TYPE_INT_ARGB);
	this.g2d = (Graphics2D) this.buffer.getGraphics();
	this.g2d.setBackground(background_color);
	this.g2d.setColor(draw_color);
    }

    public JAppletHolderX(int winw, int winh) {
	this.frame = new JApplet();
	this.frame.setSize(winw, winh);
	this.frame.setVisible(true);
	this.buffer = new BufferedImage(winw, winh, BufferedImage.TYPE_INT_ARGB);
	this.g2d = (Graphics2D) this.buffer.getGraphics();
	this.background_color = Color.BLACK;
	this.draw_color = Color.WHITE;
    }

    @Override
    public void addKeyListener(KeyListener listener) {
	this.frame.addKeyListener(listener);
    }

    @Override
    public void addMouseListener(JMouseX listener) {
	this.frame.addMouseListener(listener);
	this.frame.addMouseMotionListener(listener);
	this.frame.addMouseWheelListener(listener);
    }
}
