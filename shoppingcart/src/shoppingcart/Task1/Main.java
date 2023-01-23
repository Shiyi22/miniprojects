package shoppingcart.Task1;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        
        // args[0] to set directory 
        String directory = "src/" + args[0];
        // print to console how many carts are there in the directory 
        File dir = new File("src/%s", args[0]); 
        File[] files = dir.listFiles(); 
        if (files == null) {
            System.out.printf("There are 0 cart in %s directory\n", args[0]);
        } else {
            int fileCount = files.length; 
            System.out.printf("There are %d carts in %s directory\n", fileCount, args[0]);
        }

        boolean stop = false; 
        while (!stop) {
            Console cons = System.console(); 
            String[] input = cons.readLine("> ").trim().toLowerCase().split(" "); 

            // load user first 
            if (input[0].equals("load")) {
                ShoppingCart user = new ShoppingCart();
                user.setName(input[1]); 
                user.initiateList(); 
                String fileName = directory + "/" + input[1] + ".cart.txt"; 
                System.out.printf("file location: %s\n", fileName);
                Path scpath = Paths.get(fileName); 
                File file = scpath.toFile();
                
                if (!file.exists()) { //initial shopping cart will be empty 
                    System.out.println("Initial shopping cart is empty.");
                } else { // read and load one's list into one's ArrayList
                    FileReader fr = new FileReader(file); 
                    BufferedReader br = new BufferedReader(fr); 
                    List<String> temp = new ArrayList<>();
                    String content; 
                    
                    while ((content = br.readLine()) != null) { // read one line at a time 
                        temp.add(content);  // add the lines into ArrayList
                    }
                    user.setList(temp);
                    // close reader
                    br.close();
                    fr.close();
                    System.out.println("file exist, file loaded into system");
                }
                while (!stop) {
                    // get user input again 
                    String[] input2 = cons.readLine("> ").trim().toLowerCase().split(" "); 

                    // commands after loading 
                    switch (input2[0]) {
                        case "list":
                            user.getList();
                        case "add": 
                            user.add(input2); 
                            break;
                        case "delete": 
                            user.delete(input2[1]);
                            break;  
                        case "save":
                            user.save(fileName); 
                            break;
                        case "exit": 
                            stop = true; 
                            break; 
                        default: 
                            System.out.println("Please choose commands from: add/delete/save/exit");
                            break; 
                    }
                }
            } else {
                System.out.println("Please load a user using: load (name)");
            }
        }
    }
}
