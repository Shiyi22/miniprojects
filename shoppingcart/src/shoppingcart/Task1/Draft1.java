package shoppingcart.Task1;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Draft1 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        // args[0] to set directory 
        String directory = "src/shoppingcart/" + args[0]; 
        //File set = fred.cart.setDirectory(directory);

        // print to console how many carts are there in the directory 
        File dir = new File("./%s", args[0]); 
        File[] files = dir.listFiles(); 
        if (files == null) {
            System.out.printf("There are 0 cart in %s directory\n", args[0]);
        } else {
            int fileCount = files.length; 
            System.out.printf("There are %d carts in %s directory\n", fileCount, args[0]);
        }

        // get user input console: load, list, delete, add, save, exit 
        boolean stop = false; 
        String content;
        
        while (!stop) { // keep getting console input until asked to exit  
            Console cons = System.console(); 
            String line = cons.readLine("> "); 
            String[] input = line.trim().split(" "); 
            ShoppingCart user = new ShoppingCart();

            // load has to be inside the while !stop loop
            do {
                user.setName(input[1]);
                user.initiateList(); // new ArrayList
                Path scpath = Paths.get("%s/%s.cart", directory, input[1]); 
                File file = scpath.toFile();
                if (!file.exists()) { //initial shopping cart will be empty 
                    System.out.println("Initial shopping cart is empty.");
                } else { // read and load one's list into one's ArrayList
                    System.out.println("file exist, loading file into system");
                    FileReader fr = new FileReader(file); 
                    BufferedReader br = new BufferedReader(fr); 
                    List<String> temp = new ArrayList<>();
                    
                    while ((content = br.readLine()) != null) { // read one line at a time 
                        temp.add(content);  // add the lines into ArrayList
                    }
                    user.setList(temp);
                    // close reader
                    br.close();
                    fr.close();
                }
            } while (input[0] == "load");

            switch (input[0]) {
                case "list": 
                    user.getList(); // note: 1. 2. are not included in the list 
                    break; 
                case "add":
                    user.add(input);
                    break;  
                case "delete":
                    user.delete(input[1]); 
                    break; 
                case "save":
                    //user.save(); 
                    break; 
                case "exit": 
                    stop = true; 
                    break;
                default: System.out.println("Please use the available commands: list, add, delete, save or exit.");
            }
        }
    }
    
}
