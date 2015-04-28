package edu.grinnell.glimmer.nguyenti.mistdroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import edu.grinnell.glimmer.nguyenti.mistdroid.dagmaking.DAG;
import edu.grinnell.glimmer.nguyenti.mistdroid.data.Pixel;
import edu.grinnell.glimmer.nguyenti.mistdroid.evaluating.DAGEvaluator;
import edu.grinnell.glimmer.nguyenti.mistdroid.parsing.Parser;
import edu.grinnell.glimmer.nguyenti.mistdroid.parsing.TreeNode;

/**
 * Created by albertowusu-asare on 4/28/15.
 */
public class AltGraphicsView extends SurfaceView implements SurfaceHolder.Callback{

    PanelThread panelThread;
    private Paint paintBg;
    private boolean error = false;

    int height = 100;
    int width = 100;
    Bitmap bitmap = Bitmap.createBitmap(new DisplayMetrics(), width, height,
            Bitmap.Config.ARGB_8888);


    public AltGraphicsView(Context context) {
        super(context);
        paintBg = new Paint();
        panelThread = new PanelThread(this);
    }


    public AltGraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintBg = new Paint();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        panelThread.setRunning(true);
        panelThread.run();

    }

    @Override
    public void onDraw(Canvas canvas){
        performDraw(canvas);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        bitmap = Bitmap.createBitmap(new DisplayMetrics(), width, height,
                Bitmap.Config.ARGB_8888);
        panelThread.setRunning(true);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        // TODO Auto-generated method stub
        boolean retry = true;
        panelThread.setRunning(false);
        while (retry) {
            try {
                panelThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }


    /**
     * Set the bitmap of the graphic. This may need to be changed with time functions
     * @throws Exception
     */
    private void updateBitmap() throws Exception {
        int size = height * width;
        int colors[] = new int[size];
        Pixel pix[] = new Pixel[size];
        GraphicsModel instance = GraphicsModel.getInstance();

        // go through "image" and set pixel values
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double x = j / (width * 1.0) * 2.0 - 1.0;
                double y = i / (height * 1.0) * 2.0 - 1.0;

                pix[j + i * height] = instance.getDagEvaluator().evaluate(x, y);

                // if the code does not contain the RGB function, flip the gradient
                if (!instance.getDagEvaluator().getDag().getRGBFunction()) {
                    pix[j + i * height].flipGradient();
                }

                colors[j + i * height] = pix[j + i * height].RGBToInt();
            }
        }
        bitmap.setPixels(colors, 0, width, 0, 0, width, height);
    }

    /**
     * Set the code of the graphic
     * @param code
     * @throws Exception
     */
    public void codeIt(String code) throws Exception {
        TreeNode d = DAG.makeDAG(Parser.parse(code));

        GraphicsModel.getInstance().initializeDAG(new DAGEvaluator(d));
        Log.i("TAG_CREATE_DAG", "Dag created");
    }

    /**
     * Performs the drawing on the view
     */

    public void performDraw(Canvas canvas){

        Rect src = new Rect(0,0,width,height);
        Rect dest = new Rect(0,0,getWidth(),getWidth());

        try {
            updateBitmap();
        } catch (Exception e) {
            if (!error) {
                error = true;
            }
            e.printStackTrace();
        }

        canvas.drawBitmap(bitmap, src, dest, paintBg);

        //invalidate();

    }

    public boolean hasError() {
        return error;
    }

}
