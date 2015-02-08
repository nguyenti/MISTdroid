package edu.grinnell.glimmer.nguyenti.mistdroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tiffanynguyen on 2/1/15.
 */
public class GraphicsView extends View {


    private int gridSize = 300;

    private Paint paintBg;
    private Paint paintPoints;

    public GraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.WHITE);

        paintPoints = new Paint();
        paintPoints.setColor(Color.BLACK);

        GraphicsModel instance = GraphicsModel.getInstance();
        instance.initializeGrid(gridSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, 300, 300, paintBg);

//        float points[] = new float[]{200,200,150,150, 201,201,202,202};

//        canvas.drawPoints(points, paintPoints);
        canvas.drawPoint(300,300,paintPoints);
        // canvas.drawBitmap
        // canvas.drawPoint
        drawGrid(canvas);
    }
    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Pixel instance = GraphicsModel.getInstance().getFieldContent(i, j);
                paintPoints.setColor(instance.gradientToRGB());
                canvas.drawPoint(i,j,paintPoints);
            }
        }

        invalidate();
    }
}
