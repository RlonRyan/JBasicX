/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetworkingX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Ryan
 */
public class JHostConnectionX extends Thread {

    public static enum CONNECTION_STATE {

        INACTIVE, ACTIVE, TIMED_OUT, TERMINATED, CLOSED
    };
    private final int ID;
    private JHostX host;
    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;
    private CONNECTION_STATE state;
    private long timeout = 100000;
    private long interval = 1000;

    public JHostConnectionX(JHostX host, Socket socket, int id) {
        super("JHostConnectionX");
        this.host = host;
        this.socket = socket;
        this.ID = id;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream());
        }
        catch (IOException e) {
            System.err.println("Something went wrong connecting to client " + this.state.toString() + ".");
        }

        this.out.println("Connection established!");
        this.state = CONNECTION_STATE.ACTIVE;

    }

    @Override
    public void run() {

        long time = 0;

        while (this.state == CONNECTION_STATE.ACTIVE) {
            try {
                sleep(interval);
                time += interval;
                String message;
                if (in.ready() && (message = in.readLine()) != null) {
                    time = 0;
                    this.out.println("Message recieved!");
                    if (message.equalsIgnoreCase("Terminate")) {
                        this.state = CONNECTION_STATE.TERMINATED;
                    }
                    this.host.notifyListeners(message);
                }
                else if (time > timeout) {
                    this.state = CONNECTION_STATE.TIMED_OUT;
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
        }
    }
}
