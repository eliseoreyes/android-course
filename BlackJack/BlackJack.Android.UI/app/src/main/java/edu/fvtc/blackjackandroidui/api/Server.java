package edu.fvtc.blackjackandroidui.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Server implements ServerInterface{

    public static final String TAG = Server.class.toString();
    public static final String API_URL = "https://theblackjack.azurewebsites.net/api/User/login?";
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    Context context;

    public Server(Context context){
        this.context = context;
    }
    @Override
    public void loginWithEmailAndPassword(String email, String password) {
        requestQueue = Volley.newRequestQueue(context);
        Log.d(TAG, "onResponse: 1 ");
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                API_URL + "username=" + email + "&password=" + password,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("message");
                        }catch(JSONException jsonException){
                            Log.d(TAG, "onResponse: Cannot connect");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: cannot connect");
            }
        });
        Log.d(TAG, "loginWithEmailAndPassword: ");
    }
    
}
