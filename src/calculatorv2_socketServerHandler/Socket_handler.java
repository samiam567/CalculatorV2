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
	public Socket_handler(String mode, int port, Equation eq,boolean userCalculator) {
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
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		running = true;
		if (connectionType == ConnectionType.csharp) {
			Socket socket = null;
			
			while (running) {
				try {
					
			        socket = serverSocket.accept();
			        InputStream is = socket.getInputStream();
			        OutputStream os = socket.getOutputStream();
			        
			        while (running && socket.isConnected()) {
				        // Receiving
				        byte[] lenBytes = new byte[4];
				        is.read(lenBytes, 0, 4);
				        int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
				                  ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
				        byte[] receivedBytes = new byte[len];
				        is.read(receivedBytes, 0, len);
				        String received = new String(receivedBytes, 0, len);
			
				        // Sending
				        String toSend = handleMessage(received);
				        System.out.println("sending...");
				        if (toSend.length() == 0) toSend = "~~~~";
				        byte[] toSendBytes = toSend.getBytes();
				        int toSendLen = toSendBytes.length;
				        byte[] toSendLenBytes = new byte[4];
				        toSendLenBytes[0] = (byte)(toSendLen & 0xff);
				        toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
				        toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
				        toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
				        os.write(toSendLenBytes);
				        os.write(toSendBytes);
				        System.out.println("sent.");
			        }
		
			        
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("terminating");
			
			try {
				serverSocket.close();
				socket.close();
			}catch(Exception e) {}

			System.exit(1);
		}else if (connectionType == ConnectionType.python) {
			DataOutputStream dout = null;
			DataInputStream in = null;
			Socket soc = null;
			while(running) {
				try{	
					soc = serverSocket.accept();
					
					if (Calculator.verboseOutput) System.out.println("Receive new connection: " + soc.getInetAddress());
					
					
					dout=new DataOutputStream(soc.getOutputStream());  
					in = new DataInputStream(soc.getInputStream());
					
					while (running && soc.isConnected()) {
						String msg=(String)in.readUTF();	
						dout.writeUTF(handleMessage(msg));
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
			
			if (Calculator.verboseOutput) System.out.println("Exiting...");
			
			try {
				dout.flush();
				dout.close();
				soc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}else {
			Exception e = new Exception("invalid ConnectionTypes connectionType");
			e.printStackTrace();
			
		}
	}
	
	
	private String handleInput(String input) {
		if (userCalculator) {
			return eq.queryUserCalculator(input);
		}else {
			return eq.calculate(input);
		}
		
	}

	private String handleMessage(String msg) {
		if (Calculator.verboseOutput) System.out.println("Input: " + msg);
		
		String output = "";
		
		if (msg.startsWith("q") && (msg.equals("q") || msg.equals("quit"))) {
			running = false;
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