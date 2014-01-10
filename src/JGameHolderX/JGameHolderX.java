/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JGameHolderX;

import java.awt.Graphics2D;

/**
 *
 * @author RlonRyan
 */
public interface JGameHolderX {
    
    public Graphics2D getGraphics();
    
    public void clearBackbuffer();
    
    public void flip();
    
    public void resize(int winw, int winh);
}
