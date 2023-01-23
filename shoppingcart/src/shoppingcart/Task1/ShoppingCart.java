package shoppingcart.Task1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    // members
    private String name;  
    private List<String> list;  

    // constructors
    public ShoppingCart() {
        this.name = "name not yet given";   
    }

    // getters and setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public void getList() {
        if (list.size() == 0) {
            System.out.println("Your cart is empty");
        } else {
            for (int i = 0; i < list.size(); i++) {
                // to print numbered list 
                System.out.printf(i + 1 + ". " + list.get(i) + "\n");
            }
        }
    }
    public void setList(List<String> list) {this.list = list;}

    // methods to delete, add, save, exit to list 
    public void initiateList() {this.list = new ArrayList<>();}
    
    public void add(String[] input) {
        if (input.length == 2) { // only add one item 
            //System.out.printf("trying to add %s into list\n", input[1]);
            //System.out.printf("list size: %d\n", list.size());
            list.add(input[1]); 
            //System.out.printf("list size: %d\n", list.size());
        } else if (input.length > 2) { // add all the item 
            for (int i = 1; i < input.length; i++) {
                list.add(input[i]);
            }
        }
    }
    public void delete(String stringNum) {
        int num = Integer.parseInt(stringNum); 
        if (num >= list.size()) {
            // to add incorrect item index
            System.out.println("Incorrect item index");
        } else {
            String item = list.get(num - 1); 
            list.remove(num - 1); 
            System.out.printf("%s removed from the cart\n", item);
        }
    }
    public void save(String fileName) throws IOException {
        // write the list to txt file
        FileWriter writer = new FileWriter(fileName);  

        for (int i = 0; i < list.size(); i++) {
            writer.write(list.get(i));; 
            writer.write("\n"); 
        }
        System.out.printf("cart contents saved to %s\n", name);
        // close the writer
        writer.flush();
        writer.close(); 
    } 

    
}
