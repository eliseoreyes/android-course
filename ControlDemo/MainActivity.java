package edu.fvtc.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButton();
    }

    private void initButton() {
        Button btnDisplay = findViewById(R.id.btnDisplay);

        //Add the click event handler
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etName = findViewById(R.id.etName);
                TextView tvName = findViewById(R.id.tvName);

                String text = etName.getText().toString();
                tvName.setText(text);
                Toast.makeText(MainActivity.this,text, Toast.LENGTH_LONG ).show();

            }
        });
    }
}