/**
 * @author  RlonRyan
 * @name    JSpriteX
 * @version 1.0.0
 * @date    Dec 18, 2011
 * @info    Sprite base class.
**/

package JSpriteX;

import JBasicX.JPoint2DX;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import static java.lang.Math.*;

/**
 * @author  RlonRyan
 * @name    JSpriteX
**/
public abstract class JSpriteX {

    //Status
    private boolean visable = true;
    private boolean boundsvisable = false;
    private int status = 0;
    private int life = 100;
    private int type = 0;
    //Position
    private JPoint2DX position = new JPoint2DX();
    private JPoint2DX centeroffset = new JPoint2DX();
    private JPoint2DX center = new JPoint2DX();
    //Acceleration
    private double accel = 0;
    //Speed
    private double vel = 0;
    //Direction
    private int direction = 0;
    //Rotation
    private double rotation = 0;
    private double rotationrate = 0;
    //Size
    private JPoint2DX size = new JPoint2DX();
    private JPoint2DX scalesize = new JPoint2DX();
    private double scale = 1.0;
    private Boolean usescale = false;
    //AffineTransform
    private AffineTransform translation;
    //Update Timer
    private long lastupdate = 0;

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
    public final JPoint2DX getPosition() {
        return position;
    }

    /**
     *
     * @return
     */
    public final double getXPosition() {
        return position.getX();
    }

    /**
     *
     * @return
     */
    public final double getYPosition() {
        return position.getY();
    }

    /**
     *
     * @return
     */
    public final JPoint2DX getCenter() {
        return center;
    }

    /**
     *
     * @return
     */
    public final double getXCenter() {
        return center.getX();
    }

    /**
     *
     * @return
     */
    public final double getYCenter() {
        return center.getY();
    }

    /**
     *
     * @return
     */
    public final JPoint2DX getCenterOffset() {
        return centeroffset;
    }

    /**
     *
     * @return
     */
    public final double getXCenterOffset() {
        return centeroffset.getX();
    }

