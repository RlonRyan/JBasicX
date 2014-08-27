/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX;

import java.net.InetAddress;


/**
 *
 * @author Ryan
 */
public class JConnectionX {
    
    public final InetAddress address;
    public final int port;
    
    private long lastHeartbeat;
    private long timeout;

    public JConnectionX(InetAddress address, int port) {
	this(address, port, 1000);
    }
    
    public JConnectionX(InetAddress address, int port, long timeout) {
	this.address = address;
	this.port = port;
	this.timeout = timeout;
	this.lastHeartbeat = System.currentTimeMillis();
    }
    
    public long getTimeSinceLastHeartbeat() {
	return System.currentTimeMillis() - lastHeartbeat;
    }
    
    public void onHeartbeat() {
	this.lastHeartbeat = System.currentTimeMillis();
    }
    
}
