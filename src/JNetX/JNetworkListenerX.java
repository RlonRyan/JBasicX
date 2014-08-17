/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX;

import JNetX.JPacketX.JPackectX;

/**
 *
 * @author Ryan
 */
public interface JNetworkListenerX {

    /**
     *
     * @param packet
     */
    public void onPacket(JPackectX packet);

    /**
     *
     */
    public void onError();

    /**
     *
     */
    public void onTimeout();

    /**
     *
     */
    public void onTerminate();
}
