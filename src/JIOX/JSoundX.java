/**
 * RlonRyan
 ** JSoundX
 ** Dec 23, 2011
 *
 */
package JIOX;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public final class JSoundX {

    private String file = "gong.wav";
    private AudioInputStream ais;
    private Clip clip;
    private long pausedat = 0;

    private URL getUrl(String filename) {
        URL url = null;
        try {
            url = this.getClass().getResource(filename);
        }
        catch (Exception e) {
        }
        return url;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public final void load() {
        try {
            ais = AudioSystem.getAudioInputStream(this.getUrl(file));
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch (Exception e) {
        }
    }

    public final void play() {
        if (!this.clip.isRunning()) {
            this.clip.setMicrosecondPosition(0);
            this.clip.start();
        }
    }

    public final void pause() {
        if (this.clip.isRunning()) {
            this.pausedat = this.clip.getMicrosecondPosition();
            this.clip.stop();
        }
    }

    public final void resume() {
        if (!this.clip.isRunning()) {
            this.clip.setMicrosecondPosition(pausedat);
            this.clip.start();
            this.pausedat = 0;
        }
    }

    public final void stop() {
        if (this.clip.isRunning()) {
            this.clip.stop();
        }
    }

    public JSoundX() {
        this.load();
    }
}
