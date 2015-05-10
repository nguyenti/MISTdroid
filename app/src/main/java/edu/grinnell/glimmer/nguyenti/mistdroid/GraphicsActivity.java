package edu.grinnell.glimmer.nguyenti.mistdroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TimingLogger;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import edu.grinnell.glimmer.nguyenti.mistdroid.dagmaking.DAG;
import edu.grinnell.glimmer.nguyenti.mistdroid.data.Pixel;
import edu.grinnell.glimmer.nguyenti.mistdroid.evaluating.DAGEvaluator;
import edu.grinnell.glimmer.nguyenti.mistdroid.parsing.Parser;
import edu.grinnell.glimmer.nguyenti.mistdroid.parsing.TreeNode;

/**
 * Graphics activity that displays the rendered image
 */
public class GraphicsActivity extends Activity {

    AltGraphicsView gView;
    SurView mSurView;
    //AltGraphicsView agView;

    int time=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get context information
        Intent i = getIntent();
        String code = i.getStringExtra("TAG_CODE");


    //grpahics controller
        //agView = new AltGraphicsView(code);

        //Surfaceview view
        mSurView = new SurView(this);
    try{
        mSurView.codeIt(code);
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

        setContentView(mSurView);

    }

    /*
    private class CheckForErrorsTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            while (!gView.hasError());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),
                    "There was an issue with your code. Please try again.",
                    Toast.LENGTH_SHORT).show();
        }
    }

*/

    @Override
    protected void onPause() {
        super.onPause();
        mSurView.pause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSurView.resume();
    }

    public class SurView extends SurfaceView implements Runnable{

        Thread mThread = null;
        SurfaceHolder mSurfaceHolder;
        boolean running = false;
        int round =0;
        private long startnow;
        private long endnow;

        PanelThread panelThread;
        private Paint paintBg;
        private boolean error = false;

        int height = 200;
        int width = 200;
        Bitmap bitmap = Bitmap.createBitmap(new DisplayMetrics(), width, height,
                Bitmap.Config.ARGB_8888);



        public SurView(Context context){
            super(context);
            paintBg = new Paint();
            mSurfaceHolder = getHolder();
        }

        @Override
        public void onDraw(Canvas canvas){
            time++;
            if(time == 100) {
                endnow = android.os.SystemClock.uptimeMillis();
                Log.d("MYTAG", "Excution time: "+(endnow-startnow)+" ms");
            }
            round = (round + 1) % height;
            performDraw(canvas);
        }

        @Override
        public void run() {
            startnow = android.os.SystemClock.uptimeMillis();
            while (running)
            {
                if(!mSurfaceHolder.getSurface().isValid())
                {
                    continue;
                }
                Canvas c = mSurfaceHolder.lockCanvas();
                onDraw(c);
                mSurfaceHolder.unlockCanvasAndPost(c);
            }

        }

        public void pause(){
            running = false;
            while(true)
            {
                try {
                    mThread.join();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                break;
            }
            mThread = null;
        }

        public void resume(){
            running = true;
            mThread = new Thread(this);
            mThread.start();
        }





        /**
         * Set the bitmap of the graphic. This may need to be changed with time functions
         * @throws Exception
         */
        /*
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
        */

        private void updateBitmap() {
            int size = height * width;
            int colors[] = new int[size];
            for (int i = 0; i < size; i++) {
                if (i >= round * width && i < (round + 1) * width)
                    colors[i] = new Pixel(1).RGBToInt();
                else
                    colors[i] = new Pixel(.01).RGBToInt(); // there's a bug here with 0
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
        }

    }
}
