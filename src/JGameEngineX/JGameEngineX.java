/**
 * @author RlonRyan
 * @name JGameX
 * @version 1.0.2
 * @date September 11th, 2011
 *
 */
package JGameEngineX;

import JBasicX.JImageHandlerX;
import JGameHolderX.JAppletHolderX;
import JGameHolderX.JGameHolderX;
import JGameHolderX.JWindowHolderX;
import JIOX.JInputOutputX;
import JIOX.JKeyboardX;
import JIOX.JMouseX;
import JSpriteX.JSpriteHolderX;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

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
        GAME_INTIALIZING,
        GAME_STARTING,
        GAME_MENU,
        GAME_RUNNING,
        GAME_PAUSED;
    }
// Public
    public final String title;
    /**
     *
     */
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
    private JGameHolderX holder;
    private GAME_STATUS gamestatus;
    private GAME_STATUS prevgamestatus;
    private Rectangle2D dimensions;
    private Thread gamemain;
    private int framenum = 0;
    private int fps = 0;
    private long frametime = 0;
    private long dfps = 50;
    private boolean showgamedata = false;
    private long gametime;
    private long gamestarttime;
    private long gamepausedat;
    private long gamelastpausedat = 0;
    private long gamepausetime = 0;
    private Font font = new Font("Arial", Font.PLAIN, 10);
    private List<JGameEngineListenerX> listeners = new ArrayList<>();

    /**
     * <code>gameInit</code> function to override in new instance.
     * <p/>
     * Override function is to contain game initialization code.
     * Variables should be initialized, but not set here.
     * <br/>Called by the
     * <code>init</code> function.
     */
    public abstract void gameInit();

    /**
     * <code>gameStart</code> function to override in new instance.
     * <p/>
     * Override function is to contain game startup code.
     * Starts the game playing.
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
    public final double getGameWinWidth() {
        return this.dimensions.getWidth();
    }

    /**
     * Method for getting the game screen height.
     * <p/>
     * @return The game's height.
     */
    public final double getGameWinHeight() {
        return this.dimensions.getHeight();
    }

    /**
     * Method for getting the game screen center in terms of width.
     * <p/>
     * @return The game's center in terms of width.
     */
    public final double getCenterX() {
        return this.dimensions.getCenterX();
    }

    /**
     * Method for getting the game screen center in terms of height.
     * <p/>
     * @return The game's center in terms of height.
     */
    public final double getCenterY() {
        return this.dimensions.getCenterY();
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
     *
     * @return
     */
    public final boolean isGameDataVisible() {
        return this.showgamedata;
    }
    //Mutators

    /**
     *
     */
    public final void setBackgroundColor(Color color) {
        this.holder.setBackgroundColor(color);
    }

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
     * @param dfps
     */
    public final void setDFPS(long dfps) {
        this.dfps = dfps;
    }

    /**
     *
     */
    public final void resetFont() {
        this.holder.getGraphics().setFont(font);
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

        this.paintGameData();

        this.holder.flip();
    }

    /**
     *
     */
    public void paintGameData() {
        if (showgamedata) {
            holder.getGraphics().drawString("FPS: " + this.fps, 25, (int)this.getGameWinHeight() - 10);
            holder.getGraphics().drawString("Sups: " + this.spriteholder.getSups(), 125, (int)this.getGameWinHeight() - 10);
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
        this.holder.getGraphics().drawString(message, (int)this.getCenterX() - (10 + (this.holder.getGraphics().getFontMetrics().stringWidth(message) / 2)), (int)this.getCenterY());
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
        this.holder.getGraphics().drawString(message, (int)this.getCenterX() - (10 + (this.holder.getGraphics().getFontMetrics().stringWidth(message) / 2)), (int)this.getCenterY());
        this.holder.getGraphics().setColor(prevc);
    }

    /**
     *
     * @param width
     * @param height
     */
    public final void resizeGame(int width, int height) {
        this.dimensions.setRect(0, 0, width, height);
        this.holder.resize(width, height);
        if (mouse != null) {
            mouse.clear();
        }
    }

    public final void init() {

        System.out.print("Initializing.");

        this.gamemain = new Thread(this);
        this.gamemain.start();

        //  State
        this.gamestatus = GAME_STATUS.GAME_INTIALIZING;

        //  Resources
        this.mouse = new JMouseX();
        this.holder.addMouseListener(this.mouse);
        this.mouse.addEventListener(this);
        this.keyboard = new JKeyboardX();
        this.holder.addKeyListener(this.keyboard);
        this.keyboard.addEventListener(this);
        this.images = new JImageHandlerX(this.getClass());
        this.spriteholder = new JSpriteHolderX(this);

        //  Listeners
        this.addListener(spriteholder);

        gameInit();

        System.out.println("Initialized!");

    }

    public final void start() {

        System.out.print("Starting.");

        //  Start the game thread
        this.gamestatus = GAME_STATUS.GAME_STARTING;

        // Timing Stuff
        this.gamestarttime = System.currentTimeMillis();
        this.frametime = System.currentTimeMillis();

        //  Start-up Sprite holder
        this.spriteholder.start();

        //  Finally pass on control for pre-game stuff
        gameStart();

        System.out.println("Started!");

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
                case GAME_INTIALIZING:
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
                    e.gameStateChanged((GAME_STATUS) (args[0]), (GAME_STATUS) (args[1]));
                }
                break;
            default:
                break;
        }
    }

    public JGameEngineX(String title, String mode) throws HeadlessException {
        this(title, mode, 50, 640, 480);
    }

    public JGameEngineX(String title, String mode, int fps, int width, int height) throws HeadlessException {
        //  Set Game Atributes
        this.gamestatus = GAME_STATUS.GAME_INTIALIZING;
        this.title = title;
        this.dfps = fps;
        this.dimensions = new Rectangle(0, 0, width, height);
        switch (mode.toLowerCase()) {
            case "windowed":
                this.holder = new JWindowHolderX(this.title, width, height);
                break;
            case "applet":
                this.holder = new JAppletHolderX(width, height);
                break;
            default:
                throw new UnsupportedOperationException("Modes other than windowed and applet not yet supported at this time");
        }
    }
}