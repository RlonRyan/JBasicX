/*
 * @author RlonRyan
 * @name JSoundX
 * @version 1.0.0
 * @date Dec 23, 2011
 * @info Sound class.
 * 
 */
package JIOX;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*
 * @author RlonRyan
 * @name JSoundX
 */

/**
 *
 * @author Ryan
 */

public final class JSoundX {

    private Class holder;
    private String file;
    private AudioInputStream ais;
    private Clip clip;
    private long pausedat;

    /**
     *
     * @return
     */
    public String getFile() {
	return file;
    }

    /**
     *
     * @param file
     */
    public void setFile(String file) {
	this.file = file;
    }

    /**
     *
     */
    public final void load() {
	try {
	    ais = AudioSystem.getAudioInputStream(this.holder.getResource(this.file));
	    clip = AudioSystem.getClip();
	    clip.open(ais);
	}
	catch (Exception e) {
	    System.err.printf("A %1$s occured while loading the sound: %2$s.\n", e.getClass().getSimpleName(), this.file);
	    e.printStackTrace();
	}
    }

    /**
     *
     */
    public final void play() {
	if (!this.clip.isRunning()) {
	    this.clip.setMicrosecondPosition(0);
	    this.clip.start();
	}
    }

    /**
     *
     */
    public final void pause() {
	if (this.clip.isRunning()) {
	    this.pausedat = this.clip.getMicrosecondPosition();
	    this.clip.stop();
	}
    }

    /**
     *
     */
    public final void resume() {
	if (!this.clip.isRunning()) {
	    this.clip.setMicrosecondPosition(pausedat);
	    this.clip.start();
	    this.pausedat = 0;
	}
    }

    /**
     *
     */
    public final void stop() {
	if (this.clip.isRunning()) {
	    this.clip.stop();
	}
    }

    /**
     *
     * @param holder
     * @param file
     */
    public JSoundX(Class holder, String file) {
	this.holder = holder;
	this.file = file;
	this.pausedat = 0;
	this.load();
    }
}
