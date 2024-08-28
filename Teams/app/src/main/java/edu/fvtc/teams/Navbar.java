package edu.fvtc.teams;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Navbar {

    public static final String TAG = "Navbar";

    public static void initListButton(Activity activity)
    {
        Log.d(TAG, "initListButton: ");
        ImageButton ibList = activity.findViewById(R.id.imageButtonList);
        setUpListenerEvent(ibList, activity, TeamListActivity.class);
    }

    public static void initMapButton(Activity activity)
    {
        Log.d(TAG, "initMapButton: ");
        ImageButton ibMap = activity.findViewById(R.id.imageButtonMap);
        setUpListenerEvent(ibMap, activity, TeamMapActivity.class);
    }

    public static void initSettingsButton(Activity activity)
    {
        Log.d(TAG, "initSettingsButton: ");
        ImageButton ibSettings = activity.findViewById(R.id.imageButtonSettings);
        setUpListenerEvent(ibSettings, activity, TeamSettingsActivity.class);
    }


    private static void setUpListenerEvent(ImageButton imageButton,
                                           Activity fromActivity,
                                           Class<?> destinationActivityClass)
    {

        // Disable the button if need be.
        imageButton.setEnabled(fromActivity.getClass() != destinationActivityClass);

        // Set the onclick
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                Intent intent = new Intent(fromActivity, destinationActivityClass);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                fromActivity.startActivity(intent);
            }
        });
    }

}
