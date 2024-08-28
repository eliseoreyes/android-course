package edu.fvtc.grocerylistdb.api;

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

import edu.fvtc.grocerylistdb.MainActivity;
import edu.fvtc.grocerylistdb.models.MenuOptions;
import edu.fvtc.grocerylistdb.models.Product;

public class RestClient {

    public static final String TAG = "RestClient";

    public static void getRequest(String url, Context context, VolleyCallback volleyCallback, String option) {
        Log.d(TAG, "getRequest: Start" + url);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ArrayList<Product> products = new ArrayList<Product>();

        try {
            Log.d(TAG, "getRequest: starting ");
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Parse the JSON
                            Log.d(TAG, "onResponse: " + response);

                            try {
                                JSONArray items = new JSONArray(response);
                                Product product = null;

                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject item = items.getJSONObject(i);
                                    if (MenuOptions.SHOPPING_LIST.toString().equals(option)) {
                                        if(item.getInt("isInCart") != 1){
                                            Log.d(TAG, "onResponse: "+item.getString("item") + " | "+item.getInt("isInCart"));
                                            continue;
                                        }
                                    }

                                    product = new Product(item.getInt("id")
                                            , item.getString("item")
                                            , item.getInt("isOnShoppingList") == 1 ? true : false
                                            , item.getInt("isInCart") == 1 ? true : false
                                            , item.getString("owner")
                                            , item.getDouble("latitude")
                                            , item.getDouble("longitude")
                                    );

                                    String jsonPhoto = item.getString("photo");

                                    if (jsonPhoto != null) {
                                        byte[] bytePhoto = null;
                                        bytePhoto = Base64.decode(jsonPhoto, Base64.DEFAULT);
                                        Bitmap bmp = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);
                                        product.setPhoto(bmp);
                                    }
                                    products.add(product);
                                }
                            } catch (Exception e) {
                                try {
                                    throw e;
                                } catch (JSONException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            // ALSO SUPER IMPORTANT
                            volleyCallback.onSuccess(products);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void deleteBulkRequest(ArrayList<Product> products, String url, Context context, VolleyCallback volleyCallback){
        try{
            for (Product prod : products) {
                deleteRequest(prod, url.substring(0,49)+prod.getId(), context, volleyCallback);
            }

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static void deleteRequest(Product product, String url, Context context, VolleyCallback volleyCallback){
        try{
            executeRequest(product, url, context, volleyCallback, Request.Method.DELETE);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static void updateBulkRequest(ArrayList<Product> products, String url, Context context, VolleyCallback volleyCallback){

        try{
            for (Product product : products) {
                Log.d(TAG, "updateBulkRequest: "+product.toString());
                execPutRequest(product, url.substring(0,49)+product.getId(), context, volleyCallback);

            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static void execPutRequest(Product product,
                                      String url,
                                      Context context,
                                      VolleyCallback volleyCallback)
    {
        try {
            executeRequest(product, url, context, volleyCallback, Request.Method.PUT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void postRequest(Product product,
                                       String url,
                                       Context context,
                                       VolleyCallback volleyCallback)
    {
        try {
            executeRequest(product, url, context, volleyCallback, Request.Method.POST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void executeRequest(Product product, String url, Context context, VolleyCallback volleyCallback, int method){
        Log.d(TAG, "executeRequest: "+method+" "+url + "" );

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            Log.d(TAG, "executeRequest Product: "+product.toString());

            JSONObject object = new JSONObject();
            object.put("id", product.getId());
            object.put("item", product.getProductDescription());
            object.put("isOnShoppingList", product.getIsOnShoppingList()?1:0);
            object.put("isInCart", product.getIsInCart()?1:0);
            object.put("owner", MainActivity.name);
            object.put("latitude", product.getLatitude());
            object.put("longitude", product.getLongitude());

            if (product.getPhoto() != null){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = Bitmap.createScaledBitmap(product.getPhoto(), 144, 144, false);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String jsonPhoto = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
                object.put("photo", jsonPhoto);
            }else{
                object.put("photo", null);
            }

            final String requestBody = object.toString();
            Log.d(TAG, "executeRequest: " + requestBody);

            JsonObjectRequest request = new JsonObjectRequest(method, url, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "onResponse: " + response);
                            volleyCallback.onSuccess(new ArrayList<Product>());
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

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static void execGetOneRequest(String url,
                                         Context context,
                                         VolleyCallback volleyCallback)
    {
        Log.d(TAG, "execGetOneRequest: Start");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ArrayList<Product> products = new ArrayList<Product>();
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
                                Product product = new Product(object.getInt("id")
                                                            , object.getString("item")
                                                            , object.getInt("isOnShoppingList") == 1 ? true : false
                                                            , object.getInt("isInCart") == 1 ? true : false
                                                            , object.getString("owner")
                                                            , object.getDouble("latitude")
                                                            , object.getDouble("longitude"));

                                String jsonPhoto = object.getString("photo");

                                if(jsonPhoto != null)
                                {
                                    byte[] bytePhoto = null;
                                    bytePhoto = Base64.decode(jsonPhoto, Base64.DEFAULT);
                                    Bitmap bmp = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);
                                    product.setPhoto(bmp);
                                }

                                products.add(product);

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            volleyCallback.onSuccess(products);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: ");
                            Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        }
                    });

            requestQueue.add(stringRequest);

        } catch (Exception e) {
            Log.d(TAG, "getOneRequest: Error" + e.getMessage());
        }
    }
}