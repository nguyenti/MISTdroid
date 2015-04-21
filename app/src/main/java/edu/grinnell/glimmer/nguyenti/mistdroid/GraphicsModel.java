package edu.grinnell.glimmer.nguyenti.mistdroid;

import edu.grinnell.glimmer.nguyenti.mistdroid.data.Pixel;
import edu.grinnell.glimmer.nguyenti.mistdroid.evaluating.DAGEvaluator;

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

    private DAGEvaluator dagEvaluator;

    /*------------------+
     |  Getter/Setter   |
     +------------------*/

    public DAGEvaluator getDagEvaluator() {
        return dagEvaluator;
    }

    /*------------------+
     |   DAG methods   |
     +------------------*/
    // initialize grid
    public void initializeDAG(DAGEvaluator dag) {
        dagEvaluator = dag;
    }

}
