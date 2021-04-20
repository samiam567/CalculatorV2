package calculatorv2_socketServerHandler;

import java.io.*;  
import java.net.*;

import calculatorv2_core.Calculator;
import calculatorv2_core.Equation; 

public class Socket_handler extends Thread {
	ServerSocket serverSocket;
	
	Equation eq;
	public Socket_handler(int port, Equation eq) {
		this.eq = eq;
		if (Calculator.verboseOutput) System.out.println("Starting server at port: 12334");
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		DataOutputStream dout = null;
		DataInputStream in = null;
		Socket soc = null;
		while(true) {
			try{	
				soc = serverSocket.accept();
				
				if (Calculator.verboseOutput) System.out.println("Receive new connection: " + soc.getInetAddress());
				
				
				dout=new DataOutputStream(soc.getOutputStream());  
				in = new DataInputStream(soc.getInputStream());
				
				while (true) {
					String msg=(String)in.readUTF();
					
					if (Calculator.verboseOutput) System.out.println("Input: " + msg);
					
					String output = handleInput(msg);
					if (Calculator.verboseOutput) System.out.println("Output: " + output);
					
					dout.writeUTF(output);
				}
				
			
			}catch(EOFException f) { // Connection closed by client
				try {
					if (Calculator.verboseOutput) System.out.println("connection closed. Readying for another connection...");
					dout.flush();
					dout.close();
					soc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}catch(Exception e) {
				e.printStackTrace(); 
			}
		}
		
	}
	
	private String handleInput(String input) {
		return eq.calculate(input);
		
	}
	public void interrupt() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}