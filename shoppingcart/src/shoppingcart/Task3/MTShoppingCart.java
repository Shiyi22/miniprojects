package shoppingcart.Task3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MTShoppingCart {

    // members
    private String name;  
    private List<String> list;  

    // constructors
    public MTShoppingCart() {
        this.name = "name not yet given";   
        this.list = new ArrayList<>(); 
    }

    // getters and setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getList() {
        String listString = "";
        if (this.list.size() == 0) {
            return "empty";
        } else {
            for (int i = 0; i < list.size(); i++) {
                listString = listString + list.get(i) + " ";
            }
            return listString;
        }
    }
    public void setList(List<String> list) {this.list = list;}

    // methods to delete, add, save, exit to list 
    
    public String add(String[] input) {
        String addition = "";
        if (input.length == 2) { // only add one item 
            //System.out.printf("list size: %d\n", list.size());
            list.add(input[1]); 
            return (input[1] + " added to the cart"); 
            //System.out.printf("list size: %d\n", list.size());
        } else if (input.length > 2) { // add all the item 
            for (int i = 1; i < input.length; i++) {
                list.add(input[i]);
                addition = addition + input[i] + ", ";
            }
            return (addition + "added to the cart"); 
        } else {
            return "incorrect usage of add command"; 
        }
    }
    public String delete(String stringNum) {
        int num = Integer.parseInt(stringNum); 
        if (num >= list.size()) {
            // to add incorrect item index
            return "Incorrect item index";
        } else {
            String item = list.get(num - 1); 
            list.remove(num - 1); 
            return item + " removed from the cart";
        }
    }
    public String save(String fileName) throws IOException {
        // write the list to txt file
        FileWriter writer = new FileWriter(fileName);  

        for (int i = 0; i < list.size(); i++) {
            writer.write(list.get(i));; 
            writer.write("\n"); 
        }
        writer.flush(); // close the writer
        writer.close(); 
        return "cart contents saved to " + name;
    } 

    
}
