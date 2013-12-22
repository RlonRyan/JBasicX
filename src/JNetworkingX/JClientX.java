/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JNetworkingX;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ryan
 */
public class JClientX extends Thread {

    private String ip;
    private int port;
    public List<String> stack;
    private List<JNetworkListenerX> listeners;
    public int state = 1;

    public JClientX(String ip, int port) {
        super("JClientConnectionX");
        this.ip = ip;
        this.port = port;
        this.stack = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.state = 1;
    }

    public void addListener(JNetworkListenerX listener) {
        this.listeners.add(listener);
    }

    public void removeListener(JNetworkListenerX listener) {
        this.listeners.remove(listener);
    }

    public void fireEvent(String message) {
        for(JNetworkListenerX e : listeners) {
            e.onMessage(message);
        }
    }

    @Override
    public void run() {
        try(Socket socket = new Socket(ip, port); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {
            String message;
            while(this.state == 1){
                try{
                    sleep(1000);
                }
                catch(InterruptedException e) {
                    /*
                     * ???
                     */
                }
                while(in.ready() && (message = in.readLine()) != null) {
                    System.out.println("Recieved a message!");
                    this.fireEvent(message);
                }
                while(!stack.isEmpty()) {
                    System.out.println("Sending Messages!");
                    out.println(stack.remove(0));
                }
            }
            out.println("terminate");
        }
        catch (Exception e) {
            System.err.println("Something went wrong connecting to the host.");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
