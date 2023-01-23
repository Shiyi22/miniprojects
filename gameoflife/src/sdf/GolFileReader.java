package sdf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GolFileReader {

    // attributes
    private int gridRows;
    private int gridCols;
    private int startRowPos;
    private int startColPos; 
    private int[][] nestedArray; 
    private int count = 1; 

    // constructors 
    // getters / setters 

    // function to read file 
    public int[][] readTextFile(String fileName) throws NumberFormatException, IOException {
        // return grid configuration 
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);

        // read all the instructions from .gol file 
        String line = ""; 
        String[] input, elements;
        
        while ((line = br.readLine()) != null) {
            input = line.trim().split(" "); 
            switch (input[0]) {
                case "GRID":
                    gridRows = Integer.parseInt(input[1]);
                    gridCols = Integer.parseInt(input[2]); 
                    nestedArray = new int[gridRows][gridCols]; 
                    break;
                case "START":
                    startRowPos = Integer.parseInt(input[1]); 
                    startColPos = Integer.parseInt(input[2]); 
                    break;
                case "DATA":
                    int CurrentRow = startRowPos - 1;  

                    while(null != (line = br.readLine())) { // read the next few lines 
                        int CurrentCol = startColPos; // current column reset after every row 
                        CurrentRow ++;                         
                        elements = line.split(""); // split all individual elements 
                        for (int i = 0; i < elements.length; i++) {
                            if (elements[i].equals(" ")) {
                                CurrentCol++; 
                            } else if (elements[i].equals("*")) {
                                this.nestedArray[CurrentRow][CurrentCol] = 1; 
                                CurrentCol++; 
                            } else {
                                System.err.println("Error in data, please choose another .gol file");
                            }
                        }
                    } 
                    break; 
                case "#":
                    break; 
                default:
                    break; 
            }
        }
        br.close(); 
        fr.close(); 
        return nestedArray; 
    }

    // function to print first gen 
    public void printFirst(int[][] nestedArray) {
        System.out.println("Generation 1: ");
        
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                if (nestedArray[i][j] == 0) {
                    System.out.printf(".");
                } else {
                System.out.printf("*");
                }
            }
            System.out.println();
        }
    } 

    // function to generate new grid, return 2d array 
    public void generateBoard (int[][] currentArray) {
        // iterate through all elements in grid 
        // find number of alive neighbours 
        int[][] nextArray = new int[gridRows][gridCols]; 
        count++; // for 5 rounds iteration 
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                int alive = 0; 
                // iterate to surround neighbours 
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        // boundaries set up for grid, so that we dont take -1 and below & max rows/cols and above
                        if ((i + k >= 0 && i + k <gridRows) && (j + l >= 0 && j + l < gridCols)) {
                            // for non-alive they will be 0 so they dont add to the count 
                            alive += currentArray[i+k][j+l]; 
                        }
                    }
                }
                // to exclude current pointer cell in the alive neighbours, if it has value of 1; 
                alive -= currentArray[i][j]; 

                //active cells >> what is its next state 
                // one or no neighbour and four or more neighbour = dies 
                if ((alive <= 1 && currentArray[i][j] == 1) || (alive >= 4 && currentArray[i][j] == 1)) {
                    nextArray[i][j] = 0; 
                } else if ((alive == 2 || alive == 3) && currentArray[i][j] == 1) {
                    // 2 or 3 neighbours == survives 
                    nextArray[i][j] = 1; 
                } else if (currentArray[i][j] == 0 && alive == 3) {
                // 3 neighbours == become populated 
                    nextArray[i][j] = 1; 
                } else { // remains the same
                    nextArray[i][j] = currentArray[i][j];
                }
            }
        }
        
        // print out next Array 
        System.out.printf("Generation %d: \n", count);
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                if (nextArray[i][j] == 0) {
                    System.out.printf(".");
                } else {
                System.out.printf("*");
                }
            }
            System.out.println();
        }

        // iterate 5 generations using recursive 
        while (count <= 5) {
            generateBoard(nextArray);
        }
    }      
}
