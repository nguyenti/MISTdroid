package edu.grinnell.glimmer.nguyenti.mistdroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import edu.grinnell.glimmer.nguyenti.mistdroid.data.Pixel;

import static android.graphics.Bitmap.Config;

/**
 * Created by tiffanynguyen on 2/1/15.
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
        paintBg.setColor(Color.BLUE);

        paintPoints = new Paint();
        paintPoints.setColor(Color.RED);

        paintwidth = new Paint();
        paintwidth.setStrokeWidth(strokeWidth);
        paintwidth.setColor(Color.RED);

        GraphicsModel instance = GraphicsModel.getInstance();
        instance.initializeGrid(gridSize);
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
        updateBitmap();
        round = (round + 1) % height;
        canvas.drawBitmap(bitmap, src, dest, paintBg);

        invalidate();
    }

    private void updateBitmap() {
        int size = height * width;
        int colors[] = new int[size];
        for (int i = 0; i < size; i++) {
            if (i >= round * width && i < (round + 1) * width)
                colors[i] = new Pixel(1).gradientToRGB();
            else
                colors[i] = new Pixel(.01).gradientToRGB(); // there's a bug here with 0
        }
        bitmap.setPixels(colors, 0, width, 0, 0, width, height);
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
