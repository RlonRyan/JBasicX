/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetX;

import JNetX.JPacketX.JPackectX;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryan
 */
public class JHostConnectionX extends Thread {

    private JHostX host;
    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;
    private JConnectionStateX state;
    private long timeout = 100000;
    private long interval = 100;
    private List<JPackectX> stack;

    public void queuePacket(JPackectX packet) {
        this.stack.add(packet);
    }

    public JHostConnectionX(JHostX host, Socket socket) {
        super("JHostConnectionX");
        this.host = host;
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream());
        }
        catch (IOException e) {
            System.err.println("Something went wrong connecting to client " + this.state.toString() + ".");
        }

        this.stack = new ArrayList<>();
        this.out.println("Connection established!");
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
                    this.out.println(stack.remove(0).encode());
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
            this.in = null;
            this.out = null;
            this.socket = null;
            this.host.notifyListeners(new JPackectX(JPackectX.PACKET_TYPE.TERMINATE, ""));
        }
    }
}
