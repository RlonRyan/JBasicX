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
    //Acceleration
    protected double accel = 0;
    //Speed
    protected double vel = 0;
    //Direction
    protected int direction = 0;
    //Rotation
    protected double rotation = 0;
    protected double rotationrate = 0;
    //Size
    protected Rectangle bounds = new Rectangle();
    //AffineTransform
    protected AffineTransform translation;
    //Update Timer
    protected long lastupdate = 0;

    /**
     *
     * @return
     */
    public final boolean isVisable() {
        return visable;
    }

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
        return bounds.getLocation();
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
    public final double getWidth() {
        return bounds.getX();
    }

    /**
     *
     * @return
     */
    public final double getHeight() {
        return bounds.getY();
    }

    /**
     *
     * @return
     */
    public AffineTransform getTranslation() {
        return translation;
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
     * @param position
     */
    public final void setPosition(Point2D position) {
        this.bounds.setLocation((Point)position);
    }

    /**
     *
     * @param x
     * @param y
     */
    public final void setPosition(int x, int y) {
        this.bounds.setLocation(x, y);
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
     * @param size
     */
    public final void setSize(Rectangle size) {
        this.bounds = size;
    }

    public final void setSize(int width, int height) {
        this.bounds.setSize(width, height);
    }

    /**
     *
     * @param translation
     */
    public void setTranslation(AffineTransform translation) {
        this.translation = translation;
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
    public final void incRotation(double rotation) {
        this.rotation += rotation;
    }

    /**
     *
     * @param rotationrate
     */
    public final void incRotationrate(double rotationrate) {
        this.rotationrate += rotationrate;
    }

    /**
     *
     * @param x
     */
    public final void incX(int x) {
        this.bounds.x = this.bounds.x + x;
    }

    /**
     *
     * @param y
     */
    public final void incY(int y) {
        this.bounds.x = this.bounds.x + y;
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
        g2d.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
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