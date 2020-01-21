package Master_TaskManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.concurrent.Task;

/**
 * Hello world!
 *
 */
public class ServerApp
{
	static ServerCore serverCore;
	@SuppressWarnings("restriction")
	public static void main(String[] args)  {
		 	int port = 8080;
		 	serverCore = new ServerCore();
	        
	        Thread thread = new Thread(){
	            public void run(){
	            	 try (ServerSocket serverSocket = new ServerSocket(port)) {
		    	       	 
		    	            System.out.println("Server is listening on port " + port);
		    	            while (true) {
		    	                Socket socket = serverSocket.accept();
		    	                System.out.println("New connection");
		    	                new ServerThread(socket,serverCore).start();
		    	            }
		    	            
		    	 
		    	        } catch (IOException ex) {
		    	            System.out.println("Server exception: " + ex.getMessage());
		    	            ex.printStackTrace();
		    	        }
	            	 
	            }
	          };

	        thread.start();
	        Application.launch(ServerGui.class, args);
	    }
	
	public static ServerCore getServerCore() {
	return serverCore;
}
}
