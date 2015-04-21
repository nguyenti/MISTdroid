package edu.grinnell.glimmer.nguyenti.mistdroid;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_view);

        Button btnRed = (Button) findViewById(R.id.btn_to_graphics);
        final EditText codeText = (EditText) findViewById(R.id.code_text_view);

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check to see if any code was written
                if (codeText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Please type in some code", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(getBaseContext(), GraphicsActivity.class);
                    i.putExtra("TAG_CODE", codeText.getText().toString());
                    startActivity(i);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graphics_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
