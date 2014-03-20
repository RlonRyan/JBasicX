/**
 * @author RlonRyan
 * @name JSpriteX
 * @version 1.0.0
 * @date Jan 11, 2012
 * @info Sprite container class.
 *
 */
package JSpriteX;

import JBasicX.JImageHandlerX;
import JGameEngineX.JGameEngineListenerX;
import JGameEngineX.JGameEngineX;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;

/**
 * @author RlonRyan
 * @name JSpriteHolderX
 */
final public class JSpriteHolderX implements Runnable, JGameEngineListenerX {

    // Constants
    /**
     *
     */
    public static final int SPRITE_OTHER = 0;
    /**
     *
     */
    public static final int SPRITE_BASIC = 1;
    /**
     *
     */
    public static final int SPRITE_LOOPER = 2;
    /**
     *
     */
    public static final int SPRITE_BOUNCER = 3;
    /**
     *
     */
    public static final int SPRITE_STATIC = 4;
    // Public
    // Private
    private Thread spriteUpdateThread;
    private LinkedList<JSpriteX> sprites;
    private JImageHandlerX images;
    private JGameEngineX holder;
    private int dsups = 100;
    private int sups = 0;
    private long updatetime;
    private int updatenumber;
    private boolean active = false;

    /**
     *
     * @param holder
     */
    public JSpriteHolderX(JGameEngineX holder) {
        this.images = new JImageHandlerX();
        this.sprites = new LinkedList<>();
        this.holder = holder;
    }

    /**
     *
     * @return
     */
    final public int getDsups() {
        return dsups;
    }

    /**
     *
     * @return
     */
    final public int getSups() {
        return sups;
    }

    public final JSpriteX getSprite(int index) {
        return this.sprites.get(index);
    }

    public final BufferedImage getImage(String name) {
        return this.images.getImage(name);
    }

    /**
     *
     * @param dsups
     */
    final public void setDsups(int dsups) {
        this.dsups = dsups;
    }

    /**
     *
     * @return
     */
    public final boolean isActive() {
        return active;
    }

    /**
     *
     * @param type
     * @param direction
     * @param vel
     * @param x
     * @param y
     */
    synchronized final public void addSprite(int type, int direction, double vel, double x, double y) {
        JSpriteX spr = new JPictureSpriteX(this.images.getDefaultImage(), x, y);
        spr.setDirection(direction);
        spr.setVel(vel);
        spr.setType(type);
        this.sprites.add(spr);
    }

    synchronized final public void addSprite(int type, int direction, double vel, double x, double y, String imagename) {
        JSpriteX spr = new JPictureSpriteX(this.images.getImage(imagename), x, y);
        spr.setDirection(direction);
        spr.setVel(vel);
        spr.setType(type);
        this.sprites.add(spr);
    }

    /**
     *
     * @param type
     * @param x
     * @param y
     */
    synchronized final public void addSprite(int type, double x, double y) {
        JSpriteX spr = new JPictureSpriteX(x, y);
        spr.setType(type);
        this.sprites.add(spr);
    }

    /**
     *
     * @param type
     */
    synchronized final public void addSprite(int type) {
        JSpriteX spr = new JPictureSpriteX(this.holder.getCenterX(), this.holder.getCenterY());
        spr.setType(type);
        this.sprites.add(spr);
    }

    synchronized final public void addPicture(String name) {
        this.images.addImage(name);
    }

    synchronized final public void addPicture(String name, String filename) {
        this.images.addImage(name, filename);
    }

    synchronized final public void addPicture(String name, File file) {
        this.images.addImage(name, file);
    }

    /**
     *
     * @param index
     */
    synchronized final public void deleteSprite(int index) {
        this.sprites.remove(index);
    }

    /**
     *
     *
     */
    synchronized final public void deleteAllSprites() {
        this.sprites.clear();
    }

