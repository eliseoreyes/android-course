package edu.fvtc.sharedpreferencesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class activity_editpref extends AppCompatActivity {
    public static final String TAG = "activity_editpref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpref);
        initButton();
        initCheckBox();
        getSettings();

    }

    private void getSettings() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        CheckBox chkDarkMode = findViewById(R.id.chkValue);
        Boolean isDarkMode = new Boolean(preferences.getBoolean("darkmode", false));
        chkDarkMode.setText("DarkMode: " + isDarkMode.toString());
        chkDarkMode.setChecked(isDarkMode);
    }

    private void initCheckBox() {
        CheckBox chkDarkMode = findViewById(R.id.chkValue);

        chkDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Save a value to the SharedPreferences.
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("darkmode", isChecked);
                editor.putString("name", String.valueOf(R.string.app_name));
                editor.putInt("age", 45);

                editor.commit();
                Log.d(TAG, "onCheckedChanged: ");
            }
        });
    }

    private void initButton() {
        Button btnNavigate = findViewById(R.id.btnNavigate);
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });
    }
}