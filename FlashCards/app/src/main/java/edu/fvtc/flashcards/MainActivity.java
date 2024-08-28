package edu.fvtc.flashcards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingDeque;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public static final String TAG = "MainActivity";

    State[] states =
            {
                    new State("Wisconsin","OshKosh"),
                    new State("Minnesota","Rochester"),
                    new State("Ohio","Cinncinati")
            };

    int [] images = {R.drawable.wisconsin, R.drawable.minnesota, R.drawable.ohio};
    int [] textFiles = {R.raw.wisconsin, R.raw.wisconsin, R.raw.ohio};
    ImageView imgCard;
    TextView tvCard;

    GestureDetector gestureDetector;
    int cardNo = 2;
    boolean isFront = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCard = findViewById(R.id.imgCard);
        tvCard = findViewById(R.id.tvCard);

        gestureDetector = new GestureDetector(this,this);

        updateToNextCard();

        Log.d(TAG, "onCreate: End");
    }

    private void updateToNextCard(){

        states[cardNo].setCapital(readFile(textFiles[cardNo]));

        isFront = true;
        imgCard.setVisibility(View.VISIBLE);
        imgCard.setImageResource(images[cardNo]);
        tvCard.setText(states[cardNo].getName());

    }

    //Must have for gesturing to work!!!
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.d(TAG, "onTouchEvent: ");
        return gestureDetector.onTouchEvent(motionEvent);
    }
    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        Log.d(TAG, "onDown: ");
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapUp: ");

        String message;

        try {
            if (isFront) {
                message = "Go to Back";
                imgCard.setVisibility(View.INVISIBLE);
                tvCard.setText(states[cardNo].getCapital());
            }else {
                message = "Go to Front";
                imgCard.setVisibility(View.VISIBLE);
                tvCard.setText(states[cardNo].getName());
            }
            isFront = !isFront;
            Log.d(TAG, "onSingleTapUp: "+message);

            return true;
        }catch (Exception ex){
            Log.e(TAG, "onSingleTapUp: " + ex.getMessage() );
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {
        Log.d(TAG, "onLongPress: ");
    }

    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {

        int cards = states.length;

        try {

            int x1 = (int) motionEvent.getX();
            int x2 = (int) motionEvent1.getX();

            if (x1 < x2) {
                Log.d(TAG, "onFling: MovRight");
                Animation mover = AnimationUtils.loadAnimation(this, R.anim.moveright);
                mover.setAnimationListener(new AnimationListenerr());
                imgCard.startAnimation(mover);
                tvCard.startAnimation(mover);
                /*{
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/
                cardNo = (cardNo - 1 + cards) % cards;
            }else {
                Log.d(TAG, "onFling: movLeft");
                Animation mover = AnimationUtils.loadAnimation(this, R.anim.moveleft);
                mover.setAnimationListener(new AnimationListenerr());
                imgCard.startAnimation(mover);
                tvCard.startAnimation(mover);
                cardNo = (cardNo + 1) % cards;
            }
            //updateToNextCard();
        }catch (Exception ex){
            Log.e(TAG, "onFling: "+ex.getMessage() );
        }

        return true;
    }
    private class AnimationListenerr implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.d(TAG, "onAnimationEnd: ");
            updateToNextCard();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private String readFile(int fileId)
    {
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer;

        try {
            inputStream = getResources().openRawResource(fileId);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            stringBuffer = new StringBuffer();

            String data;

            while((data = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(data + "\n");
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            Log.d(TAG, "readFile: " + stringBuffer.toString());
            return stringBuffer.toString();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}