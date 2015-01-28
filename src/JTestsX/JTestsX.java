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
import org.junit.*;

/**
 *
 * @author Ryan
 */
public class JTestsX {

    @Test
    public void testCreateDrawing() {

        JGameEngineX testGameEngine = new JGameEngineX("Tester", "windowed", 60, 640, 480);

        JGameModeX testGameMode = new JDummyGameModeX("testMode", testGameEngine) {
            @Override
            public void paint(Graphics2D g2d) {
                g2d.setColor(Color.green);
                g2d.draw3DRect((int) (holder.getDimensions().getWidth() / 2) - (100 / 2), (int) (holder.getDimensions().getHeight() / 2) - (100 / 2), 100, 100, true);
            }
        };

        testGameEngine.registerGameMode(testGameMode);
        testGameEngine.init();
        testGameEngine.start("testMode");

        wait(1);

        testGameEngine.stop();
    }

    @Test
    public void testCreateMenu() {

        JGameEngineX testGameEngine = new JGameEngineX("Tester", "windowed", 60, 640, 480);
        JMenuX testMenu = new JMenuX("Menu", 100, 100, 440, 280, "element 1", "element 2", "element 3");

        JDummyGameModeX testGameMode = new JDummyGameModeX("testMode", testGameEngine) {
            @Override
            public void start() {
                testMenu.open();
            }

            @Override
            public void paint(Graphics2D g2d) {
                testMenu.paint(testGameEngine.getGameGraphics());
                testMenu.paintBounds(testGameEngine.getGameGraphics());
            }
        };

        testGameEngine.registerGameMode(testGameMode);

        testGameEngine.init();
        testGameEngine.start("testMode");

        wait(1);

        testGameEngine.stop();
    }

    private void wait(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            //ignore
        }
    }

}
