package shoppingcart.Task2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        //argument will take directory and port number 
        if (args.length != 2) {
            System.err.println("Key: <directory> <port>");
            System.exit(1); 
        }
        int port = Integer.parseInt(args[1]); 
        ServerSocket server = new ServerSocket(port); // start server 
        System.out.printf("Starting shopping cart server on port %s\n", port);
        System.out.printf("Using %s directory for persistence\n", args[0]);

        // to keep server connection alive even if 1 client exit 
        while (true) {
            Socket sock = server.accept(); 
            System.out.println("Connection received...");

            try { // try/catch so any client exception will not cause server to stop 
                // IO stream set up 
                while (true) {
                    OutputStream os = sock.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(os); 
                    InputStream is = sock.getInputStream();
                    ObjectInputStream ois = new ObjectInputStream(is);
                    DataInputStream dis = new DataInputStream(is);

                    // #1b read username from client 
                    String username = ois.readUTF(); 

                    // #2a load user's shopping cart to server after connection
                    String fileName = "src/" + args[0] + "/" + username + ".cart.txt"; // to read fred.cart.txt files
                    Path scpath = Paths.get(fileName); 
                    File file = scpath.toFile();
                    ShoppingCart user = new ShoppingCart();
                    user.setName(username); // read username from client 
                    
                    if (!file.exists()) { //initial shopping cart will be empty 
                        // write this to client  
                        user.setList(new ArrayList<>()); 
                        String output = "Initial shopping cart is empty.";
                        dos.writeUTF(output); 
                    } else { // read and load user's list into its ArrayList
                        FileReader fr = new FileReader(file); 
                        BufferedReader br = new BufferedReader(fr); 
                        List<String> temp = new ArrayList<>();
                        String content; 
                        
                        while ((content = br.readLine()) != null) { // read one line at a time 
                            temp.add(content);  // add the lines into ArrayList
                        }
                        user.setList(temp);
                        String output = username + " shopping cart loaded"; 
                        dos.writeUTF(output); 
                        // close reader
                        br.close();
                        fr.close();
                    }

                    // #3b read the instructions from client > execute > write back what is to be printed 
                    // loop to keep reading client's commands 
                    boolean stop = false;  
                    while (!stop) {
                        String result = dis.readUTF();
                        String[] results = result.trim().toLowerCase().split(" ");  
                        switch (results[0]) {
                            case "list":
                                dos.writeUTF(user.getList());
                                break; 
                            case "add":
                                dos.writeUTF(user.add(results));
                                break;
                            case "delete":
                                dos.writeUTF(user.delete(results[1]));
                                break;
                            case "save":
                                dos.writeUTF(user.save(fileName)); 
                                break;
                            case "exit":
                                stop = true; 
                                
                                break;
                            default: //do nothing // note that I did not add "exit" since there is nth to do on server part 
                        }
                    }
                    System.out.println("Connection closed...");
                    try {
                        sock.close(); 
                    } catch (IOException ex) { }
                }
            } catch (IOException ex) { }
        }
    }
}
