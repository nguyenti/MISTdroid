package edu.grinnell.glimmer.nguyenti.mistdroid;

/**
 * Created by tiffanynguyen on 2/8/15.
 */
public class GraphicsModel {
    
     /*------------------+
     |   Constructors   |
     +------------------*/

    // Only one instance
    private static GraphicsModel instance = null;

    // outside this class, you cannot make a new object
    private GraphicsModel() {
    }

    // key part: return the specific instance if it is already made
    // makes new one if not (see code above)
    public static GraphicsModel getInstance() {
        if (instance == null) {
            instance = new GraphicsModel();
        }
        return instance;
    }

    /*------------------+
     |    Properties    |
     +------------------*/

    private Pixel grid[][]; // stores the data of a pixel
    private int gridSize; // the dimensions of the grid

    /*------------------+
     |  Getter/Setter   |
     +------------------*/

    public Pixel getFieldContent(int x, int y) {
        return grid[y][x];
    }

    /*------------------+
     |   Grid methods   |
     +------------------*/
    // initialize grid
    public void initializeGrid(int size) {
        if (grid == null) {
            grid = new Pixel[size][size];
            gridSize = size;
        }
        setGrid();
    }

    public void setGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = new Pixel();
            }
        }
    }

    public Pixel getPixel(int x, int y) {
        return grid[x][y];
    }
}
