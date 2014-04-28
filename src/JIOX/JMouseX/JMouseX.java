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
import JGameEngineX.JGameEngineX;
import JIOX.JInputOutputX;
import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RlonRyan
 * @name JMouseX
 */
public class JMouseX implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final JGameEngineX holder;
    private final List<JInputOutputX> listeners;
    
    private Point click = new Point();
    private Point press = new Point();
    private Point release = new Point();
    private Point enter = new Point();
    private Point exit = new Point();
    private Point drag = new Point();
    private Point move = new Point();
    private Point position = new Point();
    
    private JEventBinderX bindings = new JEventBinderX();
    private int scroll = 0;
    private Boolean mousedown = false;
    private Boolean mousedrag = false;
    private int mousebutton = 0;
    /**
     *
     */
    public final int leftbutton = 1;
    /**
     *
     */
    public final int rightbutton = 2;
    /**
     *
     */
    public final int centerbutton = 3;

    /**
     *
     * @param holder
     */
    public JMouseX(JGameEngineX holder) {
	this.holder = holder;
	this.bindings = new JEventBinderX();
	this.listeners = new ArrayList<>();
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
	click = new Point();
    }

    /**
     *
     */
    public void clearPress() {
	press = new Point();
	release = new Point();
    }

    /**
     *
     */
    public void clearEnter() {
	enter = new Point();
	exit = new Point();
    }

    /**
     *
     */
    public void clearDrag() {
	drag = new Point();
    }

    /**
     *
     */
    public void clearMove() {
	move = new Point();
    }

    /**
     *
     */
    public void clearScroll() {
	scroll = 0;
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
	
	fireEvent(e);
	
	for (JInputOutputX listener : listeners) {
	    listener.updateIO(e);
	}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	click.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	enter.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
	exit.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	for (JInputOutputX listener : listeners) {
	    listener.lostFocus(e);
	}
	checkButton(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	mousedown = true;
	press.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	mousedown = false;
	release.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	mousedrag = true;
	drag.setLocation(e.getX(), e.getY());
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	mousedrag = false;
	position.setLocation(e.getX(), e.getY());
	checkButton(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
	scroll += e.getWheelRotation();
	checkButton(e);
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
     * @return
     */
    public int getScroll() {
	return scroll;
    }

    /*
     * Event Methods Go Way Down Here
     * Likely will be deprecated or removed, as the elements themselves will get
     * their own events.
     */
    synchronized public final void addEventListener(JInputOutputX listener) {
	listeners.add(listener);
    }

    synchronized public final void removeEventListener(JInputOutputX listener) {
	listeners.remove(listener);
    }

    /*
     * Event Methods Go Way Down Here
     * Likely will be deprecated or removed, as the elements themselves will get
     * their own events.
     */
    synchronized public final void bind(String mode, int id, Method m, Object... args) {
	bindings.bind(mode, id, m, args);
    }

    synchronized public final void unbind(String mode, int id, Method m) {
	bindings.unbind(mode, id, m);
    }

    synchronized public void fireEvent(AWTEvent e) {
	bindings.fireEvent(this.holder.getGameMode(), e);
    }
}
