package JGameEngineX;

import JBasicX.JImageHandlerX;
import JEventX.JEventBinderX;
import JGameEngineX.JGameModeX.JGameModeX;
import JGameHolderX.JAppletHolderX;
import JGameHolderX.JGameHolderX;
import JGameHolderX.JWindowHolderX;
import JIOX.JKeyboardX;
import JIOX.JMouseX.JMouseX;
import JSpriteX.JSpriteHolderX;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * JGameEngineX
 * <p>
 * @author Ryan
 */
public class JGameEngineX implements Runnable {

    /**
     * The title of the game.
     */
    public final String title;

    /**
     * The game sprite manager.
     */
    public JSpriteHolderX spriteholder;

    /**
     * The game mouse tracker.
     */
    public JMouseX mouse;

    /**
     * The game keyboard tracker.
     */
    public JKeyboardX keyboard;

    /**
     * The game event binder.
     */
    public JEventBinderX binder;

    /**
     * The game image resource handler.
     */
    public JImageHandlerX images;

    private String mode;
    private String prevmode;
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

    // Initializers
    /**
     * Creates a new instance of the game engine to run a client game.
     * <p>
     * @param title  The title of the game.
     * @param mode   The display mode of the game. Example: "windowed" or
     *               "applet"
     * @param fps    The desired screen refresh rate. Example: 60
     * @param width  The desired width of the game screen. Example: 640
     * @param height The desired height of the game screen. Example: 480
     * <p>
     * @throws HeadlessException
     */
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

    //Acessors
    /**
     * @return The name of the currently active game mode.
     */
    public final String getMode() {
	return this.mode;
    }

    /**
     * Returns a JGameModeX with the given name, or null if no mode is found
     * matching the given name.
     * <p>
     * @param modename The name of the mode to retrieve.
     * <p>
     * @return A JGameModeX with the mode name or null.
     */
    public final JGameModeX getGameMode(String modename) {
	return this.modes.get(modename);
    }

    /**
     * Retrieves the game window's dimensions.
     * <p>
     * @return A rectangle representing the game's screen bounds.
     */
    public final Rectangle2D getDimensions() {
	return this.dimensions;
    }

    /**
     * Returns the frames per second drawn to the screen (framerate).
     * <p>
     * @return The game's framerate.
     */
    public final int getFPS() {
	return this.fps;
    }

    /**
     * Returns the game's desired frames per second rate (desired framerate).
     * <p>
     * @return The game's desired framerate.
     */
    public final double getDFPS() {
	return this.dfps;
    }

    /**
     * Returns the game's graphics, which point to the backbuffer.
     * <p>
     * @return The game's primary graphics.
     */
    public final Graphics2D getGameGraphics() {
	return this.holder.getGraphics();
    }

    /**
     * @return
     */
    public final boolean isGameDataVisible() {
	return this.showgamedata;
    }

    public final boolean hasGameMode(String modename) {
	return this.modes.containsKey(modename);
    }

    //Mutators
    /**
     *
     * @param mode
     */
    public final void registerGameMode(JGameModeX mode) {
	this.modes.put(mode.name.toLowerCase(), mode);
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
	this.prevmode = this.mode;
	this.modes.get(this.mode).stop();
	this.mode = mode.toLowerCase();
	this.modes.get(this.mode).start();
	this.mouse.setBindings(this.modes.get(this.mode).bindings);
	this.keyboard.setBindings(this.modes.get(this.mode).bindings);
    }

