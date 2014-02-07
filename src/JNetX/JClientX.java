/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX;

import JNetX.JNetEventX.JNetEventTypeX;
import JNetX.JPacketX.JPackectX;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Ryan
 */
public class JClientX extends Thread {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private JConnectionStateX state;
    private List<JNetworkListenerX> listeners;
    private byte id;
    private BitSet acks;
    private byte ack;
    private HashMap<Integer, JPackectX> sent;

    public final InetAddress address;
    public final int port;

    public JClientX(InetAddress address, int port) {
        this(address, port, 10000);
    }

    public JClientX(InetAddress address, int port, int timeout) {
        this.address = address;
        this.port = port;

        try {
            this.state = JConnectionStateX.INVALID;
            this.socket = new DatagramSocket(port, address);
            this.socket.setSoTimeout(timeout);
            this.acks = new BitSet(256);
            this.ack = 0;
            this.state = JConnectionStateX.ACTIVE;
        } catch (SocketException e) {
            System.err.println("Well that's great...");
        }

    }

    public boolean sendPacket(JPackectX packet) {
        try {
            this.socket.send(packet.send(address, port, ack, acks, id));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public JConnectionStateX getConnectionState() {
        return this.state;
    }

    @Override
    public void run() {
        try {
            this.socket.receive(packet);
            this.acks.set(packet.getData()[4], true);
            System.out.println(this.ack + ": " + this.acks.toString());
        } catch (SocketTimeoutException e) {
            System.err.println("Connection with " + this.socket.getInetAddress() + ":" + this.socket.getPort() + " timed out.");
            this.state = JConnectionStateX.TIMEDOUT;
            this.notifyListeners(JNetEventTypeX.TIMEOUT);
            this.close();
        } catch (IOException e) {
            System.err.println("Well that's great...");
            this.state = JConnectionStateX.INVALID;
            this.notifyListeners(JNetEventTypeX.ERROR);
            this.close();
        }
    }

    public void close() {
        this.state = JConnectionStateX.TERMINATED;
        this.socket.close();
    }

    public void addListener(JNetworkListenerX listener) {
        this.listeners.add(listener);
    }

    public void removeListener(JNetworkListenerX listener) {
        this.listeners.remove(listener);
    }

    public void notifyListeners(JNetEventTypeX type, Object... data) {
        switch (type) {
            case PACKET_RECIEVED:
                if (data[0] instanceof JPackectX) {
                    for (JNetworkListenerX e : listeners) {
                        e.onPacket((JPackectX) data[0]);
                    }
                }
                break;

            case TIMEOUT:
                for (JNetworkListenerX e : listeners) {
                    e.onTimeout();
                }
                break;

            case ERROR:
                for (JNetworkListenerX e : listeners) {
                    e.onError();
                }
                break;

            case TERMINATE:
                for (JNetworkListenerX e : listeners) {
                    e.onTerminate();
                }
                break;

            default:
                break; // UMMMM?!?!?
        }

    }

}
