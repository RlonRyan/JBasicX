/*
 * JBasicX
 * By RlonRyan
 *
 * All rights reserved. No warrenty on use. User accepts all risks and damages.
 * Class: JTestsX
 * Description: JTestsX
 */
package JTestsX;

import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameModeX.JGameModeX;
import JIOX.JMenuX.JMenuX;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javafx.scene.control.MenuItem;
import org.junit.*;

/**
 *
 * @author Ryan
 */
public class JTestsX {

    JGameEngineX testGame;
    JGameModeX testMode;

    @Test
    @Before
    public void testCreateGame() {
	if (testGame == null) {
	    testGame = new JGameEngineX("Tester", "windowed", 60, 640, 480);
	}
    }

    @Test
    public void testCreateMode() {
	testMode = new JGameModeX("testMode", testGame) {

	    @Override
	    public boolean init() {
		return true;
	    }

	    @Override
	    public void registerBindings() {
		//
	    }

	    @Override
	    public void start() {
		//
	    }

	    @Override
	    public void update() {
		//
	    }

	    @Override
	    public void paint(Graphics2D g2d) {
		g2d.setColor(Color.GREEN);
		g2d.draw(new Rectangle((640 / 2) - (100 / 2), (480 / 2) - (100 / 2), 100, 100));
	    }

	    @Override
	    public void paintGameData(Graphics2D g2d) {
		//
	    }

	    @Override
	    public void pause() {
		//
	    }

	    @Override
	    public void stop() {
		//
	    }
	};

	testGame.registerGameMode(testMode);

	testGame.init();
	testGame.start(testMode.name);

	try {
	    Thread.sleep(5 * 1000);
	}
	catch (InterruptedException e) {

	}

	testGame.stop();
    }

    @Test
    @After
    public void testCreateMenu() {
	JGameEngineX testGame = new JGameEngineX("Tester", "windowed", 60, 640, 480);
	JMenuX testMenu = new JMenuX("Menu", 0, 0, 640, 480, "element 1", "element 2", "element 3");
	testMenu.paint(testGame.getGameGraphics());
	testMenu.paintBounds(testGame.getGameGraphics());
    }

}
