package edu.grinnell.glimmer.nguyenti.mistdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Graphics activity that displays the rendered image
 */
public class GraphicsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        Intent i = getIntent();
        String code = i.getStringExtra("TAG_CODE");

        final GraphicsView gView = (GraphicsView) findViewById(R.id.graphicsView);
        try {
            gView.codeIt(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
