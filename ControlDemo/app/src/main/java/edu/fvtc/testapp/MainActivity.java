package edu.fvtc.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.verify.domain.DomainVerificationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        //initButton();
        initTextChangedEvent();

    }

    private void initTextChangedEvent() {
        //Get the edit text so we can get the text
        EditText etName = findViewById(R.id.etName);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Toast.makeText(MainActivity.this, "Text change", Toast.LENGTH_LONG).show();
                TextView tvName = findViewById(R.id.tvName);
                tvName.setText(etName.getText());
            }
        });
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