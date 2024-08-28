package edu.fvtc.galleryapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import edu.fvtc.galleryapp.entity.Driver;

public class MercedesActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    public static final String TAG = "MainActivity";
    private ImageView imageView1, imageView2;
    private TextView textView;

    private ManageFile manageFile = new ManageFile();

    private Driver driver = new Driver();

    GestureDetector gestureDetector = null;

    String [] colors = {"#AAB7B8", "#D7DBDD","#F2F4F4", "#AED6F1","#EBDEF0"};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercedes);

        //Add tollBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.txvBio);

        textView.setVisibility(View.INVISIBLE);

        gestureDetector = new GestureDetector(this,this);
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


    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        Log.d(TAG, "onTouchEvent: ");
        return gestureDetector.onTouchEvent(motionEvent);
    }
    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapUp: ");
        textView.setVisibility(View.VISIBLE);

        int index = 0;
        Random rand = new Random();

        index = rand.nextInt(colors.length);

        try {
            driver.setDriverBio(ManageFile.readFile(R.raw.lewis, this));
            textView.setText(driver.getDriverBio());
            textView.setBackgroundColor(Color.parseColor(colors[index].toString()));
            textView.setMovementMethod(new ScrollingMovementMethod());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {

    }
    private class AnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.d(TAG, "onAnimationEnd: ");

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        Intent intent = new Intent(this, RedBullActivity.class );
        startActivity(intent);
        return true;
    }
}
