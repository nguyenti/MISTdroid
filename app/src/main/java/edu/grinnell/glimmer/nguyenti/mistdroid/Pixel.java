package edu.grinnell.glimmer.nguyenti.mistdroid;


import android.graphics.Color;

import java.util.Random;

/**
 * Created by tiffanynguyen on 2/8/15.
 */
public class Pixel {

    double gradient;

    public Pixel() {
        gradient = Math.random();
    }

    public Pixel(double greyscale) {
        this.gradient = greyscale;
    }

    public double getGreyscale() {
        return gradient;
    }

    public void setGreyscale(int greyscale) {
        this.gradient = greyscale;
    }

    public int gradientToRGB() {
        int rVal = (int) (gradient * 256);


//        Color RGB = new Color();
//        RGB.rgb(rVal, rVal, rVal);
//        int val = rVal * 256 * 256 + (rVal * 256) + rVal;

        int R = Math.round(255 * rVal);
        int G = Math.round(255 * rVal);
        int B = Math.round(255 * rVal);

        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;

        return 0xFF000000 | R | G | B;

//        return val;//RGB.parseColor();
    }


}
