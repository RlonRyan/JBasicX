/*
 * JBasicX
 * By RlonRyan
 * 
 * All rights reserved. No warrenty on use. User accepts all risks and damages.
 * Class: JLobbyConnectionX
 * Description: JLobbyConnectionX
 */
package JNetX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 *
 * @author Ryan
 */
public class JLobbyConnectionX extends Thread {

    public final JLobbyX lobby;
    public final Socket client;

    public final BufferedReader in;
    public final PrintWriter out;

    public void send(String message) {
	out.println(message);
    }

    @Override
    public void run() {
	try {
	    
	    this.lobby.bindings.trigger("join", new Pair<>(lobby, new Pair<>(client, null)));
	    
	    while (!client.isClosed()) {
		String request = in.readLine();
		
		System.out.println(request);
		
		if (request == null) {
		    Logger.getLogger("Network").log(Level.WARNING, "Null request by: {0}", client.getInetAddress().toString());
		    break;
		}
		else if (request.equalsIgnoreCase("heartbeat")) {
		    Logger.getLogger("Network").log(Level.FINEST, "Recieve: {0}: {1}", new Object[]{client.getInetAddress().toString(), request});
		}
		else {
		    Logger.getLogger("Network").log(Level.FINER, "Recieve: {0}: {1}", new Object[]{client.getInetAddress().toString(), request});
		    this.lobby.bindings.trigger(request, new Pair<>(lobby, new Pair<>(client, out)));
		}
	    }
	    
	    this.lobby.bindings.trigger("part", new Pair<>(lobby, new Pair<>(client, null)));
	    System.out.println("Part: " + client.getInetAddress().toString() + ".");
	}
	catch (IOException e) {
	    this.lobby.bindings.trigger("IO_EXCEPTION", new Pair<>(lobby, new Pair<>(client, null)));
	    Logger.getLogger("Network").log(Level.WARNING, "Part: Exception {0}: {1}.", new Object[]{e.getLocalizedMessage(), client.getInetAddress().toString()});

	    try {
		this.out.close();
		this.in.close();
		this.client.close();
	    }
	    catch (IOException ioe) {

	    }
	}
    }

    public JLobbyConnectionX(JLobbyX lobby, Socket client) throws IOException {
	this.lobby = lobby;
	this.client = client;
	this.out = new PrintWriter(client.getOutputStream(), true);
	this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

}
