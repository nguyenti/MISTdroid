package edu.grinnell.glimmer.nguyenti.mistdroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tiffanynguyen on 2/1/15.
 */
public class GraphicsView extends View {


    public GraphicsView(Context context) {
        super(context);
    }

    public GraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphicsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.L)
    public GraphicsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // canvas.drawBitmap
        // canvas.drawPoint
//        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);
    }
}
