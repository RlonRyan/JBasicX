/*
 * Blah
 */

package JNetX;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  RlonRyan
 * @name    JLobbyX
 * @date    Jan 23, 2014
 **/

public class JLobbyX extends Thread{
    
    public static short port;
    
    public static List<Inet4Address> clients;
    
    
    public static void main(String [] args) {
        
        port = Short.parseShort(args[0]);
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on port: " + serverSocket.getLocalPort() + ".");
            while (true) {
                serverSocket.accept();
                
            }
        }
        catch (IOException e) {
            System.err.println("Could not listen on port " + port + ".");
        }
        
    }
    
}
