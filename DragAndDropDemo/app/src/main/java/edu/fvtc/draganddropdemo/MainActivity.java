package edu.fvtc.draganddropdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.toString();
    HubConnection hubConnection;
    String hubConnectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
        initSignalRGroup();
    }

    private void initSignalRGroup() {
        hubConnection = HubConnectionBuilder
                .create("https://fvtcdp.azurewebsites.net/GameHub")
                .build();
        Log.d(TAG, "initSignalRGroup: Starting the Hub connection...");

        hubConnection.start().blockingAwait();
        hubConnection.invoke(Void.class, "GetConnectionId");
        hubConnectionId = hubConnection.getConnectionId();

        Log.d(TAG, "initSignalRGroup: ConnectionId "+hubConnectionId);

        hubConnection.on("ReceiveMessage", (user, message) -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run::"+user +"::"+message);
                    /// Move the card
                }
            });
        }, String.class, String.class);

        //Sending a message
        //hubConnection.send("JoinGame", "Bingo", "Eli");
    }

    private class DrawView extends View implements View.OnTouchListener{

        int lastTouchX, lastTouchY;
        int posX = 20, posY = 20;
        int dx, dy;
        boolean isDragging;
        Bitmap aceOfSpades = BitmapFactory.decodeResource(getResources(), R.drawable.acespades);

        public DrawView(Context context) {
            super(context);
            this.setOnTouchListener(this);
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            canvas.drawBitmap(aceOfSpades, posX, posY, null);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();

            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "onTouch: ACTION DOWN");

                    Rect box = new Rect(posX, posY, posY + aceOfSpades.getWidth(), posY + aceOfSpades.getHeight());

                    if (!box.contains(x, y)) break;

                    isDragging = true;
                    lastTouchX = x;
                    lastTouchY = y;

                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d(TAG, "onTouch: ACTION MOVE");

                    if (!isDragging) break;

                    dx = x - lastTouchX;
                    dy = y - lastTouchY;

                    lastTouchX = x;
                    lastTouchY = y;

                    posX += dx;
                    posY  += dy;

                    //redraw the screen
                    invalidate();

                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "onTouch: ACTION_UP");
                    isDragging = false;
                    break;
            }
            return true;
        }
    }
}