/*
** @Name    ${name}
** @User    ${user}
** @Version 1.0.0
** @Date    ${date}
** @Info    ${name}, a JBasicX based game by ${user}.
*/

package ${package};

import JGameEngineX.JGameEngineX;
import JSpriteX.*;
import java.awt.Graphics2D;

public class ${name} extends JGameEngineX {

    @Override
    public void gameStart() {

    }

    @Override
    public void gameEnd() {

    }

    @Override
    public void gameMenu() {

    }

    @Override
    public void gameUpdate() {

    }

    @Override
    public void gamePaused() {

    }

    @Override
    public void gamePaint(Graphics2D g2d) {

    }

    @Override
    public void gameMenuPaint(Graphics2D g2d) {
        g2d.drawString("${name}", this.getGameWinWidthCenter() - 20, this.getGameWinHeightCenter() - 20);
        g2d.drawString("GAME MENU", this.getGameWinWidthCenter() - 40, this.getGameWinHeightCenter());
    }

    @Override
    public void gamePausePaint(Graphics2D g2d) {
        g2d.drawString("${name}", this.getGameWinWidthCenter() - 20, this.getGameWinHeightCenter() - 20);
        g2d.drawString("GAME PAUSED", this.getGameWinWidthCenter() - 10, this.getGameWinHeightCenter());
        gamePaint(g2d);
    }

    @Override
    public void gameStoppedPaint(Graphics2D g2d) {
        g2d.drawString("${name}", this.getGameWinWidthCenter() - 20, this.getGameWinHeightCenter() - 20);
        g2d.drawString("GAME STOPPED", this.getGameWinWidthCenter() - 40, this.getGameWinHeightCenter());
    }

}
