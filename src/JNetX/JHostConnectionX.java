/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX;

import JNetX.JPacketX.JPackectX;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryan
 */
public class JHostConnectionX extends Thread {

    private JHostX host;
    private InetAddress address;
    private int port;
    private DatagramSocket socket = null;
    private JConnectionStateX state;
    private long timeout = 100000;
    private long interval = 100;
    private List<JPackectX> stack;

    public void queuePacket(JPackectX packet) {
        this.stack.add(packet);
    }

    public JHostConnectionX(JHostX host, InetAddress address, int port) {
        super("JHostConnectionX");
        this.host = host;
        this.port = port;
        this.address = address;
        this.stack = new ArrayList<>();
        System.out.println("Connection with: " + socket.getInetAddress().toString() + " established!");
        this.state = JConnectionStateX.ACTIVE;
    }

    @Override
    public void run() {

        long time = 0;
        String data;

        while (this.state == JConnectionStateX.ACTIVE) {
            try {
                sleep(interval);
                time += interval;

                /*
                 *  Distribute Packets!
                 */
                while(!stack.isEmpty()) {
                    socket.send(new DatagramPacket(stack.remove(0).getData(), 256, this.address, this.port));
                }

                /*
                 *  Check for any messages.
                 */
                while (in.ready() && (data = in.readLine()) != null) {
                    time = 0;
                    this.host.notifyListeners(new JPackectX(data));
                    System.out.print("Got a packet!");
                }

                /*
                 *  Check to see if the client has timed out.
                 */
                if (time > timeout) {
                    this.state = JConnectionStateX.TIMED_OUT;
                }
            }
            catch (InterruptedException e) {
                // Who dares wake me from my slumbers!
            }
            catch (IOException e) {
                //  umm...
            }
        }

        try {
            out.println("Connection " + this.state.toString());
        }
        catch (Exception e) {
            /*
             * At this point, you might as well let the whole thing blow up.
             */
        }

        close();

    }

    public void close() {
        try {
            this.out.println(new JPackectX(JPackectX.PACKET_TYPE.TERMINATE, "Connection Terminated."));
            this.in.close();
            this.out.close();
            this.socket.close();
        }
        catch (Exception e) {
            /*
             * Give up!
             */
        }
        finally {
            this.socket = null;
            this.host.notifyListeners(new JPackectX(JPackectX.PACKET_TYPE.TERMINATE, ""));
        }
    }
}
