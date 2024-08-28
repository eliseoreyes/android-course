package edu.fvtc.grocerylistdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class SetOwner extends AppCompatActivity {

    public static final String TAG = "SetOwner";
    EditText edtName;
    Button btnSave;
    String nameStr;

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_owner);
        edtName = findViewById(R.id.edtOwner);
        btnSave = findViewById(R.id.btnSave);
        setTitle("GroceryList - Set Owner");

        sp = getSharedPreferences("ownerPref", Context.MODE_PRIVATE);
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                nameStr = edtName.getText().toString();
                Log.d(TAG, "onClick: "+nameStr);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name",nameStr);
                editor.commit();
                Toast.makeText(SetOwner.this, "Owner Saved", Toast.LENGTH_LONG).show();

                // Go to main activity
                Intent intent = new Intent(SetOwner.this, MainActivity.class );
                startActivity(intent);
            }
        });
    }
}