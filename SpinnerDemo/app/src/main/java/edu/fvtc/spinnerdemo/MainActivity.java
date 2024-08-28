package edu.fvtc.spinnerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String[] items = {
            "Call of Duty",
            "Counter-Strike",
            "Battlefield 1942",
            "Hogwarts Legacy",
            "World of Warcraft",
            "Baldur's Gate",
            "Icewind Dale"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSpinner();

    }

    private void initSpinner() {
        Spinner videogames = findViewById(R.id.spinner);

        // Create a binding between the items and the control.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                items);
        // Set the display mode
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        videogames.setAdapter(arrayAdapter);

        videogames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                Log.d(TAG, "onItemSelected: " + items[index] + ":" + l);
                TextView textView = findViewById(R.id.tvInfo);
                textView.setText(items[index]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}