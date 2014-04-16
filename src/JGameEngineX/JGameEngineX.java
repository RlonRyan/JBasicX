/**
 * @author RlonRyan
 * @name JGameX
 * @version 1.0.2
 * @date September 11th, 2011
 *
 */
package JGameEngineX;

import JBasicX.JImageHandlerX;
import JEventX.JEventBinderX;
import JGameEngineX.JGameModeX.JGameModeX;
import JGameHolderX.JAppletHolderX;
import JGameHolderX.JGameHolderX;
import JGameHolderX.JWindowHolderX;
import JIOX.JInputOutputX;
import JIOX.JKeyboardX;
import JIOX.JMouseX.JMouseX;
import JSpriteX.JSpriteHolderX;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RlonRyan
 * @name JGameX
 */
public abstract class JGameEngineX implements Runnable, JInputOutputX {

    public final String title;

    protected JSpriteHolderX spriteholder;
    protected JMouseX mouse;
    protected JKeyboardX keyboard;
    protected JEventBinderX binder;

    protected JImageHandlerX images;

    private String mode;
    private JGameHolderX holder;
    private Rectangle2D dimensions;
    private Thread gamemain;
    private int framenum = 0;
    private int fps = 0;
    private long frametime = 0;
    private long dfps = 50;
    private boolean showgamedata = false;

    private Font font = new Font("Arial", Font.PLAIN, 10);

    private HashMap<String, JGameModeX> modes;

    private List<JGameEngineListenerX> listeners = new ArrayList<>();

    //Acessors
    /**
     * Method for getting the
     * <code>gamestatus</code>variable.
     * <p/>
     * @return <code>gamestatus</code><br/>0 = stopped.<br/>1 = menu.<br/>2 =
     *         running.<br/>3 = paused.
     */
    public final String getGameMode() {
	return this.mode;
    }

    public final Rectangle2D getDimensions() {
	return this.dimensions;
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
     *
     * @return
     */
    public final boolean isGameDataVisible() {
	return this.showgamedata;
    }
    //Mutators

    public final void addGameMode(String name, JGameModeX mode) {
	this.modes.put(name, mode);
    }

    /**
     *
     * @param color
     */
    public final void setBackgroundColor(Color color) {
	this.holder.setBackgroundColor(color);
    }

    /**
     *
     * @param mode
     */
    public final void setGameMode(String mode) {
	this.mode = mode;
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

    // Function
    public final void paint() {
	framenum++;
	if (System.currentTimeMillis() > frametime + 1000) {
	    frametime = System.currentTimeMillis();
	    fps = framenum;
	    framenum = 0;
	}

	this.holder.clearBackbuffer();

	this.modes.get(mode).paint(this.holder.getGraphics());

	this.paintGameData();

	this.holder.flip();
    }

    /**
     *
     */
    public void paintGameData() {
	if (showgamedata) {
	    holder.getGraphics().drawString("FPS: " + this.fps, 25, (int) this.dimensions.getHeight() - 10);
	    holder.getGraphics().drawString("Sups: " + this.spriteholder.getSups(), 125, (int) this.dimensions.getHeight() - 10);
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
	this.holder.getGraphics().drawString(message, (int) this.dimensions.getCenterX() - (10 + (this.holder.getGraphics().getFontMetrics().stringWidth(message) / 2)), (int) this.dimensions.getCenterY());
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
	this.holder.getGraphics().drawString(message, (int) this.dimensions.getCenterX() - (10 + (this.holder.getGraphics().getFontMetrics().stringWidth(message) / 2)), (int) this.dimensions.getCenterY());
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
	
	Timer progressor = new Timer();
	
	progressor.scheduleAtFixedRate(new TimerTask() {
	    @Override
	    public void run() {
		System.out.print(".");
	    }
	}, 0l, 1000l);

	//  Resources
	this.binder = new JEventBinderX();
	
	this.mouse = new JMouseX(this);
	this.holder.addMouseListener(this.mouse);
	this.mouse.addEventListener(this);
	
	this.keyboard = new JKeyboardX();
	this.holder.addKeyListener(this.keyboard);
	this.keyboard.addEventListener(this);
	
	this.images = new JImageHandlerX(this.getClass());
	this.spriteholder = new JSpriteHolderX(this);
	
	for(String key : modes.keySet()) {
	    modes.get(key).initialize();
	}

	//  Listeners
	this.addListener(spriteholder);

	progressor = null;
	
	System.out.println("Initialized!");

	this.gamemain = new Thread(this);
	this.gamemain.start();
    }

    public final void start() {
	System.out.print("Starting.");

	// Timing Stuff
	this.frametime = System.currentTimeMillis();

	//  Start-up Sprite holder
	this.spriteholder.start();

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
	    paint();
	}
    }

    public final void stop() {
	gamemain = null;
	this.spriteholder.stop();
    }

    @Override
    public void updateIO() {
	// Ummm...
    }

    @Override
    public void lostFocus(EventObject e) {
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

    public JGameEngineX(String title, String mode) throws HeadlessException {
	this(title, mode, 50, 640, 480);
    }

    public JGameEngineX(String title, String mode, int fps, int width, int height) throws HeadlessException {
	//  Set Game Atributes
	this.modes = new HashMap();
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
    
    public abstract void initalizeAddons();
    
    public abstract void registerModes();
    
    public abstract void start();
    
}
