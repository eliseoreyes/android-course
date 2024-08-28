package edu.fvtc.galleryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.BlockingDeque;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add tollBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gallery_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == R.id.charles_leclerc) {
            Log.d(TAG, "onOptionsItemSelected: Charles");
            Intent intent = new Intent(this, FerrariActivity.class );
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.lewis_hamilton) {
            Log.d(TAG, "onOptionsItemSelected: Lewis");
            Intent intent = new Intent(this, MercedesActivity.class );
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.max_verstappen) {
            Intent intent = new Intent(this, RedBullActivity.class );
            startActivity(intent);
            Log.d(TAG, "onOptionsItemSelected: MAx");
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}