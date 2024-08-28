package edu.fvtc.teams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RestClient {
    public static final String TAG = "RestClient";

    public static void execGetRequest(String url,
                                      Context context,
                                      VolleyCallback volleyCallback)
    {
        Log.d(TAG, "execGetRequest: Start: " + url);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ArrayList<Team> teams = new ArrayList<Team>();

        try{
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Parse the JSON
                            Log.d(TAG, "onResponse: " + response);

                            try {
                                // Convert the paragraph to an array of JSON
                                // ArrayList<String>
                                JSONArray items = new JSONArray(response);

                                for(int i = 0; i < items.length(); i++)
                                {
                                    // Convert one string to a json object
                                    JSONObject item = items.getJSONObject(i);
                                    Team team = new Team();
                                    team.setId(item.getInt("id"));
                                    team.setName(item.getString("name"));
                                    team.setCity(item.getString("city"));
                                    team.setCellphone(item.getString("cellNumber"));
                                    team.setRating((float)item.getDouble("rating"));
                                    team.setIsfavorite(item.getBoolean("isFavorite"));
                                    team.setLatitude(item.getDouble("latitude"));
                                    team.setLongitude(item.getDouble("longitude"));

                                    String jsonPhoto = item.getString("photo");

                                    if(jsonPhoto != null)
                                    {
                                        byte[] bytePhoto = null;
                                        bytePhoto = Base64.decode(jsonPhoto, Base64.DEFAULT);
                                        Bitmap bmp = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);
                                        team.setPhoto(bmp);
                                    }

                                    teams.add(team);
                                }
                            }
                            catch(Exception e)
                            {
                                try {
                                    throw e;
                                } catch (JSONException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            // ALSO SUPER IMPORTANT
                            volleyCallback.onSuccess(teams);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: " + error.getMessage() );
                        }
                    });
            // IMPORTANT - ADD TO THE QUEUE
            Log.d(TAG, "execGetRequest: Add request");
            requestQueue.add(stringRequest);
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public static void execDeleteRequest(Team team,
                                         String url,
                                         Context context,
                                         VolleyCallback volleyCallback)
    {
        try {
            executeRequest(team, url, context, volleyCallback, Request.Method.DELETE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void execPutRequest(Team team,
                                      String url,
                                      Context context,
                                      VolleyCallback volleyCallback)
    {
        try {
            executeRequest(team, url, context, volleyCallback, Request.Method.PUT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void execPostRequest(Team team,
                                       String url,
                                       Context context,
                                       VolleyCallback volleyCallback)
    {
        try {
            executeRequest(team, url, context, volleyCallback, Request.Method.POST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void executeRequest(Team team,
                                       String url,
                                       Context context,
                                       VolleyCallback volleyCallback,
                                       int method)
    {
        Log.d(TAG, "executeRequest: " + method + ":" + url);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject object = new JSONObject();

            object.put("id", team.getId());
            object.put("name", team.getName());
            object.put("city", team.getCity());
            object.put("rating", team.getRating());
            object.put("cellNumber", team.getCellphone());
            object.put("isFavorite", team.getIsfavorite());
            object.put("latitude", team.getLatitude());
            object.put("longitude", team.getLongitude());

            if(team.getPhoto() != null)
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = Bitmap.createScaledBitmap(team.getPhoto(), 144, 144, false);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String jsonPhoto = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
                object.put("photo", jsonPhoto);
            }
            else
            {
                object.put("photo", null);
            }

            final String requestBody = object.toString();
            Log.d(TAG, "executeRequest: " + requestBody);

            JsonObjectRequest request = new JsonObjectRequest(method, url, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "onResponse: " + response);
                            // ALSO SUPER IMPORTANT
                            volleyCallback.onSuccess(new ArrayList<Team>());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error.getMessage());
                }
            })
            {
                @Override
                public byte[] getBody(){
                    Log.i(TAG, "getBody: " + object.toString());
                    return object.toString().getBytes(StandardCharsets.UTF_8);
                }
            };

            requestQueue.add(request);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void execGetOneRequest(String url,
                                         Context context,
                                         VolleyCallback volleyCallback)
    {
        Log.d(TAG, "execGetOneRequest: Start");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ArrayList<Team> teams = new ArrayList<Team>();
        Log.d(TAG, "execGetOneRequest: " + url);

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "onResponse: " + response);

                            try {
                                JSONObject object = new JSONObject(response);
                                Team team = new Team();
                                team.setId(object.getInt("id"));
                                team.setName(object.getString("name"));
                                team.setCity(object.getString("city"));
                                team.setRating((float)object.getDouble("rating"));
                                team.setCellphone(object.getString("cellNumber"));
                                team.setIsfavorite(object.getBoolean("isFavorite"));

                                team.setLatitude(object.getDouble("latitude"));
                                team.setLongitude(object.getDouble("longitude"));

                                String jsonPhoto = object.getString("photo");

                                if(jsonPhoto != null)
                                {
                                    byte[] bytePhoto = null;
                                    bytePhoto = Base64.decode(jsonPhoto, Base64.DEFAULT);
                                    Bitmap bmp = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);
                                    team.setPhoto(bmp);
                                }

                                teams.add(team);

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            volleyCallback.onSuccess(teams);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: ");
                            Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        }
                    });

            // Important!!!
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            Log.d(TAG, "execGetOneRequest: Error" + e.getMessage());
        }
    }
}
