/**
 * RlonRyan
 ** JMidiX
 ** Dec 23, 2011
 *
 */
package JIOX;

import java.net.URL;

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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public final void load() {
        try {
        }
        catch (Exception e) {
        }
    }
}
