package edu.fvtc.teams;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String TAG = "TeamMapActivity";
    final int PERMISSION_REQUEST_LOCATION = 101;
    GoogleMap gMap;
    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    ArrayList<Team> Teams = new ArrayList<>();
    Team currentTeam = null;
    int teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_map);

        Bundle extras = getIntent().getExtras();
        teamId = extras.getInt("teamid");
        this.setTitle("Team: " + teamId);

        if(teamId != -1)
        {
            initTeam(teamId);
        }
        else {
            // Making a new team.
            currentTeam = new Team();
        }

        Navbar.initListButton(this);
        Navbar.initMapButton(this);
        Navbar.initSettingsButton(this);
        initMapTypeButtons();
        initGetLocationButton();
        initSaveButton();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //createLocationRequest();
        createLocationCallback();

    }

    //    locationRequest = LocationRequest.create();
    //    locationRequest.setInterval(10000);
    //    locationRequest.setFastestInterval(5000);
    //    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


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
            };
        };
    }

    private void startLocationUpdates() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null );
        gMap.setMyLocationEnabled(true);
    }

    private void stopLocationUpdates() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void initGetLocationButton(){
        Button buttonGetLocation = findViewById(R.id.buttonGetLocation);

        buttonGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editAddress = findViewById(R.id.editAddress);
                EditText editCity = findViewById(R.id.editCity);
                EditText editState = findViewById(R.id.editState);
                EditText editZipcode = findViewById(R.id.editZipcode);

                String address = editAddress.getText().toString() + ", " +
                        editCity.getText().toString() + ", " +
                        editState.getText().toString() + ", " +
                        editZipcode.getText().toString();
                List<Address> addresses = null;

                Geocoder geo = new Geocoder(TeamMapActivity.this);
                try{
                    addresses = geo.getFromLocationName(address, 1);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                TextView txtLatitude = findViewById((R.id.textLatitude));
                TextView txtLongitude = findViewById((R.id.textLongitude));

                txtLatitude.setText(String.valueOf(addresses.get(0).getLatitude()));
                txtLongitude.setText(String.valueOf(addresses.get(0).getLongitude()));

                if(currentTeam != null)
                {
                    currentTeam.setLatitude(addresses.get(0).getLatitude());
                    currentTeam.setLongitude(addresses.get(0).getLongitude());
                }


            }
        });
    }

    private void initTeam(int teamid) {
        try{
            Log.d(TAG, "initTeam: " + teamid);

            RestClient.execGetOneRequest(TeamListActivity.TEAMSAPI + teamid,
                    this,
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(ArrayList<Team> result) {
                            currentTeam = result.get(0);
                            Log.d(TAG, "onSuccess: " + currentTeam.getName());
                            TeamMapActivity.this.setTitle(currentTeam.getName());
                        }
                    });

            Log.d(TAG, "initTeam: " + currentTeam.toString());
        }
        catch(Exception e)
        {
            Log.d(TAG, "initTeam: " + e.getMessage());
        }

    }

    private void initSaveButton() {
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamId == -1)
                {
                    Log.d(TAG, "Inserting: " +currentTeam.toString());
                    RestClient.execPostRequest(currentTeam,
                            TeamListActivity.TEAMSAPI,
                            TeamMapActivity.this,
                            new VolleyCallback() {
                                @Override
                                public void onSuccess(ArrayList<Team> result) {
                                    currentTeam.setId(result.get(0).getId());
                                    Log.d(TAG, "onSuccess: Post" + currentTeam.getId());
                                }
                            });
                }
                else {
                    Log.d(TAG, "Updating: " + currentTeam.toString());
                    RestClient.execPutRequest(currentTeam,
                            TeamListActivity.TEAMSAPI + teamId,
                            TeamMapActivity.this,
                            new VolleyCallback() {
                                @Override
                                public void onSuccess(ArrayList<Team> result) {
                                    Log.d(TAG, "onSuccess: Post" + currentTeam.getId());
                                }
                            });
                }
            }
        });

    }

    private void initMapTypeButtons() {
        RadioGroup rgMapType = findViewById(R.id.radioGroupMapType);
        rgMapType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbNormal = findViewById(R.id.radioButtonNormal);
                if (rbNormal.isChecked()) {
                    gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                else  {
                    gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        RadioButton rbNormal = findViewById(R.id.radioButtonNormal);
        rbNormal.setChecked(true);

        Point size = new Point();
        WindowManager w = getWindowManager();
        w.getDefaultDisplay().getSize(size);
        int measuredWidth = size.x;
        int measuredHeight = size.y;

        if (Teams.size()>0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (int i=0; i<Teams.size(); i++) {
                currentTeam = Teams.get(i);

                Geocoder geo = new Geocoder(this);
                List<Address> addresses = null;

                //String address = currentTeam.getStreetAddress() + ", " +
                //        currentTeam.getCity() + ", " +
                //        currentTeam.getState() + " " +
                //        currentTeam.getZipCode();

                //try {
                //addresses = geo.getFromLocationName(address, 1);
                //}

                LatLng point = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                builder.include(point);

                //gMap.addMarker(new MarkerOptions().position(point).title(currentTeam.getTeamName()).snippet(address));
            }
            gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), measuredWidth, measuredHeight, 450));
        }
        else {
            if (currentTeam != null) {
                Geocoder geo = new Geocoder(this);
                List<Address> addresses = null;

                /*String address = currentTeam.getStreetAddress() + ", " +
                        currentTeam.getCity() + ", " +
                        currentTeam.getState() + " " +
                        currentTeam.getZipCode();*/

                try {
                    // addresses = geo.getFromLocationName(address, 1);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                LatLng point = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());

                //gMap.addMarker(new MarkerOptions().position(point).title(currentTeam.getTeamName()).snippet(address));
                gMap.animateCamera(CameraUpdateFactory. newLatLngZoom(point, 16));
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(TeamMapActivity.this).create();
                alertDialog.setTitle("No Data");
                alertDialog.setMessage("No data is available for the mapping function.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    } });
                alertDialog.show();
            }
        }


        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(TeamMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(TeamMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                        Snackbar.make(findViewById(R.id.activity_team_map), "MyTeamList requires this permission to locate " + "your Teams", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        ActivityCompat.requestPermissions(TeamMapActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_LOCATION);
                                    }
                                })
                                .show();

                    } else {
                        ActivityCompat.requestPermissions(TeamMapActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_LOCATION);
                    }
                } else {
                    startLocationUpdates();
                }
            }else {
                startLocationUpdates();
            }
        }
        catch (Exception e) {
            Toast.makeText(getBaseContext(), "Error requesting permission", Toast.LENGTH_LONG).show();
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