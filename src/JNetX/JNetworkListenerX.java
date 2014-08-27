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
@Deprecated
public interface JNetworkListenerX {

    /**
     *
     * @param packet
     */
    default public void onPacket(JPackectX packet){return;};

    /**
     *
     */
    default public void onError(){return;};

    /**
     *
     */
    default public void onTimeout(){return;};

    /**
     *
     */
    default public void onTerminate(){return;};
}
