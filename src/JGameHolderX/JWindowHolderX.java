/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JGameHolderX;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author  RlonRyan
 * @name    JWindowX
 * @date    Jan 10, 2014
 **/

public class JWindowHolderX implements JGameHolderX{

    public static void main(String[] args) {
        
    }

    @Override
    public Graphics2D getGraphics() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public final void clearBackbuffer() {
        Color prev = g2d.getColor();
        g2d.setPaint(this.backgroundcolor);
        g2d.fillRect(0, 0, backbuffer.getWidth(), backbuffer.getHeight());
        g2d.setColor(prev);
    }

    @Override
    public void flip() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resize(int winw, int winh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
