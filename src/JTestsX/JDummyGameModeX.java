/*
 * JBasicX
 * By RlonRyan
 *
 * All rights reserved. No warrenty on use. User accepts all risks and damages.
 * Class: JDummyGameModeX
 * Description: JDummyGameModeX
 */
package JTestsX;

import JGameEngineX.JGameEngineX;
import JGameEngineX.JGameModeX.JGameModeX;
import java.awt.Graphics2D;

/**
 *
 * @author Ryan
 */
public class JDummyGameModeX extends JGameModeX {

    public JDummyGameModeX(String name, JGameEngineX holder) {
	super(name, holder);
    }

    @Override
    public boolean init() {
	return true;
    }

    @Override
    public void registerBindings() {

    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }

    @Override
    public void paint(Graphics2D g2d) {

    }

    @Override
    public void paintGameData(Graphics2D g2d) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

}
