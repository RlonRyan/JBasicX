/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JGameHolderX;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JFrame;

/**
 * @author  RlonRyan
 * @name    JWindowX
 * @date    Jan 10, 2014
 **/

public class JWindowHolderX implements JGameHolderX{

    JFrame frame;
    Graphics2D g2d;
    Image buffer;
    
    @Override
    public Graphics2D getGraphics() {
        return g2d;
    }

    @Override
    public final void clearBackbuffer() {
        Color prev = g2d.getColor();
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());
        g2d.setColor(prev);
    }

    @Override
    public void flip() {
        this.frame.getGraphics().drawImage(buffer, 0, 0, null);
    }

    @Override
    public void resize(int winw, int winh) {
        this.frame.setSize(winw, winh);
        this.frame.setVisible(true);
        this.g2d = (Graphics2D)this.frame.createImage(winh, winh).getGraphics();
    }

    public JWindowHolderX(String title,int winw, int winh) {
        this.frame = new JFrame(title);
        this.frame.setSize(winw, winh);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.createBufferStrategy(1);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.buffer = this.frame.createImage(winh, winh);
        this.g2d = (Graphics2D)this.frame.createImage(winh, winh).getGraphics();
    }
    
}
