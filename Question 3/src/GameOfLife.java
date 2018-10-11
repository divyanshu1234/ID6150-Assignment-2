import java.util.Random;

/*
* Objects of this class can bbe used to simulate individual
* Game of Life instances, each having its own size and
* initial number of seeds
*
* Grid is processed as a boolean array, value of any cell
* being 'true' indicates the presence of an organism in the
* cell, and 'false' being the opposite
*
* */

public class GameOfLife {

    // Setting up the parameters of the Game of Life
    private final int gridSize;
    private final int numSeeds;
    private final float cellLength;

    private boolean[][] grid;


    public GameOfLife(int gridSize, int numSeeds) {
        this.gridSize = gridSize;
        this.numSeeds = numSeeds;
        this.cellLength = 1.0f / gridSize;

        // Initializing the grid
        grid = new boolean[gridSize][gridSize];
        generateSeeds();
    }


    // Puts seeds on random locations in the grid
    private void generateSeeds() {
        Random random = new Random();

        for (int i = 0; i < numSeeds; i++) {
            int xi = random.nextInt(gridSize);
            int yi = random.nextInt(gridSize);
            grid[yi][xi] = true;
        }
    }


    // Returns the next iteration of the grid
    public boolean[][] iterate() {

        boolean newGrid[][] = new boolean[gridSize][gridSize];

        // Copying old grid to new one
        for (int i = 0; i < gridSize; ++i) {
            System.arraycopy(grid[i], 0, newGrid[i], 0, gridSize);
        }

        // Applying the rules to each element of the grid
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
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

                // Periodic boundary conditions
                int ii = (i + k + gridSize) % gridSize;
                int jj = (j + l + gridSize) % gridSize;

                // Leaving the cell itself
                if (k == 0 && l == 0)
                    continue;

                if (grid[ii][jj])
                    ++neighbourCount;
            }
        }

        return neighbourCount;
    }


    public int getGridSize() {
        return gridSize;
    }


    public float getCellLength() {
        return cellLength;
    }
}