    /**
     *
     */
    synchronized final public void hideAll() {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).hide();
        }
    }

    /**
     *
     */
    synchronized final public void showAll() {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).show();
        }
    }

    /**
     *
     */
    synchronized final public void updateSprites() {

        for (int i = 0; i < this.sprites.size(); i++) {
            JSpriteX spr = this.sprites.get(i);
            switch (spr.getType()) {
                case SPRITE_BASIC:
                    spr.update();
                    if ((spr.getX() < 0) || (spr.getX() > this.holder.getGameWinWidth()) || (spr.getY() < 0) || (spr.getY() > this.holder.getGameWinHeight())) {
                        this.sprites.remove(i);
                    }
                    break;
                case SPRITE_LOOPER:

                    if (spr.getX() < 0) {
                        spr.setX(this.holder.getGameWinWidth());
                    } else if (spr.getX() > this.holder.getGameWinWidth()) {
                        spr.setX(0);
                    } else if (spr.getY() < 0) {
                        spr.setY(this.holder.getGameWinHeight());
                    } else if (spr.getY() > this.holder.getGameWinHeight()) {
                        spr.setY(0);
                    }
                    spr.update();
                    break;
                case SPRITE_BOUNCER:
                    if (spr.getX() < 0) {
                        spr.setDirection(180 - spr.getDirection());
                        spr.setRotation(spr.getDirection() - 90);
                        spr.setX(0);
                    } else if (spr.getX() > this.holder.getGameWinWidth()) {
                        spr.setDirection(180 - spr.getDirection());
                        spr.setRotation(spr.getDirection() - 90);
                        spr.setX(this.holder.getGameWinWidth());
                    } else if (spr.getY() < 0) {
                        spr.setDirection(360 - spr.getDirection());
                        spr.setRotation(spr.getDirection() - 90);
                        spr.setY(0);
                    } else if (spr.getY() > this.holder.getGameWinHeight()) {
                        spr.setDirection(360 - spr.getDirection());
                        spr.setRotation(spr.getDirection() - 90);
                        spr.setY(this.holder.getGameWinHeight());
                    }
                    spr.update();
                    break;
                case SPRITE_STATIC:
                    break;
                case SPRITE_OTHER:
                default:
                    spr.update();
                    break;

            }
        }
        updatenumber++;
        if (System.currentTimeMillis() > updatetime + 1000) {
            updatetime = System.currentTimeMillis();
            sups = updatenumber;
            updatenumber = 0;
        }
    }

    /**
     *
     * @param g2d
     */
    synchronized final public void drawSprites(Graphics2D g2d) {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).draw(g2d);
        }
    }

    /**
     *
     * @param g2d
     */
    synchronized final public void drawSpriteBounds(Graphics2D g2d) {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).drawBoundsTo(g2d);
        }
    }

    /**
     *
     * @param sprite
     * <p/>
     * @return
     */
    synchronized final public JSpriteX collidesWith(JSpriteX sprite) {
        for (int i = 0; i < this.sprites.size(); i++) {
            if (this.sprites.get(i).collidesWith(sprite)) {
                return this.sprites.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param sprite
     * <p/>
     * @return
     */
    synchronized final public JSpriteX collidesWithAndRemove(JSpriteX sprite) {
        for (int i = 0; i < this.sprites.size(); i++) {
            if (this.sprites.get(i).collidesWith(sprite)) {
                return this.sprites.remove(i);
            }
        }
        return null;
    }

    /**
     *
     * @param sprite
     * <p/>
     * @return
     */
    synchronized final public int checkCollisionsWith(JSpriteX sprite) {
        int c = 0;
        for (int i = 0; i < this.sprites.size(); i++) {
            if (this.sprites.get(i).collidesWith(sprite)) {
                c++;
            }
        }
        return c;
    }

    /**
     *
     * @param sprite
     * <p/>
     * @return
     */
    synchronized final public int checkCollisionsWithAndRemove(JSpriteX sprite) {
        int c = 0;
        for (int i = 0; i < this.sprites.size(); i++) {
            if (this.sprites.get(i).collidesWith(sprite)) {
                this.sprites.remove(i);
                c++;
            }
        }
        return c;
    }

    /**
     *
     */
    synchronized final public void checkCollisionsAndRemove() {
        for (int i = 0; i < this.sprites.size(); i++) {
            for (int ii = 0; ii < this.sprites.size(); ii++) {
                if (i != ii && this.sprites.get(i).collidesWith(this.sprites.get(ii))) {
                    this.sprites.remove(i);
                    this.sprites.remove(ii);
                }
            }
        }
    }

    synchronized final public void pauseAll() {
        for (JSpriteX e : sprites) {
            e.pause();
        }
    }

    /**
     *
     */
    final public void start() {
        if (this.isActive()) {
            return;
        }
        this.spriteUpdateThread = new Thread(this);
        this.spriteUpdateThread.start();
        this.active = true;
        this.updatetime = 0;
        this.updatenumber = 0;
    }

    @Override
    final public void run() {
        Thread current = Thread.currentThread();
        while (current == this.spriteUpdateThread) {
            try {
                this.updateSprites();
                Thread.sleep(1000 / this.dsups);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     *
     */
    final public void stop() {
        this.spriteUpdateThread = null;
        this.active = false;
    }

    @Override
    public void gameStateChanged(JGameEngineX.GAME_STATUS newstate, JGameEngineX.GAME_STATUS oldstate) {
        if (oldstate == JGameEngineX.GAME_STATUS.GAME_RUNNING && this.isActive()) {
            this.stop();
            this.pauseAll();
        }
        if (newstate == JGameEngineX.GAME_STATUS.GAME_RUNNING && !this.isActive()) {
            this.start();
            this.pauseAll();
        }
    }
}
