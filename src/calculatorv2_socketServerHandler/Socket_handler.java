package calculatorv2_socketServerHandler;

import java.io.*;  
import java.net.*;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation; 

public class Socket_handler extends Thread {
	
	ServerSocket serverSocket;
	
	static final String[] connectionTypeStrings = {"csharp","python"};
	static enum ConnectionType {csharp,python,none};
	ConnectionType connectionType = ConnectionType.none;
	
	boolean userCalculator = false;
	boolean running = true;
	Equation eq;
	public Socket_handler(String mode, int port, Equation eq, boolean userCalculator) {
		this.userCalculator = userCalculator;
		if (mode.equals(connectionTypeStrings[0])) {
			connectionType = ConnectionType.csharp;
		}else if (mode.equals(connectionTypeStrings[1])) {
			connectionType = ConnectionType.python;
		}else {
			Exception e = new Exception("Incorrect mode for Socket_handler. Possible modes:");
			for (String s : connectionTypeStrings) System.out.println("   " + s);
			e.printStackTrace();
			connectionType = ConnectionType.none;
			return;
		}
		
		this.eq = eq;
		if (Calculator.verboseOutput) System.out.println("Starting server at port: " + port);
		try {
			 // server is listening on port
			serverSocket = new ServerSocket(port); 
			serverSocket.setReuseAddress(true); 
		} catch (IOException e) {
			e.printStackTrace();
			try {
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		running = true;
		
		  
        try { 
  
        	
  
            // running infinite loop for getting 
            // client request 
            while (running) { 
  
                // socket object to receive incoming client 
                // requests 
                Socket client = serverSocket.accept(); 

                // Displaying that new client is connected 
                // to server 
                System.out.println("New client connected "
                                   + client.getInetAddress() 
                                         .getHostAddress() + ":" + client.getPort()); 
  
                // create a new thread object 
                ClientHandler clientSock 
                    = new ClientHandler(this, client);
  
                // This thread will handle the client 
                // separately 
                new Thread(clientSock).start(); 
            } 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
        finally { 
            if (serverSocket != null) { 
                try { 
                	serverSocket.close(); 
                } 
                catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
	}
	
	public void interrupt() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	  public void finalize() {
	    try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	
	
}