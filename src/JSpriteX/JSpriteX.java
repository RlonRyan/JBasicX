/**
 * @author RlonRyan
 * @name JSpriteX
 * @version 1.0.0
 * @date Dec 18, 2011
 * @info Sprite base class.
 *
 */
package JSpriteX;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author RlonRyan
 * @name JSpriteX
 *
 */
public abstract class JSpriteX {

    //Status
    protected boolean visable = true;
    protected boolean boundsvisable = false;
    protected int status = 0;
    protected int life = 100;
    protected int type = 0;
    //Velocity
    protected double vel = 0;
    //Acceleration
    protected double accel = 0;
    //Direction
    protected int direction = 0;
    //Rotation
    protected int rotation = 0;
    protected int rotationrate = 0;
    //Bounds
    protected Rectangle2D bounds = new Rectangle.Double();
    //Update Timer
    protected long lastupdate = 0;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Booleans
    /**
     *
     * @return
     */
    public final boolean isVisable() {
	return visable;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Getters
    /**
     *
     * @return
     */
    public final int getStatus() {
	return status;
    }

    /**
     *
     * @return
     */
    public int getType() {
	return type;
    }

    /**
     *
     * @return
     */
    public final int getLife() {
	return life;
    }

    /**
     *
     * @return
     */
    public final Point2D getPosition() {
	return new Point.Double(bounds.getX(), bounds.getY());
    }

    public final Rectangle2D getBounds() {
	return bounds;
    }

    /**
     *
     * @return
     */
    public final double getAccel() {
	return accel;
    }

    /**
     *
     * @return
     */
    public final double getVel() {
	return vel;
    }

    /**
     *
     * @return
     */
    public final int getDirection() {
	return direction;
    }

    /**
     *
     * @return
     */
    public final int getRotation() {
	return rotation;
    }

    /**
     *
     * @return
     */
    public final int getRotationrate() {
	return rotationrate;
    }

    /**
     *
     * @return
     */
    public final double getX() {
	return bounds.getX();
    }

    /**
     *
     * @return
     */
    public final double getY() {
	return bounds.getY();
    }

    /**
     *
     * @return
     */
    public final double getWidth() {
	return bounds.getWidth();
    }

    /**
     *
     * @return
     */
    public final double getHeight() {
	return bounds.getHeight();
    }
    
    /**
     *
     * @return
     */
    public final double getRadius() {
	return Math.sqrt((bounds.getWidth() * bounds.getWidth()) + (bounds.getHeight()* bounds.getHeight())) / 2;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Set
    /**
     *
     * @param visable
     */
    public final void setVisable(boolean visable) {
	this.visable = visable;
    }

    /**
     *
     */
    public final void hide() {
	this.visable = false;
    }

    /**
     *
     */
    public final void show() {
	this.visable = true;
    }

    /**
     *
     * @param status
     */
    public final void setStatus(int status) {
	this.status = status;
    }

    /**
     *
     * @param type
     */
    public void setType(int type) {
	this.type = type;
    }

    /**
     *
     * @param life
     */
    public final void setLife(int life) {
	this.life = life;
    }

    /**
     *
     * @param accel
     */
    public final void setAccel(double accel) {
	this.accel = accel;
    }

    /**
     *
     * @param vel
     */
    public final void setVel(double vel) {
	this.vel = vel;
    }

    /**
     *
     * @param direction
     */
    public final void setDirection(int direction) {
	direction = direction % 360;
	if (direction < 0) {
	    direction = direction + 360;
	}
	this.direction = direction;
    }

    /**
     *
     * @param rotation
     */
    public final void setRotation(int rotation) {
	this.rotation = rotation;
    }

    /**
     *
     * @param rotationrate
     */
    public final void setRotationrate(int rotationrate) {
	this.rotationrate = rotationrate;
    }

    /**
     *
     * @param position
     */
    public final void setPosition(Point2D pos) {
	this.bounds.setRect(pos.getX(), pos.getY(), this.bounds.getWidth(), this.bounds.getHeight());
    }

    /**
     *
     * @param x
     * @param y
     */
    public final void setPosition(double x, double y) {
	this.bounds.setRect(x, y, this.bounds.getWidth(), this.bounds.getHeight());
    }

    /**
     *
     * @param x
     */
    public final void setX(double x) {
	this.setPosition(x, this.bounds.getY());
    }

    /**
     *
     * @param y
     */
    public final void setY(double y) {
	this.setPosition(this.bounds.getX(), y);
    }

    public final void setSize(double width, double height) {
	this.bounds.setRect(this.bounds.getX(), this.bounds.getY(), width, height);
    }

    /**
     *
     * @param width
     */
    public final void setWidth(double width) {
	this.setSize(width, this.bounds.getHeight());
    }

    /**
     *
     * @param height
     */
    public final void setHeight(double height) {
	this.setSize(this.bounds.getWidth(), height);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Inc
    /**
     *
     * @param life
     */
    public final void incLife(int life) {
	this.life += life;
    }

    /**
     *
     * @param rotation
     */
    public final void incRot(int rotation) {
	this.rotation += rotation;
    }

    /**
     *
     * @param inc
     */
    public final void incRotRate(int inc) {
	this.rotationrate += inc;
    }

    /**
     *
     * @param inc
     */
    public final void incX(double inc) {
	this.setX(this.bounds.getX() + inc);
    }

    /**
     *
     * @param inc
     */
    public final void incY(double inc) {
	this.setY(this.bounds.getY() + inc);
    }

    /**
     *
     * @param vel
     */
    public final void incVel(double vel) {
	this.setVel(this.getVel() + vel);
    }

    /**
     *
     * @param accel
     */
    public final void incAccel(double accel) {
	this.setAccel(this.getAccel() + accel);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Auto

    public final void rot() {
	this.rotation = this.direction - 90;
    }

    public final void pause() {
	this.lastupdate = 0;
    }

    /**
     *
     */
    public final void update() {
	if (this.lastupdate == 0) {
	    this.lastupdate = System.currentTimeMillis();
	    return;
	}
	if (this.getVel() < 0 && this.accel < 0) {
	    this.setVel(0);
	}
	else {
	    this.incVel((this.accel / 1000) * (System.currentTimeMillis() - this.lastupdate));
	}
	this.incX(-(this.vel * Math.cos(Math.toRadians(direction)) / 1000) * (System.currentTimeMillis() - this.lastupdate));
	this.incY(-(this.vel * Math.sin(Math.toRadians(direction)) / 1000) * (System.currentTimeMillis() - this.lastupdate));
	this.incRot((int) ((((double)rotationrate) / 1000) * (System.currentTimeMillis() - this.lastupdate)));
	this.lastupdate = System.currentTimeMillis();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Scale
    // TBI
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Bounds
    /**
     *
     * @param sprite
     * <p/>
     * @return
     */
    public final Boolean collidesWith(JSpriteX sprite) {
	return (this != sprite) && (this.collidesWith(sprite.getBounds()));
    }

    /**
     *
     * @param rect
     * <p/>
     * @return
     */
    public final Boolean collidesWith(Rectangle2D rect) {
	return this.bounds.intersects(rect);
    }

    /**
     *
     * @param point
     * <p/>
     * @return
     */
    public final Boolean collidesWith(Point2D point) {
	return this.bounds.contains(point);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Draw
    /**
     *
     * @param g2d
     */
    public abstract void paint(Graphics2D g2d);
    
    /**
     *
     * @param g2d
     */
    public final void paintBounds(Graphics2D g2d) {
	g2d.setTransform(new AffineTransform());
	g2d.setColor(Color.WHITE);
	g2d.drawRect((int) this.bounds.getX(), (int) this.bounds.getY(), (int) this.bounds.getWidth(), (int) this.bounds.getHeight());
	g2d.setColor(Color.BLUE);
	double rad = getRadius();
	g2d.drawOval((int)(bounds.getCenterX() - rad), (int)(bounds.getCenterY() - rad), (int)(2 * rad), (int)(2 * rad));
	g2d.setColor(Color.GREEN);
	g2d.drawLine((int)this.bounds.getCenterX(), (int)this.bounds.getCenterY(), (int)(this.bounds.getCenterX() - this.vel * Math.cos(Math.toRadians(direction))), (int)(this.bounds.getCenterY() - this.vel * Math.sin(Math.toRadians(direction))));
	g2d.setColor(Color.RED);
	g2d.drawLine((int)this.bounds.getCenterX(), (int)this.bounds.getCenterY(), (int)(this.bounds.getCenterX() - this.accel * Math.cos(Math.toRadians(direction))), (int)(this.bounds.getCenterY() - this.accel * Math.sin(Math.toRadians(direction))));
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The Force, with you it is.
    /**
     *
     * @param force
     * @param direction
     */
    public final void applyForce(double force, double direction) {
	double f1x = force * Math.cos(Math.toRadians(direction));
	double f1y = force * Math.sin(Math.toRadians(direction));
	double f2x = this.vel * Math.cos(Math.toRadians(this.direction));
	double f2y = this.vel * Math.sin(Math.toRadians(this.direction));
	this.direction = (int)Math.toDegrees(Math.atan2(f1y + f2y, f1x + f2x));
	this.vel = Math.hypot(f1x + f2x, f1y + f2y);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  TEH END
}