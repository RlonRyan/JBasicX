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
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RlonRyan
 * @name JGameX
 */
public class JGameEngineX implements Runnable {

    public final String title;

    public JSpriteHolderX spriteholder;
    public JMouseX mouse;
    public JKeyboardX keyboard;
    public JEventBinderX binder;
    public JImageHandlerX images;

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

    // Initializers
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

    public final JGameModeX getGameMode(String mode) {
	return this.modes.get(mode);
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
	this.modes.get(this.mode).stop();
	this.mode = mode.toLowerCase();
	this.modes.get(this.mode).start();
	this.mouse.setBindings(this.modes.get(this.mode).bindings);
	this.keyboard.setBindings(this.modes.get(this.mode).bindings);
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

    public final void toggleGameDataVisable() {
	this.showgamedata = !this.showgamedata;
    }

    /**
     *
     */
    public final void resetFont() {
	this.holder.getGraphics().setFont(font);
    }

    public final void resetGraphics() {
	this.holder.resetGraphics();
    }

    // Paint
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
	    System.out.print("Initalizing Modes...");

	    for (String k : modes.keySet()) {
		modes.get(k).init();
	    }

	    System.out.println("Modes Initialized!");
	    System.out.print("Initalizing Bindings...");

	    for (String key : modes.keySet()) {
		modes.get(key).registerBindings();
	    }

	    System.out.println("Modes Initialized!");
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

    public final void stop() {
	gamemain = null;
	this.spriteholder.stop();
    }

}
