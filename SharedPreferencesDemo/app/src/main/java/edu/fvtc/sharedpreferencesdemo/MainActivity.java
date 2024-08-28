package edu.fvtc.sharedpreferencesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButton();
    }

    @Override
    public void onResume()
    {
        Log.d(TAG, "onResume: ");
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        CheckBox chkDarkMode = findViewById(R.id.chkValue);
        Boolean isDarkMode = new Boolean(preferences.getBoolean("darkmode", false));
        chkDarkMode.setText("DarkMode: " + isDarkMode.toString());
        chkDarkMode.setChecked(isDarkMode);
        Log.d(TAG, "onResume: " + isDarkMode);
        super.onResume();

    }

    private void initButton() {
        Button btnNavigate = findViewById(R.id.btnNavigate);

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the other activity
                startActivity(new Intent(view.getContext(), activity_editpref.class));
            }
        });
    }
}