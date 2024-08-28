package edu.fvtc.blackjackandroidui.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import edu.fvtc.blackjackandroidui.models.User;

public class RestClient {
    public static final String TAG = RestClient.class.toString();

    public static void postRequest(User user, String url, Context context, VolleyCallback volleyCallback){

        try{
            executeRequest(user, url, context, volleyCallback, Request.Method.POST);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void executeRequest(User user, String url, Context context, VolleyCallback volleyCallback, int method){

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            Log.d(TAG, "executeRequest: "+user.toString());
            JSONObject object = new JSONObject();

            object.put("username", user.getUserName());
            object.put("password", user.getPassword());

            final String requestBody = object.toString();

            JsonObjectRequest request = new JsonObjectRequest(method, url, object,
                    new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "onResponse: " + response);
                    volleyCallback.onSuccess(new User());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error.getMessage());
                }
            }) {
                @Override
                public byte[] getBody(){
                    Log.d(TAG, "getBody: "+object.toString());
                    return object.toString().getBytes(StandardCharsets.UTF_8);

                }
            };

            Log.d(TAG, "executeRequest:body "+request.getBody());
            requestQueue.add(request);

        }catch(Exception ex){
            Log.d(TAG, "executeRequestEX: "+ex.getMessage());
        }
    }
}
