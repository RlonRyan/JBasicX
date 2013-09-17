/**
 * @author  RlonRyan
 * @name    JSpriteX
 * @version 1.0.0
 * @date    Jan 11, 2012
 * @info    Sprite container class.
**/

package JSpriteX;

import JGameEngineX.JGameEngineX;
import java.awt.Graphics2D;
import java.util.LinkedList;

final public class JSpriteHolderX implements Runnable {

    // Constants
    public static final int SPRITE_OTHER = 0;
    public static final int SPRITE_BASIC = 1;
    public static final int SPRITE_LOOPER = 2;
    public static final int SPRITE_BOUNCER = 3;
    public static final int SPRITE_STATIC = 4;
    // Public

    // Private
    private Thread spriteUpdateThread;
    private LinkedList<JSpriteX> sprites;
    private LinkedList<JSpriteX> templatesprites;
    private LinkedList<String> templatespritesname;
    private JGameEngineX holder;
    private int dsups = 105;
    private int sups = 0;
    private long updatetime = 0;
    private int updatenumber = 0;
    private boolean active = false;

    public JSpriteHolderX(JGameEngineX holder) {
        this.sprites = new LinkedList<JSpriteX>();
        this.holder = holder;
    }

    final public int getDsups() {
        return dsups;
    }

    final public int getSups() {
        return sups;
    }

    final public void setDsups(int dsups) {
        this.dsups = dsups;
    }

    public final boolean isActive(){
        return active;
    }

    synchronized final public void addTemplateSprite(String name, int type, int direction, double vel, double x, double y) {
        JPictureSpriteX spr = new JPictureSpriteX(this.holder.getGameWinWidthCenter(), this.holder.getGameWinHeightCenter());
        spr.setPosition(x, y);
        spr.setDirection(direction);
        spr.setVel(vel);
        spr.setType(type);
        this.templatesprites.add(spr);
        this.templatespritesname.add(name);
    }

    synchronized final public void addTemplateSprite(JSpriteX sprite, String name) {
        this.templatesprites.add(sprite);
        this.templatespritesname.add(name);
    }

    synchronized final public void addSprite(int type, int direction, double vel, double x, double y) {
        JPictureSpriteX spr = new JPictureSpriteX(this.holder.getGameWinWidthCenter(), this.holder.getGameWinHeightCenter());
        spr.setPosition(x, y);
        spr.setDirection(direction);
        spr.setVel(vel);
        spr.setType(type);
        this.sprites.add(spr);
    }

    synchronized final public void addSprite(int type, double x, double y) {
        JPictureSpriteX spr = new JPictureSpriteX(this.holder.getGameWinWidthCenter(), this.holder.getGameWinHeightCenter());
        spr.setPosition(x, y);
        spr.setType(type);
        this.sprites.add(spr);
    }

    synchronized final public void addSprite(int type) {
        JPictureSpriteX spr = new JPictureSpriteX(this.holder.getGameWinWidthCenter(), this.holder.getGameWinHeightCenter());
        spr.setType(type);
        this.sprites.add(spr);
    }

    synchronized final public void deleteSprite(int index) {
        this.sprites.remove(index);
    }

    synchronized final public void cloneTemplateSprite(String name, int direction, double vel, double x, double y) {
        this.sprites.add(this.templatesprites.get(this.templatespritesname.indexOf(name)));
        this.sprites.getLast().setPosition(x, y);
        this.sprites.getLast().setDirection(direction);
        this.sprites.getLast().setVel(vel);
    }

    synchronized final public void cloneTemplateSprite(String name, double x, double y) {
        this.sprites.add(this.templatesprites.get(this.templatespritesname.indexOf(name)));
        this.sprites.getLast().setPosition(x, y);
    }

    synchronized final public void cloneTemplateSprite(String name) {
        this.sprites.add(this.templatesprites.get(this.templatespritesname.indexOf(name)));
    }

