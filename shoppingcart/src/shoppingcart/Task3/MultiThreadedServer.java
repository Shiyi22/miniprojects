package shoppingcart.Task3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
    
    public static String arg; 
    public static void main(String[] args) throws IOException {
        
        //argument will take directory and port number 
        if (args.length != 2) {
            System.err.println("Key: <directory> <port>");
            System.exit(1); 
        }
        int port = Integer.parseInt(args[1]); 
        arg = args[0]; 
        ServerSocket server = new ServerSocket(port); // start server 
        System.out.printf("Starting shopping cart server on port %s\n", port);
        System.out.printf("Using %s directory for persistence\n", arg);

        // to keep server connection alive even if 1 client exit 
        while (true) {
            
            Socket sock = server.accept(); 
            System.out.println("Connection received...");
            
            // to add: new thread to handle client request 
            ConnectionHandler handler = new ConnectionHandler(sock);
            Thread thread = new Thread(handler);
            // to add: threat.start(); 
            thread.start(); 

            // try/catch so any client exception will not cause server to stop 
			handler.run();
        }
    }
}
