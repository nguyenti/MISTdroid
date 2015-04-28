package edu.grinnell.glimmer.nguyenti.mistdroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Graphics activity that displays the rendered image
 */
public class GraphicsActivity extends Activity {

    AltGraphicsView gView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alt_graphics);

        Intent i = getIntent();
        String code = i.getStringExtra("TAG_CODE");

        gView = (AltGraphicsView) findViewById(R.id.altgraphicsView);
        try {
            gView.codeIt(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CheckForErrorsTask().execute();
    }

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
}
