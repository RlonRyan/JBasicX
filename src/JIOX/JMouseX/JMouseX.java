/**
 * @author RlonRyan
 * @name JSoundX
 * @version 1.0.0
 * @date Dec 17, 2011
 * @info Mouse handler class.
 *
 */
package JIOX.JMouseX;

import JEventX.JEventBinderX;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;

/**
 * @author RlonRyan
 * @name JMouseX
 */
public class JMouseX implements MouseListener, MouseMotionListener, MouseWheelListener {

    private JEventBinderX bindings;

    private Point click = new Point();
    private Point press = new Point();
    private Point release = new Point();
    private Point enter = new Point();
    private Point exit = new Point();
    private Point drag = new Point();
    private Point move = new Point();
    private Point position = new Point();

    private int scroll = 0;
    private Boolean mousedown = false;
    private Boolean mousedrag = false;
    private int mousebutton = 0;

    public final int leftbutton		= 1;
    public final int rightbutton	= 2;
    public final int centerbutton	= 3;

    /**
     *
     */
    public JMouseX() {
	//TODO: Something...
    }

    /**
     *
     * @return
     */
    public Point2D getClick() {
	return click;
    }

    /**
     *
     * @return
     */
    public Point2D getDrag() {
	return drag;
    }

    /**
     *
     * @return
     */
    public Point2D getEnter() {
	return enter;
    }

    /**
     *
     * @return
     */
    public Point2D getExit() {
	return exit;
    }

    /**
     *
     * @return
     */
    public Point2D getMove() {
	return move;
    }

    /**
     *
     * @return
     */
    public Point2D getPress() {
	return press;
    }

    /**
     *
     * @return
     */
    public Point2D getRelease() {
	return release;
    }

    /**
     *
     * @return
     */
    public Point2D getPosition() {
	return position;
    }
    
    public int getX() {
	return position.x;
    }
    
    public int getY() {
	return position.y;
    }

    /**
     *
     * @return
     */
    public int getMousebutton() {
	return mousebutton;
    }
    
        /**
     *
     * @return
     */
    public int getScroll() {
	return scroll;
    }
    
    /**
     *
     * @return
     */
    public Boolean isMousedown() {
	return mousedown;
    }

    /**
     *
     * @return
     */
    public Boolean isMousedrag() {
	return mousedrag;
    }
    
    /**
     *
     */
    public void clear() {
	click = new Point();
	press = new Point();
	release = new Point();
	enter = new Point();
	exit = new Point();
	drag = new Point();
	move = new Point();
	scroll = 0;
	mousedown = false;
	mousedrag = false;
	mousebutton = 0;
    }
    
    /**
     *
     */
    public void clearClick() {
	this.click = new Point();
    }

    /**
     *
     */
    public void clearPress() {
	this.press = new Point();
	this.release = new Point();
    }

    /**
     *
     */
    public void clearEnter() {
	this.enter = new Point();
	this.exit = new Point();
    }

    /**
     *
     */
    public void clearDrag() {
	this.drag = new Point();
    }

    /**
     *
     */
    public void clearMove() {
	this.move = new Point();
    }

    /**
     *
     */
    public void clearScroll() {
	this.scroll = 0;
    }
    
    public void setBindings(JEventBinderX bindings) {
	this.bindings = bindings;
    }

    private void checkButton(MouseEvent e) {
	switch (e.getButton()) {
	    case 1:
		mousebutton = leftbutton;
		break;
	    case 2:
		mousebutton = rightbutton;
		break;
	    case 3:
		mousebutton = centerbutton;
	    default:
		mousebutton = 0;
	}
	this.position = e.getPoint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	enter.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
	fireEvent(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	mousedrag = false;
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
	fireEvent(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	mousedrag = true;
	drag.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
	fireEvent(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
	scroll += e.getWheelRotation();
	checkButton(e);
	fireEvent(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	click.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
	fireEvent(e);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
	mousedown = true;
	press.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
	fireEvent(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	mousedown = false;
	release.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
	fireEvent(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
	exit.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
	fireEvent(e);
    }
    
    public void fireEvent(MouseEvent e) {
	if (bindings != null) {
	    this.bindings.fireEvent(e);
	}
    }

}
