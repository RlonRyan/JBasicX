/**
 * @author RlonRyan
 * @name JGameX
 * @version 1.0.2
 * @date September 11th, 2011
**/

package JGameEngineX;

import JBasicX.JImageHandlerX;
import JIOX.*;
import JSpriteX.JSpriteHolderX;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

public abstract class JGameEngineX extends Applet implements Runnable, JInputOutputX, KeyListener {

// Constants
    /**
     *
     */
    public static final int gamestopped = 0;
    /**
     *
     */
    public static final int gamestarting = 1;
    /**
     *
     */
    public static final int gamemenu = 2;
    /**
     *
     */
    public static final int gamerunning = 3;
    /**
     *
     */
    public static final int gamepaused = 4;
    /**
     *
     */
    public static final Color defaultbackgroundcolor = Color.BLACK;
    /**
     *
     */
    public static final Color defaultdrawcolor = Color.WHITE;
// Public
    /**
     *
     */
    public JSpriteHolderX spriteholder;
// Protected
    /**
     *
     */
    protected JMouseX mouse;
    /**
     *
     */
    protected JImageHandlerX images;
// Private
    private boolean[] keys = new boolean[526];
    private int gamestatus;
    private int winw;
    private int winh;
    private int winwc;
    private int winhc;
    private Thread gamemain;
    private int framenum = 0;
    private int fps = 0;
    private long frametime = 0;
    private long dfps = 50;
    private boolean showgamedata = true;
    private BufferedImage backbuffer;
    private Graphics2D g2d;
    private AffineTransform affinetransform;
    private long gametime;
    private long gamestarttime;
    private long gamepausedat;
    private long gamelastpausedat = 0;
    private long gamepausetime = 0;
    private Color backgroundcolor = Color.BLACK;
    private Color drawcolor = Color.WHITE;

    /**
     * <code>gameStart</code> function to override in new instance.
     * <p/>
     * Override function is to contain game startup code.
     * <br/>Called by the
     * <code>init</code> function.
     */
    public abstract void gameStart();

    /**
     * <code>gameEnd</code> function to override in new instance.
     * <p/>
     * Override function is to contain game end code.
     * <br/>Called when
     * <code>gamestatus = stopped</code>.
     */
    public abstract void gameEnd();

    /**
     * <code>gameStart</code> function to override in new instance.
     * <p/>
     * Override function is to contain game menu code.
     * <br/>Called when
     * <code>gamestatus = menu</code>.
     */
    public abstract void gameMenu();

    /**
     * <code>gameStart</code> function to override in new instance.
     * <p/>
     * Override function is to contain game update code.
     * <br/>Called when
     * <code>gamestatus = running</code>.
     */
    public abstract void gameUpdate();

    /**
     * <code>gameStart</code> function to override in new instance.
     * <p/>
     * Override function is to contain game pause code.
     * <br/>Called when
     * <code>gamestatus = paused</code>.
     */
    public abstract void gamePaused();

    /**
     *
     * @param g2d The graphics pointing to backbuffer to be drawn to.
     */
    public abstract void gameMenuPaint(Graphics2D g2d);

    /**
     *
     * @param g2d The graphics pointing to backbuffer to be drawn to.
     */
    public abstract void gamePaint(Graphics2D g2d);

    /**
     *
     * @param g2d The graphics pointing to backbuffer to be drawn to.
     */
    public abstract void gamePausePaint(Graphics2D g2d);

    /**
     *
     * @param g2d The graphics pointing to backbuffer to be drawn to.
     */
    public abstract void gameStoppedPaint(Graphics2D g2d);
    //Acessors

    /**
     * Method for getting the
     * <code>gamestatus</code>variable.
     * <p/>
     * @return <code>gamestatus</code><br/>0 = stopped.<br/>1 = menu.<br/>2 =
     *         running.<br/>3 = paused.
     */
    public final int getGameStatus() {
        return this.gamestatus;
    }

    /**
     * M
     * <p/>
     * @return
     */
    public final int getGameWinWidth() {
        return this.winw;
    }

    /**
     *
     * @return
     */
    public final int getGameWinHeight() {
        return this.winh;
    }

    /**
     *
     * @return
     */
    public final int getGameWinWidthCenter() {
        return this.winwc;
    }

    /**
     *
     * @return
     */
    public final int getGameWinHeightCenter() {
        return this.winhc;
    }

    /**
     *
     * @return
     */
    public final BufferedImage getBackbuffer() {
        return this.backbuffer;
    }

