/*
* This is the starting point to the program
* Takes the user inputs, validates them and
* initializes the Game of Life
*
* */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final int MIN_GRID_SIZE = 10;
    private static final int MAX_GRID_SIZE = 1000;

    private static BufferedReader bufferedReader;


    public static void main(String[] args) throws IOException {

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));


        int gridSize = takeInput(
                "grid size",
                MIN_GRID_SIZE,
                MAX_GRID_SIZE
        );

        // Number of seeds cannot exceed the number of cells
        int numSeeds = takeInput(
                "number of seeds",
                1,
                gridSize*gridSize
        );

        // Initializing the Game of Life
        GameOfLife game = new GameOfLife(gridSize, numSeeds);
        GridDrawer gridDrawer = new GridDrawer(game);
        gridDrawer.draw();
    }


    private static int takeInput(String varName, int minVal, int maxVal) throws IOException {
        int input;

        while (true) {
            System.out.printf("Enter the %s (Range [%d, %d]): ", varName, minVal, maxVal);
            input = Integer.parseInt(bufferedReader.readLine());

            // Validating the input
            if (input >= minVal && input <= maxVal)
                break;
            else
                System.out.println("Input is out of range");
        }

        return input;
    }
}
