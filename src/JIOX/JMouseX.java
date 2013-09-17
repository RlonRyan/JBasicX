/**
 * @author  RlonRyan
 * @name    JSoundX
 * @version 1.0.0
 * @date    Dec 17, 2011
 * @info    Mouse handler class.
**/

package JIOX;

import JBasicX.*;
import java.awt.event.*;

public class JMouseX implements MouseListener, MouseMotionListener, MouseWheelListener {

    private JInputOutputX user;
    private JPoint2DX click = new JPoint2DX();
    private JPoint2DX press = new JPoint2DX();
    private JPoint2DX release = new JPoint2DX();
    private JPoint2DX enter = new JPoint2DX();
    private JPoint2DX exit = new JPoint2DX();
    private JPoint2DX drag = new JPoint2DX();
    private JPoint2DX move = new JPoint2DX();
    private JPoint2DX position = new JPoint2DX();
    private int scroll = 0;
    private Boolean mousedown = false;
    private Boolean mousedrag = false;
    private int mousebutton = 0;
    public final int leftbutton = 1;
    public final int rightbutton = 2;
    public final int centerbutton = 3;

    public JMouseX(JInputOutputX user) {
        this.user = user;
    }

    public void clear() {
        click = new JPoint2DX();
        press = new JPoint2DX();
        release = new JPoint2DX();
        enter = new JPoint2DX();
        exit = new JPoint2DX();
        drag = new JPoint2DX();
        move = new JPoint2DX();
        scroll = 0;
        mousedown = false;
        mousedrag = false;
        mousebutton = 0;
    }

    public void clearClick() {
        click = new JPoint2DX();
    }

    public void clearPress() {
        press = new JPoint2DX();
        release = new JPoint2DX();
    }

    public void clearEnter() {
        enter = new JPoint2DX();
        exit = new JPoint2DX();
    }

    public void clearDrag() {
        drag = new JPoint2DX();
    }

    public void clearMove() {
        move = new JPoint2DX();
    }

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
        user.updateIO();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click.setPoint(e.getX(), e.getY());
        position.setPoint(e.getX(), e.getY());
        checkButton(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        enter.setPoint(e.getX(), e.getY());
        position.setPoint(e.getX(), e.getY());
        checkButton(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        exit.setPoint(e.getX(), e.getY());
        position.setPoint(e.getX(), e.getY());
        checkButton(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousedown = true;
        press.setPoint(e.getX(), e.getY());
        position.setPoint(e.getX(), e.getY());
        checkButton(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousedown = false;
        release.setPoint(e.getX(), e.getY());
        position.setPoint(e.getX(), e.getY());
        checkButton(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousedrag = true;
        drag.setPoint(e.getX(), e.getY());
        position.setPoint(e.getX(), e.getY());
        checkButton(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousedrag = false;
        position.setPoint(e.getX(), e.getY());
        checkButton(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll += e.getWheelRotation();
        checkButton(e);
    }

    public JPoint2DX getClick() {
        return click;
    }

    public JPoint2DX getDrag() {
        return drag;
    }

    public JPoint2DX getEnter() {
        return enter;
    }

    public JPoint2DX getExit() {
        return exit;
    }

    public JPoint2DX getMove() {
        return move;
    }

    public JPoint2DX getPress() {
        return press;
    }

    public JPoint2DX getRelease() {
        return release;
    }

    public JPoint2DX getPosition() {
        return position;
    }

    public int getMousebutton() {
        return mousebutton;
    }

    public Boolean isMousedown() {
        return mousedown;
    }

    public Boolean isMousedrag() {
        return mousedrag;
    }

    public int getScroll() {
        return scroll;
    }
}
