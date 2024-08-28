package edu.fvtc.grocerylistdb;


import static androidx.constraintlayout.motion.widget.Debug.getLocation;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.fvtc.grocerylistdb.api.RestClient;
import edu.fvtc.grocerylistdb.api.VolleyCallback;
import edu.fvtc.grocerylistdb.models.MenuOptions;
import edu.fvtc.grocerylistdb.models.Product;

public class GroceryLocation extends AppCompatActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    public static final String TAG = "GroceryLocation";
    final int PERMISSION_REQUEST_LOCATION = 101;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int REQUEST_LOCATION = 1;

    public static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    GoogleMap gMap;
    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    ArrayList<Product> products = new ArrayList<>();
    Product currentProduct = null;
    int productId;
    MainActivity main = new MainActivity();
    EditText editLatitude;
    EditText editLongitude;
    Button btnGetCurrentLocation;
    LocationManager locationManager;
    String latitude;
    String longitude;
    EditText editAddress;
    EditText editCity;
    EditText editState;
    EditText editZipcode;
    TextView textAddress;

    boolean locationPermissionsGranted = false;

    public static final float DEFAULT_ZOOM = 15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_location);

        getLocationPermission();

        editLatitude = findViewById(R.id.editLatitude);
        editLongitude = findViewById(R.id.editLongitude);
        editAddress = findViewById(R.id.editAddress);
        editCity = findViewById(R.id.editCity);
        editState = findViewById(R.id.editState);
        editZipcode = findViewById(R.id.editZipcode);
        textAddress = findViewById(R.id.textAddress);
        btnGetCurrentLocation = findViewById(R.id.btnGetLocation);

        Bundle extras = getIntent().getExtras();
        productId = extras.getInt("productId");
        this.setTitle("ProductId: " + productId);


        readFromApi();

        if (productId != -1) {
            initProduct(productId);
        } else {
            currentProduct = new Product();
        }

        initFindLocationButton();
        initSaveButton();
        initLookUp();
        initGetCurrentLocation();

        createLocationCallback();
    }

    private Address geoLocation(){
        Log.d(TAG, "geoLocation: GEOLOCATION");
        String searchString = editCity.getText().toString();
        Geocoder geocoder = new Geocoder(GroceryLocation.this);
        Address address = new Address(Locale.US);

        List<Address> addressList = new ArrayList<>();

        try {
            addressList = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException ex){
            Log.d(TAG, "geoLocation: IOException " +ex.getMessage());
        }

        if (addressList.size() > 0) {
             address = addressList.get(0);
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
            Log.d(TAG, "geoLocation: "+address.toString());
        }
        return address;
    }
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(GroceryLocation.this);
    }

    private void getLocationPermission() {
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionsGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            locationPermissionsGranted = true;
                            return;
                        }
                    }
                    locationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }

    private void getCurrentLocation() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (locationPermissionsGranted) {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Found location");
                            Location currentLocation = (Location) task.getResult();

                            editLatitude.setText(Double.valueOf(currentLocation.getLatitude()).toString());
                            editLongitude.setText(Double.valueOf(currentLocation.getLongitude()).toString());

                            currentProduct.setLatitude(currentLocation.getLatitude());
                            currentProduct.setLongitude(currentLocation.getLongitude());
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "I'm Here!");
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(GroceryLocation.this, "Unable to get current location", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        } catch (SecurityException sec) {
            Log.d(TAG, "getLocation: " + sec.getMessage());
        }

    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: " + latLng.latitude + " long " + latLng.longitude);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        gMap.addMarker(options);
    }

    private void initLookUp() {

        Button buttonGetLookUp = findViewById(R.id.btnLookup);

        buttonGetLookUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showControl(v);
                Address address = geoLocation();
                editAddress.setText(address.getAddressLine(0));
                editCity.setText(address.getLocality());
                editState.setText(address.getAdminArea().toUpperCase());
                editZipcode.setText(address.getPostalCode() == "" ? address.getPostalCode() : editZipcode.getText().toString());
                moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getLocality());
            }
        });
    }

    private void hideControl(View view){
        editAddress.setVisibility(view.INVISIBLE);
        editCity.setVisibility(view.INVISIBLE);
        editState.setVisibility(view.INVISIBLE);
        editZipcode.setVisibility(view.INVISIBLE);
        textAddress.setVisibility(view.INVISIBLE);
    }

    private void showControl(View view){
        editAddress.setVisibility(view.VISIBLE);
        editCity.setVisibility(view.VISIBLE);
        editState.setVisibility(view.VISIBLE);
        editZipcode.setVisibility(view.VISIBLE);
        textAddress.setVisibility(view.VISIBLE);
    }

    private void initGetCurrentLocation() {
        btnGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideControl(view);
                getCurrentLocation();
                //moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "I'm Here!");
            }
        });
    }

    private void initFindLocationButton() {
        Button buttonFindLocation = findViewById(R.id.btnFindLocation);
        buttonFindLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Address address = geoLocation();
                editAddress.setText(address.getAddressLine(0));
                editCity.setText(address.getLocality());
                editState.setText(address.getAdminArea().toUpperCase());
                editZipcode.setText(address.getPostalCode() == "" ? address.getPostalCode() : editZipcode.getText().toString());

                currentProduct.setLatitude(address.getLatitude());
                currentProduct.setLongitude(address.getLongitude());
                moveCamera(new LatLng(Double.valueOf(editLatitude.getText().toString()), Double.valueOf(editLongitude.getText().toString())), DEFAULT_ZOOM, address.getLocality());
            }
        });
    }

    private void initSaveButton() {
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productId == -1) {
                    Log.d(TAG, "Inserting: " + currentProduct.toString());
                    RestClient.postRequest(currentProduct, main.getAPI(), GroceryLocation.this, new VolleyCallback() {
                        @Override
                        public void onSuccess(ArrayList<Product> result) {
                            Log.d(TAG, "onSuccess: " + currentProduct.toString());
                            Intent intent = new Intent(GroceryLocation.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    RestClient.execPutRequest(currentProduct,
                            main.getAPI().substring(0, 49) + productId,
                            GroceryLocation.this,
                            new VolleyCallback() {
                                @Override
                                public void onSuccess(ArrayList<Product> result) {
                                    Log.d(TAG, "onSuccess: " + currentProduct.toString());
                                    Intent intent = new Intent(GroceryLocation.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                }
            }
        });

    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Toast.makeText(getBaseContext(), "Lat: " + location.getLatitude() +
                                    " Long: " + location.getLongitude() +
                                    " Accuracy:  " + location.getAccuracy(),
                            Toast.LENGTH_LONG).show();
                }
            }

            ;
        };
    }

    private void initProduct(int productId) {

        try {
            Log.d(TAG, "initProduct: " + productId);
            RestClient.execGetOneRequest(main.getAPI().substring(0, 49) + productId, this, new VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Product> result) {
                    currentProduct = result.get(0);
                    Log.d(TAG, "onSuccess: initProduct " + currentProduct.getLatitude() + " Longitude "+currentProduct.getLongitude());
                    GroceryLocation.this.setTitle(currentProduct.getProductDescription());
                    editLatitude.setText(currentProduct.getLatitude().toString());
                    editLongitude.setText(currentProduct.getLongitude().toString());

                }
            });
        } catch (Exception ex) {
            Log.d(TAG, "initProduct: " + currentProduct.toString());
            ex.printStackTrace();
        }
    }

    private void startLocationUpdates() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        gMap.setMyLocationEnabled(true);
    }

    private void stopLocationUpdates() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        gMap = googleMap;

        if (locationPermissionsGranted) {
            getLocation();
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setZoomControlsEnabled(true);
            gMap.getUiSettings().setCompassEnabled(true);

        }

    }

    public void readFromApi(){
        //Get sharePreference
        SharedPreferences sp = getApplicationContext().getSharedPreferences("ownerPref", Context.MODE_PRIVATE);

        String name = sp.getString("name", "");

        try{
            RestClient.getRequest(main.getAPI()+name,this, new VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Product> result) {
                    products = result;

                }
            }, MenuOptions.MASTER_LIST.toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}