    public final void previousGameMode() {
	setGameMode(this.prevmode);
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
    public final void toggleGameDataVisable() {
	this.showgamedata = !this.showgamedata;
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
	this.holder.resetGraphics();
    }

    // Paint
    /**
     *
     */
    public final void paint() {
	framenum++;
	if (System.currentTimeMillis() > frametime + 1000) {
	    frametime = System.currentTimeMillis();
	    fps = framenum;
	    framenum = 0;
	}

	this.holder.clearBackbuffer();
	this.holder.resetGraphics();
	this.modes.get(mode).paint(this.holder.getGraphics());
	if (this.showgamedata) {
	    this.paintGameData();
	}
	this.holder.flip();
    }

    /**
     *
     */
    public void paintGameData() {
	this.holder.resetGraphics();
	this.modes.get(mode).paintGameData(this.holder.getGraphics());
	this.holder.resetGraphics();
	if (showgamedata) {
	    int h = holder.getGraphics().getFontMetrics().getHeight();
	    holder.getGraphics().setColor(Color.WHITE);
	    holder.getGraphics().drawRoundRect(10, (int) (this.dimensions.getMaxY() - 1.5 * h), (int) this.dimensions.getWidth() - 20, (int) this.dimensions.getWidth() - 20, (int) this.dimensions.getWidth() / 25, (int) this.dimensions.getHeight() / 25);
	    holder.getGraphics().setColor(new Color(150, 150, 150, 100));
	    holder.getGraphics().fillRoundRect(10, (int) (this.dimensions.getMaxY() - 1.5 * h), (int) this.dimensions.getWidth() - 20, (int) this.dimensions.getWidth() - 20, (int) this.dimensions.getWidth() / 25, (int) this.dimensions.getHeight() / 25);
	    holder.getGraphics().setColor(Color.WHITE);
	    holder.getGraphics().drawString("FPS: " + this.fps, 25, (int) this.dimensions.getHeight() - 10);
	    holder.getGraphics().drawString("Sups: " + this.spriteholder.getSups(), 125, (int) this.dimensions.getHeight() - 10);
	}
	holder.resetGraphics();
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

    // Run
    /**
     * Begins game execution.
     * Initializes run-time prerequisites. Starts with core io, followed by the
     * game modes.
     */
    public final void init() {

	System.out.println(title + " is initializing!");
	System.out.println();
	System.out.print("Initializing Core...");

	long init_time = System.currentTimeMillis();
	Timer progressor = new Timer();

	progressor.scheduleAtFixedRate(new TimerTask() {
	    @Override
	    public void run() {
		System.out.print(".");
	    }
	}, 0l, 1000l);

	try {

	    //  Resources
	    this.mouse = new JMouseX();
	    this.holder.addMouseListener(this.mouse);

	    this.keyboard = new JKeyboardX();
	    this.holder.addKeyListener(this.keyboard);

	    this.images = new JImageHandlerX(this.getClass());
	    this.spriteholder = new JSpriteHolderX(this);

	    System.out.println("Core Initialized!");
	    System.out.print("Initalizing Modes...\n");

	    Iterator<Map.Entry<String, JGameModeX>> it = modes.entrySet().iterator();

	    it.forEachRemaining((e) -> {
		System.out.print("\tInitialing Mode: " + e.getKey() + "...");
		if (e.getValue().init()) {
		    System.out.println("Initialized.");
		}
		else {
		    System.out.println("Failed.");
		    System.out.print("\t\tRemoving Mode: " + e.getKey() + "...");
		    it.remove();
		    System.out.println("Removed.");
		}
	    });

	    System.out.println("Modes Initialized!");
	    System.out.print("Initalizing Bindings...");

	    for (String key : modes.keySet()) {
		modes.get(key).registerBindings();
	    }

	    System.out.println("Initialized!");
	    progressor.cancel();
	    System.out.println();
	    System.out.println("Initialized! (" + (System.currentTimeMillis() - init_time) + " ms)");
	}
	catch (Exception e) { //The buck stops here
	    progressor.cancel();
	    System.out.println("Failed!");
	    System.out.println("");
	    System.out.println("The error reports:");
	    e.printStackTrace();
	}

    }

    /**
     * Follows initialize, starts actual execution.
     * Creates the main game thread.
     * <p>
     * @param mode The name of the mode to start with.
     */
    public final void start(String mode) {

	System.out.print("Starting...");

	long start_time = System.currentTimeMillis();
	Timer progressor = new Timer();

	progressor.scheduleAtFixedRate(new TimerTask() {
	    @Override
	    public void run() {
		System.out.print(".");
	    }
	}, 0l, 1000l);

	this.mode = mode.toLowerCase();
	this.modes.get(this.mode).start();
	this.mouse.setBindings(this.modes.get(this.mode).bindings);
	this.keyboard.setBindings(this.modes.get(this.mode).bindings);

	this.gamemain = new Thread(this);
	this.gamemain.start();

	// Timing Stuff
	this.frametime = System.currentTimeMillis();

	//  Start-up Sprite holder
	this.spriteholder.start();

	progressor.cancel();
	System.out.println("Started! (" + (System.currentTimeMillis() - start_time) + " ms)");

    }

    /**
     *
     */
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

	    if (this.mode != null && this.modes.get(mode) != null) {
		this.modes.get(mode).update();
	    }
	    paint();
	}
    }

    /**
     *
     */
    public final void stop() {
	gamemain = null;
	this.spriteholder.stop();
    }

}
