package edu.fvtc.blackjackandroidui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;

import java.util.concurrent.BlockingDeque;

import edu.fvtc.blackjackandroidui.api.RestClient;
import edu.fvtc.blackjackandroidui.api.Server;
import edu.fvtc.blackjackandroidui.api.VolleyCallback;
import edu.fvtc.blackjackandroidui.models.User;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.toString();

    public static final String URL = "https://theblackjack.azurewebsites.net/api/User/login?";
    Button btnLogin, btnSignup;
    EditText etUSerName, etPassword;
    User user;
    Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        server = new Server(this);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignuP);
        etUSerName = findViewById(R.id.edtUserName);
        etPassword = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                startActivity(intent);

               // server.loginWithEmailAndPassword(etUSerName.getText().toString().trim(), etPassword.getText().toString().trim());
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}