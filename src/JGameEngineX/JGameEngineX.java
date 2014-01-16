/**
 * @author RlonRyan
 * @name JGameX
 * @version 1.0.2
 * @date September 11th, 2011
 *
 */
package JGameEngineX;

import JBasicX.JImageHandlerX;
import JGameHolderX.JGameHolderX;
import JGameHolderX.JWindowHolderX;
import JIOX.JInputOutputX;
import JIOX.JKeyboardX;
import JIOX.JMouseX;
import JSpriteX.JSpriteHolderX;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.EventObject;

/**
 * @author RlonRyan
 * @name JGameX
 */
public abstract class JGameEngineX implements Runnable, JInputOutputX {

    public static enum EVENT_TYPE {

        STATE_CHANGE;
    }

    public static enum GAME_STATUS {

        GAME_STOPPED,
        GAME_STARTING,
        GAME_MENU,
        GAME_RUNNING,
        GAME_PAUSED;
    }
    // Constants
    public static final Color defaultbackgroundcolor = Color.BLACK;
    /**
     *
     */
    public static final Color defaultdrawcolor = Color.WHITE;
// Public
    /**
     *
     */
    public JGameHolderX holder;
// Protected
    /**
     *
     */
    protected JSpriteHolderX spriteholder;
    protected JMouseX mouse;
    protected JKeyboardX keyboard;
    /**
     *
     */
    protected JImageHandlerX images;
// Private
    private GAME_STATUS gamestatus;
    private GAME_STATUS prevgamestatus;
    private int winw;
    private int winh;
    private int winwc;
    private int winhc;
    private Thread gamemain;
    private int framenum = 0;
    private int fps = 0;
    private long frametime = 0;
    private long dfps = 50;
    private boolean showgamedata = false;
    private AffineTransform affinetransform;
    private long gametime;
    private long gamestarttime;
    private long gamepausedat;
    private long gamelastpausedat = 0;
    private long gamepausetime = 0;
    private Color backgroundcolor = Color.BLACK;
    private Color drawcolor = Color.WHITE;
    private Font font = new Font("Arial", Font.PLAIN, 10);
    private java.util.List<JGameEngineListenerX> listeners = new ArrayList<>();

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
     * Draws the game menu to the backbuffer.
     * <p/>
     * @param g2d The graphics pointing to backbuffer to be drawn to.
     */
    public abstract void gameMenuPaint(Graphics2D g2d);

    /**
     * Draws the main game screen to the backbuffer.
     * <p/>
     * @param g2d The graphics pointing to backbuffer to be drawn to.
     */
    public abstract void gamePaint(Graphics2D g2d);

    /**
     * Draws the game pause screen to the backbuffer.
     * <p/>
     * @param g2d The graphics pointing to backbuffer to be drawn to.
     */
    public abstract void gamePausePaint(Graphics2D g2d);

    /**
     * Draws the game stopped screen to the backbuffer.
     * <p/>
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
    public final GAME_STATUS getGameStatus() {
        return this.gamestatus;
    }

    /**
     * Method for getting the game screen width.
     * <p/>
     * @return The game's width.
     */
    public final int getGameWinWidth() {
        return this.winw;
    }

    /**
     * Method for getting the game screen height.
     * <p/>
     * @return The game's height.
     */
    public final int getGameWinHeight() {
        return this.winh;
    }

    /**
     * Method for getting the game screen center in terms of width.
     * <p/>
     * @return The game's center in terms of width.
     */
    public final int getGameWinWidthCenter() {
        return this.winwc;
    }

    /**
     * Method for getting the game screen center in terms of height.
     * <p/>
     * @return The game's center in terms of height.
     */
    public final int getGameWinHeightCenter() {
        return this.winhc;
    }

    /**
     * Returns the frames per second drawn to the screen (framerate).
     * <p/>
     * @return The game's framerate.
     */
    public final int getFPS() {
        return this.fps;
    }

    /**
     * Returns the game's desired frames per second rate (desired framerate).
     * <p/>
     * @return The game's desired framerate.
     */
    public final double getDFPS() {
        return this.dfps;
    }

    /**
     * Returns the game's graphics, which point to the backbuffer.
     * <p/>
     * @return The game's primary graphics.
     */
    public final Graphics2D getGameGraphics() {
        return this.holder.getGraphics();
    }

    /**
     * Returns the game's start time.
     * <p/>
     * @return The time the game first started.
     */
    public final long getGameStartTime() {
        return this.gamestarttime;
    }

