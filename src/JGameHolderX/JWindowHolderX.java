/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JGameHolderX;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * @author  RlonRyan
 * @name    JWindowX
 * @date    Jan 10, 2014
 **/

public class JWindowHolderX extends JPanel implements JGameHolderX{

    Graphics2D g2d;
    BufferedImage buffer;
    
    @Override
    public Graphics2D getGraphics() {
        return g2d;
    }

    @Override
    public final void clearBackbuffer() {
        Color prev = g2d.getColor();
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        g2d.setColor(prev);
    }

    @Override
    public void flip() {
        this.getGraphics().drawImage(buffer, 0, 0, null);
    }

    @Override
    public void resize(int winw, int winh) {
        super.resize(winh, winh);
        this.buffer = new BufferedImage(winh, winh, BufferedImage.TYPE_INT_ARGB);
        this.g2d = this.buffer.createGraphics();
        super.setVisible(true);
    }

    public JWindowHolderX(int winw, int winh) {
        super.setSize(winh, winh);
        this.buffer = new BufferedImage(winh, winh, BufferedImage.TYPE_INT_ARGB);
        this.g2d = this.buffer.createGraphics();
    }
    
}
