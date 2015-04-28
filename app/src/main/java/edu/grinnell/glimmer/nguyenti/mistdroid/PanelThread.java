package edu.grinnell.glimmer.nguyenti.mistdroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by albertowusu-asare on 4/25/15.
 */
public class PanelThread extends Thread {

    private SurfaceHolder _surfaceHolder;
    private AltGraphicsView _panel;
    private boolean _run = false;
    private boolean isDrawing = true;


    public PanelThread(AltGraphicsView _panel) {
        this._panel = _panel;
        this._surfaceHolder = _panel.getHolder();
    }

    public PanelThread(SurfaceHolder surfaceHolder, AltGraphicsView panel, Bitmap bitmap) {
       this._surfaceHolder = surfaceHolder;
        this._panel = panel;
    }


    /**
     * Sets the run boolean
     */
    public void setRunning(boolean run) { //Allow us to stop the thread
        _run = run;
    }


    @Override
    public void run() {
        super.run();
        while (_run) {     //When setRunning(false) occurs, _run is

            Canvas canvas = _surfaceHolder.lockCanvas();
            //set to false and loop ends, stopping thread
            if(canvas != null){
                synchronized (_surfaceHolder) {
                    _panel.onDraw(canvas);
                   // _panel.performDraw(canvas);
                    _panel.invalidate();
                }
                _surfaceHolder.unlockCanvasAndPost(canvas);
            }

            }
        }
    }


