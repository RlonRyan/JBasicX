/**
 * @author  RlonRyan
 * @name    JMidiX
 * @version 1.0.0
 * @date    Dec 23, 2011
 * @info    Midi class.
**/

package JIOX;

import java.net.URL;

/**
 * @author  RlonRyan
 * @name    JMidiX
 */
public class JMidiX {

    private String file = "drwhotheme.wav";
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
        }
        catch (Exception e) {
        }
    }
}
