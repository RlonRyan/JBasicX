/*
 * Blah
 */
package JNetX;

import JBasicX.JBinderX;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

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
    public final int port;

    /**
     *
     */
    public final List<InetAddress> clients;
    public final JBinderX<String, Pair<JLobbyX, Pair<Socket, PrintWriter>>> bindings;

    /**
     *
     */
    @Override
    public void run() {
	try (ServerSocket serverSocket = new ServerSocket(port)) {
	    Logger.getLogger("Network").log(Level.INFO, "Listening on port: {0}.", serverSocket.getLocalPort());
	    while (true) {
		try {
		    Socket client = serverSocket.accept();
		    
		    Logger.getLogger("Network").log(Level.INFO, "Join: {0}.", client.getInetAddress().toString());
		    client.setSoTimeout(1000);
		    client.setKeepAlive(false);
		    new JLobbyConnectionX(this, client).run();
		}
		catch (IOException e) {
		    Logger.getLogger("Network").log(Level.WARNING, "Failed to establish connection with a client.");
		}
	    }
	}
	catch (IOException e) {
	    Logger.getLogger("Network").log(Level.SEVERE, "Could not listen on port: {0}.", port);
	}
	
    }

    /**
     *
     * @param port
     */
    public JLobbyX(int port) {
	this.port = port;
	this.clients = new ArrayList<>();
	this.bindings = new JBinderX();
    }
    
    public static void main(String[] args) {
	
	JLobbyX instance = new JLobbyX(7654);
	
	instance.bindings.bind("add", (data) -> {
	    boolean result = data.getKey().clients.add(data.getValue().getKey().getInetAddress());
	    data.getValue().getValue().println(result);
	});
	instance.bindings.bind("get", (data) -> {
	    data.getValue().getValue().println(data.getKey().clients);
	});
	instance.bindings.bind("rem", (data) -> {
	    boolean result = data.getKey().clients.remove(data.getValue().getKey().getInetAddress());
	    data.getValue().getValue().println(result);
	});
	instance.bindings.bind("help", (data) -> {
	    data.getValue().getValue().println(Arrays.toString(instance.bindings.getTriggers()));
	});
	instance.bindings.bind("?", (data) -> {
	    instance.bindings.trigger("help", data);
	});
	instance.bindings.bind("join", (data) -> {
	    //TODO: Something...
	});
	instance.bindings.bind("part", (data) -> {
	    //TODO: Something...
	});
	instance.bindings.bindDefault((data) -> {
	    if (data.getValue() != null && data.getValue().getValue() != null) {
		data.getValue().getValue().println("?");
	    }
	});
	
	instance.start();
    }
}
