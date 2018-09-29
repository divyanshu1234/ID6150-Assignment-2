import java.util.Random;

public class GameOfLife {

    // Setting up the parameters of the Game of Life
    private static final int GRID_SIZE = 250;
    private static final int NUM_SEEDS = 8000;
    private static final float CELL_LENGTH = 1.0f / GRID_SIZE;

    private boolean[][] grid;


    public GameOfLife() {
        grid = new boolean[GRID_SIZE][GRID_SIZE];
        generateSeeds();
    }


    // Puts seeds on random locations in the grid
    private void generateSeeds() {
        Random random = new Random();

        for (int i = 0; i < NUM_SEEDS; i++) {
            int xi = random.nextInt(GRID_SIZE);
            int yi = random.nextInt(GRID_SIZE);
            grid[yi][xi] = true;
        }
    }


    // Returns the next iteration of the grid
    public boolean[][] iterate() {

        boolean newGrid[][] = new boolean[GRID_SIZE][GRID_SIZE];

        // Copying old grid to new one
        for (int i = 0; i < GRID_SIZE; ++i) {
            System.arraycopy(grid[i], 0, newGrid[i], 0, GRID_SIZE);
        }

        // Applying the rules to each element of the grid
        for (int i = 0; i < GRID_SIZE; ++i) {
            for (int j = 0; j < GRID_SIZE; ++j) {
                int neighbourCount = getNeighbourCount(i, j);

                // Organism is born
                if (neighbourCount == 3) {
                    newGrid[i][j] = true;
                    continue;
                }

                // Cell does not change its state
                if (neighbourCount == 2)
                    continue;

                // For the remaining conditions
                // (neighbourCount > 3 || neighbourCount < 2)
                // The organism dies
                newGrid[i][j] = false;
            }
        }

        grid = newGrid; // Updating the grid

        return newGrid;
    }


    // Returns the number of neighbours for a cell location
    private int getNeighbourCount(int i, int j) {
        int neighbourCount = 0;

        for (int k = -1; k <= 1; ++k) {
            for (int l = -1; l <= 1; l++) {
                int ii = i + k;
                int jj = j + l;

                // Boundary conditions
                if (ii == -1 || jj == -1 || ii == GRID_SIZE || jj == GRID_SIZE)
                    continue;

                // Leaving the cell itself
                if (k == 0 && l == 0)
                    continue;

                if (grid[ii][jj])
                    ++neighbourCount;
            }
        }

        return neighbourCount;
    }


    public static int getGridSize() {
        return GRID_SIZE;
    }


    public static float getCellLength() {
        return CELL_LENGTH;
    }
}