    /**
     *
     * @return
     */
    public final int getFPS() {
        return this.fps;
    }

    /**
     *
     * @return
     */
    public final double getDFPS() {
        return this.dfps;
    }

    /**
     *
     * @return
     */
    public final Graphics2D getGameGraphics() {
        return this.g2d;
    }

    /**
     *
     * @return
     */
    public final long getGameStartTime() {
        return this.gamestarttime;
    }

    /**
     *
     * @return
     */
    public final long getGameTime() {
        return this.gametime;
    }

    /**
     *
     * @return
     */
    public final long getTotalGamePauseTime() {
        return ((this.gametime - this.gamepausedat) + this.gamepausetime);
    }

    /**
     *
     * @return
     */
    public final long getGamePauseTime() {
        return this.gametime - this.gamepausedat;
    }

    /**
     *
     * @return
     */
    public final long getGameGameLastPausedAt() {
        return this.gamelastpausedat;
    }

    /**
     *
     * @return
     */
    public final Color getDrawColor() {
        return drawcolor;
    }

    /**
     *
     * @return
     */
    public final Color getBackgroundColor() {
        return backgroundcolor;
    }

    /**
     *
     * @return
     */
    public final boolean[] getKeys() {
        return this.keys;
    }

    /**
     *
     * @return
     */
    public final String getKeysDownString() {
        String keysdown = "Keys: ";
        for (int i = 1; i < 525; i++) {
            if (this.keys[i] == true) {
                keysdown += " " + KeyEvent.getKeyText(i) + ";";
            }
        }
        return keysdown;
    }

    /**
     *
     * @param keycode
     * @return
     */
    public final boolean isKeyDown(int keycode) {
        return this.keys[keycode];
    }