    /**
     *
     * @return
     */
    public final double getYCenterOffset() {
        return centeroffset.getY();
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
    public final JPoint2DX getSize() {
        return size;
    }

    /**
     *
     * @return
     */
    public final double getWidth() {
        return size.getX();
    }

    /**
     *
     * @return
     */
    public final double getHeight() {
        return size.getY();
    }

    /**
     *
     * @return
     */
    public final JPoint2DX getScalesize() {
        return scalesize;
    }

    /**
     *
     * @return
     */
    public final double getScaleWidth() {
        return scalesize.getX();
    }

    /**
     *
     * @return
     */
    public final double getScaleHeight() {
        return scalesize.getY();
    }

    /**
     *
     * @return
     */
    public final double getScale() {
        return scale;
    }

    /**
     *
     * @return
     */
    public final Boolean usingScale() {
        return usescale;
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
    public final void setPosition(JPoint2DX position) {
        this.position = position;
        this.autoSetCenter();
    }

    /**
     *
     * @param x
     * @param y
     */
    public final void setPosition(double x, double y) {
        this.position.setPoint(x, y);
        this.autoSetCenter();
    }

    /**
     *
     * @param x
     */
    public final void setXPosition(double x) {
        this.position.setX(x);
        this.autoSetCenter();
    }

    /**
     *
     * @param y
     */
    public final void setYPosition(double y) {
        this.position.setY(y);
        this.autoSetCenter();
    }

    /**
     *
     * @param centeroffset
     */
    public final void setCenterOffset(JPoint2DX centeroffset) {
        this.centeroffset = centeroffset;
    }

    /**
     *
     * @param xoffset
     * @param yoffset
     */
    public final void setCenterOffset(double xoffset, double yoffset) {
        this.center.setPoint(xoffset, yoffset);
    }

    /**
     *
     * @param xoffset
     */
    public final void setXCenterOffset(double xoffset) {
        this.center.setX(xoffset);
    }

    /**
     *
     * @param yoffset
     */
    public final void setYCenterOffset(double yoffset) {
        this.center.setY(yoffset);
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
    public final void setSize(JPoint2DX size) {
        this.size = size;
    }

    /**
     *
     * @param width
     * @param height
     */
    public final void setSize(double width, double height) {
        this.size.setPoint(width, height);
        this.autoSetCenter();
    }

    /**
     *
     * @param width
     */
    public final void setWidth(double width) {
        this.size.setX(width);
        this.autoSetCenter();
    }

    /**
     *
     * @param height
     */
    public final void setHeight(double height) {
        this.size.setY(height);
        this.autoSetCenter();
    }

    /**
     *
     * @param scalesize
     */
    public final void setScalesize(JPoint2DX scalesize) {
        this.scalesize = scalesize;
        this.autoSetCenter();
    }

    /**
     *
     * @param width
     * @param height
     */
    public final void setScaleSize(double width, double height) {
        this.scalesize.setPoint(width, height);
        this.autoSetCenter();
    }

    /**
     *
     * @param width
     */
    public final void setScaleWidth(double width) {
        this.scalesize.setX(width);
        this.autoSetCenter();
    }

    /**
     *
     * @param height
     */
    public final void setScaleHeight(double height) {
        this.scalesize.setY(height);
        this.autoSetCenter();
    }

    /**
     *
     * @param scale
     */
    public final void setScale(double scale) {
        this.scale = scale;
        this.autoSetCenter();
    }

    /**
     *
     * @param usescale
     */
    public final void useScale(Boolean usescale) {
        this.usescale = usescale;
        this.autoSetCenter();
    }

    /**
     *
     */
    public final void useScale() {
        this.usescale = true;
        this.autoSetCenter();
    }

    /**
     *
     */
    public final void noScale() {
        this.usescale = false;
        this.autoSetCenter();
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
     * @param scale
     */
    public final void incScale(double scale) {
        this.scale += scale;
    }

    /**
     *
     * @param x
     */
    public final void incX(double x) {
        this.setXPosition(this.getPosition().getX() + x);
    }

    /**
     *
     * @param y
     */
    public final void incY(double y) {
        this.setYPosition(this.getPosition().getY() + y);
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

    /**
     *
     */
    public final void autoSetCenter() {
        if (this.usescale) {
            this.center = new JPoint2DX(this.getXPosition() + this.getWidth() / 2 + this.getXCenterOffset(), this.getYPosition() + this.getHeight() / 2 + this.getYCenterOffset());
        }
        else {
            this.center = new JPoint2DX((this.getXPosition() + (this.getScaleWidth() / 2) + this.getXCenterOffset()), (this.getYPosition() + (this.getScaleHeight() / 2) + this.getYCenterOffset()));
        }
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
        this.incX(((this.vel * (Math.cos(this.direction * Math.PI / 180))) / 1000) * (System.currentTimeMillis() - this.lastupdate));
        this.incY(((this.vel * (Math.sin(this.direction * Math.PI / 180))) / 1000) * (System.currentTimeMillis() - this.lastupdate));
        this.lastupdate = System.currentTimeMillis();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Scale
    /**
     *
     */
    public final void scale() {
        this.usescale = true;
        this.setScaleSize(this.getWidth() * scale, this.getHeight() * scale);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Bounds
    /**
     *
     * @return
     */
    public final Rectangle boundsToRect() {
        this.autoSetCenter();
        if (this.usingScale()) {
            return new Rectangle((int) (this.getXPosition() - (this.getScaleWidth() / 2)),
                    (int) (this.getYPosition() - (this.getScaleHeight() / 2)),
                    (int) this.getScaleWidth(),
                    (int) this.getScaleHeight());
        }
        else {
            return new Rectangle((int) (this.getXPosition() - (this.getWidth() / 2)),
                    (int) (this.getYPosition() - (this.getHeight() / 2)),
                    (int) this.getWidth(),
                    (int) this.getHeight());
        }
    }

    /**
     *
     * @param sprite
     * @return
     */
    public final Boolean collidesWith(JSpriteX sprite) {
        return (this != sprite) && (this.boundsToRect().intersects(sprite.boundsToRect()));
    }

    /**
     *
     * @param rect
     * @return
     */
    public final Boolean collidesWith(Rectangle rect) {
        return this.boundsToRect().intersects(rect);
    }

    /**
     *
     * @param point
     * @return
     */
    public final Boolean collidesWith(JPoint2DX point) {
        return this.boundsToRect().contains(point.getX(), point.getY());
    }

    /**
     *
     * @param g2d
     */
    public final void drawBoundsTo(Graphics2D g2d) {
        g2d.drawRect(this.boundsToRect().x, this.boundsToRect().y, this.boundsToRect().width, this.boundsToRect().height);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Draw
    /**
     *
     * @param g2d
     */
    public final void draw(Graphics2D g2d) {
        if (usescale) {
            g2d.scale(this.scale, this.scale);
        }
        if (this.boundsvisable) {
            g2d.drawRect(this.boundsToRect().x, this.boundsToRect().y, this.boundsToRect().width, this.boundsToRect().height);
        }
        g2d.translate(this.getXPosition(), this.getYPosition());
        g2d.rotate(Math.toRadians(this.rotation));
        g2d.translate(-(this.getWidth() / 2), -(this.getHeight() / 2));
        this.drawSprite(g2d);
    }

    /**
     *
     * @param g2d
     */
    protected abstract void drawSprite(Graphics2D g2d);
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