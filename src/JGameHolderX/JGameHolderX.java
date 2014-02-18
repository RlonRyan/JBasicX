/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JGameHolderX;

import JIOX.JKeyboardX;
import JIOX.JMouseX;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author RlonRyan
 */
public interface JGameHolderX {
    
    public Graphics2D getGraphics();
    
    public void resetGraphics();
    
    public void setBackgroundColor(Color color);
    
    public void clearBackbuffer();
    
    public void flip();
    
    public void resize(int winw, int winh);
    
    public void addKeyListener(JKeyboardX listener);
    
    public void addMouseListener(JMouseX listener);
    
}
