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

/**
 *
 * @author RlonRyan
 */
public interface JGameHolderX {

    /**
     *
     * @return
     */
    public Graphics2D getGraphics();

    /**
     *
     */
    public void resetGraphics();

    /**
     *
     * @param color
     */
    public void setBackgroundColor(Color color);

    /**
     *
     */
    public void clearBackbuffer();

    /**
     *
     */
    public void flip();

    /**
     *
     * @param winw
     * @param winh
     */
    public void resize(int winw, int winh);

    /**
     *
     * @param listener
     */
    public void addKeyListener(KeyListener listener);

    /**
     *
     * @param listener
     */
    public void addMouseListener(JMouseX listener);
}
