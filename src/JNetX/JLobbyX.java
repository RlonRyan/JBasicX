/*
 * Blah
 */
package JNetX;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.util.List;

/*
 * @author RlonRyan
 * @name JLobbyX
 * @date Jan 23, 2014
 *
 */

/**
 *
 * @author Ryan
 */

public class JLobbyX extends Thread {

    /**
     *
     */
    public int port;

    /**
     *
     */
    public static List<Inet4Address> clients;

    /**
     *
     */
    @Override
    public void run() {

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

    /**
     *
     * @param port
     */
    public JLobbyX(int port) {
	this.port = port;
    }
}