    /**
     *
     * @param keycode
     * @return
     */
    public final boolean isKeyDownAndRemove(int keycode) {
        if (this.keys[keycode]) {
            this.keys[keycode] = false;
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public final boolean isGameDataVisible() {
        return this.showgamedata;
    }
    //Mutators

    /**
     *
     * @param status
     */
    public final void setGameStatus(int status) {
        this.gamestatus = status;
    }

    /**
     *
     * @param visable
     */
    public final void setGameDataVisable(boolean visable) {
        this.showgamedata = visable;
    }

    /**
     *
     * @param drawcolor
     */
    public final void setDrawColor(Color drawcolor) {
        this.drawcolor = drawcolor;
    }

    /**
     *
     * @param backgroundcolor
     */
    public final void setBackgroundColor(Color backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    /**
     *
     * @param framerate
     * @param gamewindowwidth
     * @param gamewindowheight
     * @param status
     */
    public final void setGameAtrib(int framerate, int gamewindowwidth, int gamewindowheight, int status) {
        this.dfps = framerate;
        this.gamestatus = status;
        this.resizeGame(gamewindowwidth, gamewindowheight);
    }

    /**
     *
     * @param dfps
     */
    public final void setDFPS(long dfps) {
        this.dfps = dfps;
    }

    //Reset
    /**
     *
     */
    public final void resetBackgroundColorToDefault() {
        this.backgroundcolor = defaultbackgroundcolor;
    }

    /**
     *
     */
    public final void resetDrawColorToDefault() {
        this.drawcolor = defaultdrawcolor;
        this.g2d.setColor(drawcolor);
    }

    /**
     *
     */
    public final void resetDrawColor() {
        this.g2d.setColor(drawcolor);
    }

    /**
     *
     */
    public final void resetAffineTransform() {
        this.g2d.setTransform(affinetransform);
    }
    //Functions

    /**
     *
     */
    public void pausegame() {
        this.gamepausedat = this.gametime;
        this.gamestatus = gamepaused;
    }

    /**
     *
     */
    public void unpausegame() {
        this.gamepausetime += this.gametime - this.gamepausedat;
        this.gamelastpausedat = this.gametime;
        this.gamepausedat = 0;
        this.gamestatus = gamerunning;
    }

    @Override
    public final void update(Graphics g) {
        framenum++;
        if (System.currentTimeMillis() > frametime + 1000) {
            frametime = System.currentTimeMillis();
            fps = framenum;
            framenum = 0;
        }
        clearBackbuffer();
        switch (this.gamestatus) {
            case gamemenu:
                gameMenuPaint(g2d);
                break;
            case gamepaused:
                gamePausePaint(g2d);
                break;
            case gamerunning:
                gamePaint(g2d);
                break;
            case gamestopped:
                gameStoppedPaint(g2d);
                break;
            default:
                paintError("Invalid Game Mode.");
                break;
        }
        if (showgamedata) {
            paintGameData();
        }
        paint(g);
    }

    @Override
    public final void paint(Graphics g) {
        g.drawImage(this.backbuffer, 0, 0, this);
    }

    /**
     *
     */
    public final void clearBackbuffer() {
        Color prev = g2d.getColor();
        g2d.setPaint(this.backgroundcolor);
        g2d.fillRect(0, 0, backbuffer.getWidth(), backbuffer.getHeight());
        g2d.setColor(prev);
    }

    /**
     *
     */
    public void paintGameData() {
        g2d.drawString("FPS: " + this.fps, 10, backbuffer.getHeight() - 10);
        g2d.drawString("Sups: " + this.spriteholder.getSups(), 100, this.getGameWinHeight() - 10);
    }

    /**
     *
     * @param desc
     */
    public void paintError(String desc) {
        String message = "Error: " + desc;
        Color prevc = this.g2d.getColor();
        this.g2d.setColor(Color.red);
        this.g2d.drawString(message, this.getGameWinWidthCenter() - (10 + (this.g2d.getFontMetrics().stringWidth(message) / 2)), this.getGameWinHeightCenter());
        this.g2d.setColor(prevc);
    }

    /**
     *
     * @param e
     */
    public void paintError(Exception e) {
        String message = "Error: " + e.getLocalizedMessage();
        Color prevc = this.g2d.getColor();
        this.g2d.setColor(Color.red);
        this.g2d.drawString(message, this.getGameWinWidthCenter() - (10 + (this.g2d.getFontMetrics().stringWidth(message) / 2)), this.getGameWinHeightCenter());
        this.g2d.setColor(prevc);
    }

    /**
     *
     * @param width
     * @param height
     */
    public final void resizeGame(int width, int height) {
        this.winw = width;
        this.winh = height;
        this.winwc = width / 2;
        this.winhc = height / 2;
        this.backbuffer = new BufferedImage(winw, winh, BufferedImage.TYPE_INT_RGB);
        this.g2d = backbuffer.createGraphics();
        this.affinetransform = this.g2d.getTransform();
        super.resize(winw, winh);
        if (mouse != null) {
            mouse.clear();
        }
    }

    @Override
    public final void init() {

        //  Set Game Atributes
        this.setGameAtrib(50, 640, 480, gamestarting);

        //  Resources
        this.mouse = new JMouseX(this);
        this.images = new JImageHandlerX();
        this.spriteholder = new JSpriteHolderX(this);

        //  Listeners
        this.addKeyListener(this);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.addMouseWheelListener(mouse);

        // ???
        // I have no clue why theese have to be here but they do...
        start();

        //  Start the game thread
        this.gamemain = new Thread(this);
        this.gamemain.start();

        // Timing Stuff
        this.gamestarttime = System.currentTimeMillis();
        this.frametime = System.currentTimeMillis();
    }

    @Override
    public final void start() {

        //  Start-up Sprite holder
        this.spriteholder.start();

        //  Finally pass on control for pre-game stuff
        gameStart();
    }

    @Override
    public final void run() {
        Thread current = Thread.currentThread();
        while (current == this.gamemain) {
            try {
                Thread.sleep(1000 / this.dfps);
            }
            catch (InterruptedException e) {
                this.g2d.drawString(e.getLocalizedMessage(), 0, 0);
            }
            this.gametime = System.currentTimeMillis() - gamestarttime;
            switch(this.gamestatus) {
                case gamemenu:
                    gameMenu();
                    break;
                case gamerunning:
                    gameUpdate();
                    break;
                case gamepaused:
                    gamePaused();
                    break;
                case gamestopped:
                    stop();
                    break;
                case gamestarting:
                    System.out.println("This system is slow.");
                    break;
                default:
                    System.err.println("Invalid game mode.");
                    break;
            }
            repaint();
        }
    }

    @Override
    public final void stop() {
        gamemain = null;
        this.spriteholder.stop();
        this.gameEnd();
    }

    /**
     *
     */
    @Override
    public void updateIO() {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent k) {
    }

    @Override
    public void keyPressed(KeyEvent k) {
        this.keys[k.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent k) {
        this.keys[k.getKeyCode()] = false;
    }
}
