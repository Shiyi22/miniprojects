package shoppingcart.Task3;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MultiThreadedClient {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        // argument will take single parameter
        if (args.length != 1) {
            System.err.println("Key: <user>@<host>:<port>");
        }
        String[] input = args[0].split("[@:]"); 
        String user = input[0]; 
        String host = input[1]; 
        int port = Integer.parseInt(input[2]); 
        
        // set up client connection to server port 
        Socket sock = new Socket(host, port); 
        System.out.printf("Connected to shopping cart server at %s on %s port %d\n", host, user, port);

        // IO stream set up 
        OutputStream os = sock.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os); 
        DataOutputStream dos = new DataOutputStream(os);
        InputStream is = sock.getInputStream();
        //ObjectInputStream ois = new ObjectInputStream(is);
        DataInputStream dis = new DataInputStream(is);
        
        // #1a write username to server 
        oos.writeUTF(user); 
        oos.flush(); 

        // #2b read whether cart is loaded 
        String cartStatus = dis.readUTF(); 
        System.out.println(cartStatus);
        
        // #3a after loading, get console instructions & send commands to server 
        Console cons = System.console(); 
        boolean stop = false; 
        
        while (!stop) {
            String wholeinput2 = cons.readLine("> ");
            String[] input2 = wholeinput2.trim().toLowerCase().split(" ");
            switch (input2[0]) {
                case "list":
                    dos.writeUTF(wholeinput2); // write to server  
                    
                    String[] results = (dis.readUTF()).trim().split(" ");
                    if (results[0].equals("empty")) {
                        System.out.println("Your cart is empty");
                    } else {
                        for (int i = 0; i < results.length; i++) {
                            System.out.println(i+1 + ". " + results[i]);
                        }
                    }
                    break;
                case "add":
                case "delete": 
                case "save":
                    dos.writeUTF(wholeinput2); // send the instructions 
                    System.out.println(dis.readUTF()); // read the results and print in terminal 
                    break;
                case "exit":
                    dos.writeUTF(wholeinput2); // send exit msg so that server can print out conn closed 
                    stop = true; 
                    break; 
                default: 
                    System.out.println("Please choose commands from: list/add/delete/save/exit");
                    break; 
            }
        }
        sock.close(); 
        oos.flush(); 
        oos.close();
    }
}
