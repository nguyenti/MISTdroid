package edu.grinnell.glimmer.nguyenti.mistdroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import edu.grinnell.glimmer.nguyenti.mistdroid.dagmaking.DAG;
import edu.grinnell.glimmer.nguyenti.mistdroid.data.Pixel;
import edu.grinnell.glimmer.nguyenti.mistdroid.evaluating.DAGEvaluator;
import edu.grinnell.glimmer.nguyenti.mistdroid.parsing.Parser;
import edu.grinnell.glimmer.nguyenti.mistdroid.parsing.TreeNode;

import static android.graphics.Bitmap.Config;

/**
 * View that contains a Canvas that takes code and displays an image on the canvas
 */
public class GraphicsView extends View {


    private int gridSize = 200;
    private int strokeWidth = 50;

    private Paint paintBg;
    private Paint paintPoints;
    private Paint paintwidth;


    public GraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);


        paintBg = new Paint();
//        paintBg.setColor(Color.BLUE);

        paintPoints = new Paint();
        paintPoints.setColor(Color.RED);

        paintwidth = new Paint();
        paintwidth.setStrokeWidth(strokeWidth);
        paintwidth.setColor(Color.RED);


    }

    int height = 100;
    int width = 100;
    int round = 0;
    Bitmap bitmap = Bitmap.createBitmap(new DisplayMetrics(), width, height, Config.ARGB_8888);
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect src = new Rect(0,0,width,height);
        Rect dest = new Rect(0,0,getWidth(),getWidth());

//        Bitmap bitmap = makeBitmap(round);
//        round = (round + 1) % height;
//        canvas.drawBitmap(bitmap, src, dest, paintBg);
        try {
            updateBitmap();
        } catch (Exception e) {
            e.printStackTrace();
        }


        canvas.drawBitmap(bitmap, src, dest, paintBg);

        invalidate();
    }

    public void codeIt(String code) throws Exception {
        TreeNode d = DAG.makeDAG(Parser.parse(code));

        GraphicsModel.getInstance().initializeDAG(new DAGEvaluator(d));
        Log.i("TAG_CREATE_DAG", "Dag created");
//        updateBitmap();
    }

    private void updateBitmap() throws Exception {
        int size = height * width;
        int colors[] = new int[size];
        Pixel pix[] = new Pixel[size];
        GraphicsModel instance = GraphicsModel.getInstance();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double x = j / (width * 1.0) * 2.0 - 1.0;
                double y = i / (height * 1.0) * 2.0 - 1.0;
                pix[j + i * height] = instance.getDAG().evaluate(x, y);
//                colors[j + i * height] = (new Pixel(-1)).gradientToRGB();
                colors[j + i * height] = pix[j + i * height].gradientToRGB();
            }
        }
        /**
        for (int i = 0; i < size; i++) {
            if (i >= round * width && i < (round + 1) * width)
                colors[i] = new Pixel(-1).gradientToRGB();
            else
                colors[i] = new Pixel(1).gradientToRGB(); // there's a bug here with 0
         }
         **/
        bitmap.setPixels(colors, 0, width, 0, 0, width, height);
//        invalidate();
    }

    private Bitmap makeBitmap(int round) {
        int size = height * width;
        int colors[] = new int[size];
        for (int i = 0; i < size; i++) {
            if (i >= round * width && i < (round + 1) * width)
                colors[i] = new Pixel(1).gradientToRGB();
            else
                colors[i] = new Pixel(.01).gradientToRGB(); // there's a bug here with 0
        }
        Bitmap bitmap = Bitmap.createBitmap(new DisplayMetrics(), colors, width, height, Config.ARGB_8888);

        return bitmap;
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < 10; i+=strokeWidth) {
            for (int j = 0; j < 10; j+=strokeWidth) {
//                Pixel instance = GraphicsModel.getInstance().getFieldContent(i, j);
//                paintPoints.setColor(instance.gradientToRGB());

                canvas.drawPoint(i,j,paintwidth);
            }
        }

        invalidate();
    }

    // to keep the width and height as a square
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int w = MeasureSpec.getSize(widthMeasureSpec);
//        int h = MeasureSpec.getSize(heightMeasureSpec);
//        int d = (w == 0) ? h : (h == 0) ? w : (w < h) ? w : h;
//        setMeasuredDimension(d, d);
//    }
}