    /**
     * Returns the time elapsed since the game started.
     * <p/>
     * @return The game time.
     */
    public final long getGameTime() {
        return this.gametime;
    }

    /**
     * Returns the total time spent in the game paused state.
     * Returns 0 if not in the pause state.
     * <p/>
     * @return The total time spent in the game paused state.
     */
    public final long getTotalGamePauseTime() {
        if (this.gamestatus == GAME_STATUS.GAME_STARTING) {
            return ((this.gametime - this.gamepausedat) + this.gamepausetime);
        }
        else {
            return 0;
        }
    }

    /**
     * Returns the time elapsed since the game was last paused.
     * Returns 0 if not in the pause state.
     * <p/>
     * @return The total time spent in the game paused state.
     */
    public final long getGamePauseTime() {
        if (this.gamestatus == GAME_STATUS.GAME_PAUSED) {
            return this.gametime - this.gamepausedat;
        }
        else {
            return 0;
        }
    }

    /**
     * Returns the last time the game was paused.
     * <p/>
     * @return
     */
    public final long getGameGameLastPausedAt() {
        return this.gamelastpausedat;
    }

    /**
     * Returns the current draw color.
     * The default starting color is
     * <code>Color.WHITE</code>.
     * <p/>
     * @return The current draw color.
     */
    public final Color getDrawColor() {
        return drawcolor;
    }

