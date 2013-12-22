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
public class JClientX extends Thread {

    private String ip;
    private int port;
    private List<JPackectX> stack;
    private List<JNetworkListenerX> listeners;
    private JConnectionStateX state;

    public JClientX(String ip, int port) {
        super("JClientConnectionX");
        this.ip = ip;
        this.port = port;
        this.stack = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.state = JConnectionStateX.ACTIVE;
    }

    public void addListener(JNetworkListenerX listener) {
        this.listeners.add(listener);
    }

    public void removeListener(JNetworkListenerX listener) {
        this.listeners.remove(listener);
    }

    public void notifyListners(JPackectX packet) {
        for(JNetworkListenerX e : listeners) {
            e.onPacket(packet);
        }
    }

    public void queuePacket(JPackectX packet) {
        this.stack.add(packet);
    }

    @Override
    public void run() {
        try(Socket socket = new Socket(ip, port); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {
            String message;
            while(this.state == JConnectionStateX.ACTIVE){
                try{
                    sleep(1000);
                }
                catch(InterruptedException e) {
                    /*
                     * ???
                     */
                }
                while(in.ready() && (message = in.readLine()) != null) {
                    this.notifyListners(new JPackectX(message));
                }
                while(!stack.isEmpty()) {
                    out.println(stack.remove(0).encode());
                }
            }
            out.println(new JPackectX(JPackectX.PACKET_TYPE.TERMINATE, "").encode());
        }
        catch (IOException e) {
            System.err.println("Something went wrong connecting to the host.");
            System.exit(1);
        }
    }

    public void close() {
        this.state = JConnectionStateX.TERMINATED;
    }

}
