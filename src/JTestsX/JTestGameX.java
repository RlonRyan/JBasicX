/*
 * JBasicX
 * By RlonRyan
 *
 * All rights reserved. No warrenty on use. User accepts all risks and damages.
 * Class: JTestGameX
 * Description: JTestGameX
 */
package JTestsX;

import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameModeX.JGameModeX;
import java.awt.Color;
import java.awt.Graphics2D;
import org.junit.*;

/**
 *
 * @author Ryan
 */
public class JTestGameX {

    public JGameEngineX testGameEngine;
    public JGameModeX testGameMode;

    @Test
    public void testCreateEngine() {
	testGameEngine = new JGameEngineX("testGame", "windowed", 60, 640, 480);
    }

    @Test
    public void testCreateGameMode() {
	testGameMode = new JDummyGameModeX("testMode", testGameEngine) {
	    @Override
	    public void paint(Graphics2D g2d) {
		g2d.setColor(Color.green);
		g2d.draw3DRect((int) (holder.getDimensions().getWidth() / 2) - (100 / 2), (int) (holder.getDimensions().getHeight() / 2) - (100 / 2), 100, 100, true);
	    }
	};
    }

    @Test
    public void testRegisterGameMode() {
	testGameEngine.registerGameMode(testGameMode);
    }

    @Test
    public void testRegisterBindings() {

    }

    @Test
    public void testGameInit() {
	testGameEngine.init();
    }

    @Test
    public void testGameExecute() {

	// Start 'er up!
	testGameEngine.start("testMode");

	try {
	    Thread.sleep(5 * 1000);
	}
	catch (InterruptedException e) {
	    //Ignore
	}

	// Stop Hammertime!
	testGameEngine.stop();
    }

}
