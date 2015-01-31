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
import JSpriteX.JSpriteHolderX;
import java.awt.Color;
import java.awt.Graphics2D;
import org.junit.*;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Ryan
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JTestsX {

    @Test
    public void testCreateDrawing() {

        JGameEngineX testGameEngine = new JGameEngineX("testCreateDrawing", "dummy", 60, 640, 480);

        JGameModeX testGameMode = new JDummyGameModeX("testMode", testGameEngine) {

            int size = 100;
            int rot = -15;

            @Override
            public void paint(Graphics2D g2d) {

                size = size + 50;
                rot = rot + 15;

                int xpos = (int) (holder.getDimensions().getWidth() / 2);
                int ypos = (int) (holder.getDimensions().getHeight() / 2);

                int radius = (int) Math.sqrt(((size / 2) * (size / 2)) * 2);

                g2d.translate(xpos, ypos);
                g2d.rotate(Math.toRadians(rot));

                g2d.setColor(Color.red);
                g2d.drawOval(-radius, -radius, radius * 2, radius * 2);
                g2d.setColor(Color.green);
                g2d.drawRect(-(size / 2), -(size / 2), size, size);
                g2d.setColor(Color.blue);
                g2d.drawLine(-(size / 2), -(size / 2), (size / 2), (size / 2));
                g2d.drawLine((size / 2), -(size / 2), -(size / 2), (size / 2));

                g2d.rotate(-Math.toRadians(rot));
                g2d.translate(-xpos, -ypos);
            }
        };

        testGameEngine.registerGameMode(testGameMode);
        testGameEngine.init();
        testGameEngine.setGameMode(testGameMode.name);

        for (int i = 0; i < 10; i++) {
            testGameMode.update();
            testGameEngine.paint();
        }

        testGameEngine.stop();
    }

    @Test
    public void testCreateMenu() {

        JGameEngineX testGameEngine = new JGameEngineX("testCreateMenu", "dummy", 60, 640, 480);
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
                testMenu.incrementHighlight();
            }
        };

        testGameEngine.registerGameMode(testGameMode);

        testGameEngine.init();
        testGameEngine.setGameMode(testGameMode.name);

        for (int i = 0; i < 10; i++) {
            testGameMode.update();
            testGameEngine.paint();
        }

        testGameEngine.stop();
    }

    @Test
    public void testCreateSprites() {

        JGameEngineX testGameEngine = new JGameEngineX("testCreateSprites", "dummy", 60, 640, 480);

        JDummyGameModeX testGameMode = new JDummyGameModeX("testMode", testGameEngine) {

            JSpriteHolderX spriteholder;

            @Override
            public boolean init() {
                spriteholder = new JSpriteHolderX(holder);
                return true;
            }

            @Override
            public void start() {
                for (int i = 0; i < 90; i++) {
                    spriteholder.addSprite(JSpriteHolderX.SPRITE_BOUNCER, i * 4, i * 4, i, holder.getDimensions().getCenterX() - 25, holder.getDimensions().getCenterY() - 25);
                }
            }

            @Override
            public void update() {
                spriteholder.updateSprites();
            }

            @Override
            public void paint(Graphics2D g2d) {
                spriteholder.paintSprites(g2d);
                spriteholder.paintSpriteBounds(g2d);
            }

        };

        testGameEngine.registerGameMode(testGameMode);

        testGameEngine.init();
        testGameEngine.setGameMode(testGameMode.name);

        for (int i = 0; i < 25; i++) {
            testGameMode.update();
            testGameEngine.paint();
        }

        testGameEngine.stop();
    }

    private void wait(int sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException e) {
            //ignore
        }
    }

}
