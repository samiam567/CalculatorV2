package calculatorv2_socketServerHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation;

public class ClientHandler implements Runnable {
	private final Socket clientSocket;
	
	private Socket_handler parentSocketHandler;
	
	boolean running = true;
    // Constructor 
    public ClientHandler(Socket_handler parentSocketHandler, Socket socket) 
    { 
    	this.parentSocketHandler = parentSocketHandler;
        this.clientSocket = socket; 
    } 

    public void run() { 
    	
    	System.out.println("clienthandler started");
        PrintWriter out = null; 
        BufferedReader in = null; 
        
        try {
	        
        	// get the outputstream of client 
            out = new PrintWriter(clientSocket.getOutputStream(), true); 

            // get the inputstream of client 
            in = new BufferedReader( 
                new InputStreamReader( 
                    clientSocket.getInputStream())); 

            String line; 
            while (parentSocketHandler.running && running && (line = in.readLine()) != null) { 
            	
            	System.out.println("received " + line);

                // writing the received message from 
                // client 
            	
            	String toSend = handleMessage(line);
                
            
            	
            	// Sending
		        out.println(toSend + "~~~~");
		        
		        
		        
            } 
        }catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
	        try { 
	            if (out != null) { 
	                out.close(); 
	            } 
	            if (in != null) { 
	                in.close(); 
	                clientSocket.close(); 
	            } 
	        } 
	        catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
        } 
    } 
	private String handleInput(String input) {
		if (parentSocketHandler.userCalculator) {
			return parentSocketHandler.eq.queryUserCalculator(input);
		}else {
			return parentSocketHandler.eq.calculate(input);
		}
	}

	private String handleMessage(String msg) {
		if (Calculator.verboseOutput) System.out.println("Input: " + msg);
		
		String output = "";
		
		if (msg.startsWith("q") && (msg.equals("q") || msg.equals("quit"))) {
			parentSocketHandler.running = false;
			running = false;
			parentSocketHandler.interrupt();
			
			output = "quiting";
		}else {
			try {
				output = handleInput(msg);
			}catch(Exception e) {
				output = e.toString();
			}
		}
		
		if (Calculator.verboseOutput) System.out.println("Output: " + output);
		
		return output;
	}
} 