    /**
     * Returns the current background color.
     * The default starting color is
     * <code>Color.BLACK</code>.
     * <p/>
     * @return The current background color.
     */
    public final Color getBackgroundColor() {
        return backgroundcolor;
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
    public final void setGameStatus(GAME_STATUS status) {
        this.prevgamestatus = this.gamestatus;
        this.gamestatus = status;
        fireEvent(EVENT_TYPE.STATE_CHANGE, this.gamestatus, this.prevgamestatus);
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
     * Should only be called once.
     *
     * @param framerate
     * @param gamewindowwidth
     * @param gamewindowheight
     * @param status
     */
    public final void setGameAtrib(int framerate, int gamewindowwidth, int gamewindowheight, GAME_STATUS status) {
        this.dfps = framerate;
        this.prevgamestatus = this.gamestatus;
        this.gamestatus = status;
        this.resizeGame(gamewindowwidth, gamewindowheight);
        fireEvent(EVENT_TYPE.STATE_CHANGE, this.prevgamestatus, this.gamestatus);
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
        this.holder.getGraphics().setColor(drawcolor);
    }

    /**
     *
     */
    public final void resetDrawColor() {
        this.holder.getGraphics().setColor(drawcolor);
    }

    /**
     *
     */
    public final void resetAffineTransform() {
        this.holder.getGraphics().setTransform(affinetransform);
    }

    /**
     *
     */
    public final void resetFont() {
        this.holder.getGraphics().setFont(font);
    }

    /**
     *
     */
    public final void resetGraphics() {
        this.resetDrawColor();
        this.resetAffineTransform();
        this.resetFont();
    }

    //Functions
    /**
     *
     */
    public void pausegame() {
        this.gamepausedat = this.gametime;
        this.setGameStatus(GAME_STATUS.GAME_PAUSED);
    }

    /**
     *
     */
    public void unpausegame() {
        this.gamepausetime += this.gametime - this.gamepausedat;
        this.gamelastpausedat = this.gametime;
        this.gamepausedat = 0;
        this.setGameStatus(GAME_STATUS.GAME_PAUSED);
    }

    public final void paint() {
        framenum++;
        if (System.currentTimeMillis() > frametime + 1000) {
            frametime = System.currentTimeMillis();
            fps = framenum;
            framenum = 0;
        }
        
        this.holder.clearBackbuffer();
        
        resetGraphics();
        
        switch (this.gamestatus) {
            case GAME_MENU:
                gameMenuPaint(holder.getGraphics());
                break;
            case GAME_PAUSED:
                gamePausePaint(holder.getGraphics());
                break;
            case GAME_RUNNING:
                gamePaint(holder.getGraphics());
                break;
            case GAME_STOPPED:
                gameStoppedPaint(holder.getGraphics());
                break;
            default:
                paintError("Invalid Game Mode.");
                break;
        }
        
        resetGraphics();
        paintGameData();
        
        this.holder.flip();
    }

    /**
     *
     */
    public void paintGameData() {
        if (showgamedata) {
            holder.getGraphics().drawString("FPS: " + this.fps, 10, this.getGameWinHeight() - 10);
            holder.getGraphics().drawString("Sups: " + this.spriteholder.getSups(), 100, this.getGameWinHeight() - 10);
        }
    }

    /**
     *
     * @param desc
     */
    public void paintError(String desc) {
        String message = "Error: " + desc;
        Color prevc = this.holder.getGraphics().getColor();
        this.holder.getGraphics().setColor(Color.red);
        this.holder.getGraphics().drawString(message, this.getGameWinWidthCenter() - (10 + (this.holder.getGraphics().getFontMetrics().stringWidth(message) / 2)), this.getGameWinHeightCenter());
        this.holder.getGraphics().setColor(prevc);
    }

    /**
     *
     * @param e
     */
    public void paintError(Exception e) {
        String message = "Error: " + e.getLocalizedMessage();
        Color prevc = this.holder.getGraphics().getColor();
        this.holder.getGraphics().setColor(Color.red);
        this.holder.getGraphics().drawString(message, this.getGameWinWidthCenter() - (10 + (this.holder.getGraphics().getFontMetrics().stringWidth(message) / 2)), this.getGameWinHeightCenter());
        this.holder.getGraphics().setColor(prevc);
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
        this.holder.resize(winw, winh);
        if (mouse != null) {
            mouse.clear();
        }
    }

    public final void init() {

        System.out.print("Loading.");

        //  Set Game Atributes
        this.setGameAtrib(fps, winw, winh, GAME_STATUS.GAME_STARTING);

        //  Resources
        this.mouse = new JMouseX();
        this.mouse.addEventListener(this);
        this.keyboard = new JKeyboardX();
        this.keyboard.addEventListener(this);
        this.images = new JImageHandlerX();
        this.spriteholder = new JSpriteHolderX(this);

        //  Listeners
        this.addListener(spriteholder);
        
        this.start();

    }

    public final void start() {

        //  Start the game thread
        this.gamemain = new Thread(this);
        this.gamemain.start();

        //  Start-up Sprite holder
        this.spriteholder.start();

        //  Finally pass on control for pre-game stuff
        gameStart();

        // Timing Stuff
        this.gamestarttime = System.currentTimeMillis();
        this.frametime = System.currentTimeMillis();

        System.out.println("\nStarted!");

    }

    @Override
    public final void run() {
        Thread current = Thread.currentThread();
        while (current == this.gamemain) {
            try {
                Thread.sleep(1000 / this.dfps);
            }
            catch (InterruptedException e) {
                this.holder.getGraphics().drawString(e.getLocalizedMessage(), 0, 0);
            }
            this.gametime = System.currentTimeMillis() - gamestarttime;
            switch (this.gamestatus) {
                case GAME_MENU:
                    gameMenu();
                    break;
                case GAME_RUNNING:
                    gameUpdate();
                    break;
                case GAME_PAUSED:
                    gamePaused();
                    break;
                case GAME_STOPPED:
                    stop();
                    break;
                case GAME_STARTING:
                    System.out.print(".");
                    break;
                default:
                    System.err.println("Invalid game mode.");
                    break;
            }
            paint();
        }
    }

    public final void stop() {
        gamemain = null;
        this.spriteholder.stop();
        this.gameEnd();
    }

    @Override
    public void updateIO() {
        // Ummm...
    }

    @Override
    public void lostFocus(EventObject e) {
        if (this.gamestatus == GAME_STATUS.GAME_RUNNING) {
            this.setGameStatus(GAME_STATUS.GAME_PAUSED);
        }
    }

    synchronized protected void addListener(JGameEngineListenerX listener) {
        if (!this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }
    }

    synchronized protected void removeListener(JGameEngineListenerX listener) {
        if (this.listeners.contains(listener)) {
            this.listeners.remove(listener);
        }
    }

    synchronized protected void fireEvent(EVENT_TYPE type, Object... args) {
        switch (type) {
            case STATE_CHANGE:
                for (JGameEngineListenerX e : this.listeners) {
                    e.gameStateChanged((GAME_STATUS)(args[0]), (GAME_STATUS)(args[1]));
                }
                break;
            default:
                break;
        }
    }

    public JGameEngineX(String mode) throws HeadlessException {
        this(mode, 50, 640, 480);
    }
    
    public JGameEngineX(String mode, int fps, int width, int height) throws HeadlessException {
        //  Set Game Atributes
        switch(mode) {
            case "windowed":
                this.holder = new JWindowHolderX(width, height);
                break;
            default:
                throw new UnsupportedOperationException("Modes other than windowed not yet supported at this time");
        }
        this.setGameAtrib(fps, width, height, GAME_STATUS.GAME_STARTING);
    }

}