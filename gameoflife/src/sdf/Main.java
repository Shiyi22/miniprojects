package sdf;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException , IOException {
        
        // pass argument into read file method 
        String fileName = args[0] + ".gol";
        GolFileReader gfr = new GolFileReader();
        
        // read file to generate 2d array in FileReader object   
        int[][] nestedArray = gfr.readTextFile(fileName);       
        
        // print initial grid structure 
        gfr.printFirst(nestedArray);

        // iterate 5 rounds, using set conditions and printing them 
        gfr.generateBoard(nestedArray);
    }
}