    synchronized final public void hideAll() {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).hide();
        }
    }

    synchronized final public void showAll() {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).show();
        }
    }

    synchronized final public void updateSprites() {

        for (int i = 0; i < this.sprites.size(); i++) {
            JSpriteX spr = this.sprites.get(i);
            switch (spr.getType()) {
                case SPRITE_BASIC:
                    spr.update();
                    if ((spr.getXPosition() < 0) || (spr.getXPosition() > this.holder.getGameWinWidth()) || (spr.getYPosition() < 0) || (spr.getYPosition() > this.holder.getGameWinHeight())) {
                        this.sprites.remove(i);
                    }
                    break;
                case SPRITE_LOOPER:

                    if (spr.getXPosition() < 0) {
                        spr.setXPosition(this.holder.getGameWinWidth());
                    }
                    else if (spr.getXPosition() > this.holder.getGameWinWidth()) {
                        spr.setXPosition(0);
                    }
                    else if (spr.getYPosition() < 0) {
                        spr.setYPosition(this.holder.getGameWinHeight());
                    }
                    else if (spr.getYPosition() > this.holder.getGameWinHeight()) {
                        spr.setYPosition(0);
                    }
                    spr.update();
                    break;
                case SPRITE_BOUNCER:
                    if (spr.getXPosition() < 0) {
                        spr.setDirection(180 - spr.getDirection());
                        spr.setRotation(spr.getDirection() - 90);
                        spr.setXPosition(0);
                    }
                    else if (spr.getXPosition() > this.holder.getGameWinWidth()) {
                        spr.setDirection(180 - spr.getDirection());
                        spr.setRotation(spr.getDirection() - 90);
                        spr.setXPosition(this.holder.getGameWinWidth());
                    }
                    else if (spr.getYPosition() < 0) {
                        spr.setDirection(360 - spr.getDirection());
                        spr.setRotation(spr.getDirection() - 90);
                        spr.setYPosition(0);
                    }
                    else if (spr.getYPosition() > this.holder.getGameWinHeight()) {
                        spr.setDirection(360 - spr.getDirection());
                        spr.setRotation(spr.getDirection() - 90);
                        spr.setYPosition(this.holder.getGameWinHeight());
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

    synchronized final public void drawSprites(Graphics2D g2d) {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).draw(g2d);
            this.holder.resetAffineTransform();
            this.holder.resetDrawColor();
        }
    }

    synchronized final public void drawSpriteBounds(Graphics2D g2d) {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).drawBoundsTo(g2d);
            this.holder.resetAffineTransform();
            this.holder.resetDrawColor();
        }
    }

    synchronized final public JSpriteX collidesWith(JSpriteX sprite) {
        for (int i = 0; i < this.sprites.size(); i++) {
            if (this.sprites.get(i).collidesWith(sprite)) {
                return this.sprites.get(i);
            }
        }
        return null;
    }

    synchronized final public JSpriteX collidesWithAndRemove(JSpriteX sprite) {
        for (int i = 0; i < this.sprites.size(); i++) {
            if (this.sprites.get(i).collidesWith(sprite)) {
                return this.sprites.remove(i);
            }
        }
        return null;
    }

    synchronized final public int checkCollisionsWith(JSpriteX sprite) {
        int c = 0;
        for (int i = 0; i < this.sprites.size(); i++) {
            if (this.sprites.get(i).collidesWith(sprite)) {
                c++;
            }
        }
        return c;
    }

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

    final public void start() {
        this.spriteUpdateThread = new Thread(this);
        this.spriteUpdateThread.start();
        this.active = true;
    }

    @Override
    final public void run() {
        Thread current = Thread.currentThread();
        while (current == this.spriteUpdateThread) {
            try {
                this.updateSprites();
                Thread.sleep(1000 / this.dsups);
            }
            catch (InterruptedException e) {
            }
        }
    }

    final public void stop() {
        this.spriteUpdateThread = null;
        this.active = false;
    }
}
