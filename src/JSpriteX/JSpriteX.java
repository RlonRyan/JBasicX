/**
 * @author  RlonRyan
 * @name    JSpriteX
 * @version 1.0.0
 * @date    Dec 18, 2011
 * @info    Sprite base class.
**/

package JSpriteX;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import static java.lang.Math.*;

/**
 * @author  RlonRyan
 * @name    JSpriteX
**/
public abstract class JSpriteX {

    //Status
    protected boolean visable = true;
    protected boolean boundsvisable = false;
    protected int status = 0;
    protected int life = 100;
    protected int type = 0;
    //Position
    protected Point2D position = new Point.Double();
    //Velocity
    protected double vel = 0;
    //Acceleration
    protected double accel = 0;
    //Direction
    protected int direction = 0;
    //Rotation
    protected double rotation = 0;
    protected double rotationrate = 0;
    //Bounds
    protected Rectangle bounds = new Rectangle();
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
        return position;
    }

    public final Rectangle getBounds() {
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
    public final double getRotation() {
        return rotation;
    }

    /**
     *
     * @return
     */
    public final double getRotationrate() {
        return rotationrate;
    }

    /**
     *
     * @return
     */
    public final Rectangle getSize() {
        return bounds;
    }

    /**
     *
     * @return
     */
    public final double getX() {
        return position.getX();
    }

    /**
     *
     * @return
     */
    public final double getY() {
        return position.getY();
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
        else if (direction >= 360) {
            direction = direction - 360;
        }
        this.direction = direction;
    }

    /**
     *
     * @param rotation
     */
    public final void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     *
     * @param rotationrate
     */
    public final void setRotationrate(double rotationrate) {
        this.rotationrate = rotationrate;
    }

    /**
     *
     * @param position
     */
    public final void setPosition(Point.Double position) {
        this.position = position;
    }

    /**
     *
     * @param x
     * @param y
     */
    public final void setPosition(double x, double y) {
        this.position.setLocation(x, y);
    }

    /**
     *
     * @param x
     */
    public final void setX(double x) {
        this.position.setLocation(x, this.position.getY());
    }

     /**
     *
     * @param y
     */
    public final void setY(int y) {
        this.position.setLocation(this.position.getX(), y);
    }

    public final void setSize(int width, int height) {
        this.bounds.setSize(width, height);
    }

    public final void setSize(double width, double height) {
        this.bounds.setSize((int)width, (int)height);
    }

    /**
     *
     * @param width
     */
    public final void setWidth(int width) {
        this.bounds.width = width;
    }

     /**
     *
     * @param height
     */
    public final void setHeight(int height) {
        this.bounds.height = height;
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
    public final void incRot(double rotation) {
        this.rotation += rotation;
    }

    /**
     *
     * @param inc
     */
    public final void incRotRate(double inc) {
        this.rotationrate += inc;
    }

    /**
     *
     * @param x
     */
    public final void incX(double x) {
        this.setX(this.position.getX() + x);
    }

    /**
     *
     * @param y
     */
    public final void incY(double y) {
        this.setX(this.position.getY() + y);
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
        if (this.getVel() > 0) {
            this.incVel((this.accel / 1000) * (System.currentTimeMillis() - this.lastupdate));
        }
        else {
            this.setVel(0);
        }
        this.incX((int)(((this.vel * (Math.cos(this.direction * Math.PI / 180))) / 1000) * (System.currentTimeMillis() - this.lastupdate)));
        this.incY((int)(((this.vel * (Math.sin(this.direction * Math.PI / 180))) / 1000) * (System.currentTimeMillis() - this.lastupdate)));
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
     * @return
     */
    public final Boolean collidesWith(JSpriteX sprite) {
        return (this != sprite) && (this.collidesWith(sprite.getBounds()));
    }

    /**
     *
     * @param rect
     * @return
     */
    public final Boolean collidesWith(Rectangle rect) {
        return this.bounds.intersects(rect);
    }

    /**
     *
     * @param point
     * @return
     */
    public final Boolean collidesWith(Point2D point) {
        return this.bounds.contains(point);
    }

    /**
     *
     * @param g2d
     */
    public final void drawBoundsTo(Graphics2D g2d) {
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.WHITE);
        g2d.drawRect((int)this.position.getX(), (int)this.position.getY(), bounds.width, bounds.height);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Draw
    /**
     *
     * @param g2d
     */
    public abstract void draw(Graphics2D g2d);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The Force, with you it is.
    /**
     *
     * @param force
     * @param direction
     */
    public final void applyForce(double force, double direction) {
        double adjacent1 = force * cos(toRadians(direction));
        double opposite1 = force * sin(toRadians(direction));
        double adjacent2 = this.vel * cos(toRadians(this.direction));
        double opposite2 = this.vel * sin(toRadians(this.direction));
        double adjacent = (adjacent1 + adjacent2);
        double opposite = (opposite1 + opposite2);
        direction = toDegrees(atan(opposite / adjacent));
        force = sqrt((opposite * opposite) + (adjacent * adjacent));
        this.direction = (int)direction + this.direction;
        this.vel = force;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //  TEH END
}