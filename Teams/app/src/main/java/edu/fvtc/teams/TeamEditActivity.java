package edu.fvtc.teams;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.Manifest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class TeamEditActivity extends AppCompatActivity implements RaterDialog.SaveRatingListener, OnMapReadyCallback {
    Team team;
    public static final String TAG = TeamEditActivity.class.getName();
    boolean loading = true;
    public static final int PERMISSION_REQUEST_PHONE = 102;
    public static final int PERMISSION_REQUEST_CAMERA = 103;
    public static final int CAMERA_REQUEST = 1888;
    FusedLocationProviderClient fusedLocationClient;
    GoogleMap gMap1;

    int teamId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        teamId = extras.getInt("teamid");
        this.setTitle("Team: " + teamId);

        // init buttons
        Navbar.initListButton(this);
        Navbar.initMapButton(this);
        Navbar.initSettingsButton(this);

        initRatingButton();
        initSaveButton();
        initTextChanged(R.id.etName);
        initTextChanged(R.id.etCity);
        initTextChanged(R.id.editCell);
        initCallFunction();
        initImageButton();
        initMapTypeButtons();
        initGeoButton();


        //teams = new ArrayList<Team>();
        //teams = TeamListActivity.readFromTextFile(this);
        //Log.d(TAG, "onCreate: Teams: " + teams.size());

        if(teamId != -1)
        {
            // Editing an existing team
            //team = teams.get(teamid-1);

            initTeam(teamId);
        }
        else {
            // Making a new team.
            team = new Team();
        }
        RebindTeam();
        loading = false;
        setForEditing(false);
        initToggleButton();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        Log.d(TAG, "onCreate: End");
    }

    private void initGeoButton() {
        Button btnGeo = findViewById(R.id.btnGeo);
        btnGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamEditActivity.this, TeamMapActivity.class);
                intent.putExtra("teamid", team.getId());
                startActivity(intent);
            }
        });
    }

    private void initImageButton() {
        ImageButton imageTeam = findViewById(R.id.imageTeam);

        imageTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= 23)
                {
                    // Check for the manifest permission
                    if(ContextCompat.checkSelfPermission(TeamEditActivity.this, Manifest.permission.CAMERA) != PERMISSION_GRANTED){
                        if(ActivityCompat.shouldShowRequestPermissionRationale(TeamEditActivity.this, Manifest.permission.CAMERA)){
                            Snackbar.make(findViewById(R.id.activity_main), "Teams requires this permission to take a photo.",
                                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.d(TAG, "onClick: snackBar");
                                    ActivityCompat.requestPermissions(TeamEditActivity.this,
                                            new String[] {Manifest.permission.CAMERA},PERMISSION_REQUEST_PHONE);
                                }
                            }).show();
                        }
                        else {
                            Log.d(TAG, "onClick: ");
                            ActivityCompat.requestPermissions(TeamEditActivity.this,
                                    new String[] {Manifest.permission.CAMERA},PERMISSION_REQUEST_PHONE);
                            takePhoto();
                        }
                    }
                    else{
                        Log.d(TAG, "onClick: ");
                        takePhoto();
                    }
                }
                else {
                    // Only rely on the previous permissions
                    takePhoto();
                }
            }
        });

    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);;
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST)
        {
            if(resultCode == RESULT_OK)
            {
                Bitmap photo= (Bitmap)data.getExtras().get("data");
                Bitmap scaledPhoto = Bitmap.createScaledBitmap(photo, 144, 144, true);
                ImageButton imageButton = findViewById(R.id.imageTeam);
                imageButton.setImageBitmap(scaledPhoto);
                team.setPhoto(scaledPhoto);
            }
        }
    }

    private void initCallFunction() {
        EditText etPhone = findViewById(R.id.editCell);
        etPhone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                checkPhonePermission(team.getCellphone());
                return false;
            }
        });
    }

    private void initTeam(int teamid) {
        try{
            //TeamDataSource ds = new TeamDataSource(this);
            //ds.open();
            Log.d(TAG, "initTeam: " + teamid);
            //team = ds.get(teamid);
            //ds.close();

            RestClient.execGetOneRequest(TeamListActivity.TEAMSAPI + teamid,
                    this,
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(ArrayList<Team> result) {
                            team = result.get(0);
                            Log.d(TAG, "onSuccess: " + team.getName());
                            TeamEditActivity.this.setTitle(team.getName());
                            RebindTeam();
                        }
                    });

            Log.d(TAG, "initTeam: " + team.toString());
        }
        catch(Exception e)
        {
            Log.d(TAG, "initTeam: " + e.getMessage());
        }

    }
    private void setForEditing(boolean enabled)
    {
        EditText editName = findViewById(R.id.etName);
        EditText editCity = findViewById(R.id.etCity);
        EditText editCellPhone = findViewById(R.id.editCell);
        TextView txtRating = findViewById(R.id.txtRating);
        Button btnRating = findViewById(R.id.btnRating);

        editName.setEnabled(enabled);
        editCity.setEnabled(enabled);
        editCellPhone.setEnabled(enabled);
        btnRating.setEnabled(enabled);

        if(enabled)
            // Set focus to this control
            editName.requestFocus();
        else
        {
            // Scroll to the top of the scrollview
            ScrollView scrollView = findViewById(R.id.scrollView);
            scrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    }

    private void checkPhonePermission(String cellphone) {
        // Check the API version
        if(Build.VERSION.SDK_INT >= 23)
        {
            // Check for the manifest permission
            if(ContextCompat.checkSelfPermission(TeamEditActivity.this, Manifest.permission.CALL_PHONE) != PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(TeamEditActivity.this, Manifest.permission.CALL_PHONE)){
                    Snackbar.make(findViewById(R.id.activity_main), "Teams requires this permission to place a call form the app.",
                            Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d(TAG, "onClick: snackBar");

                            ActivityCompat.requestPermissions(TeamEditActivity.this,
                                    new String[] {Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_PHONE);
                        }
                    }).show();
                }
                else {
                    Log.d(TAG, "checkPhonePermission: 1");
                    ActivityCompat.requestPermissions(TeamEditActivity.this,
                            new String[] {Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_PHONE);
                    callTeam(cellphone);
                }
            }
            else{
                Log.d(TAG, "checkPhonePermission: 2");
                callTeam(cellphone);
            }
        }
        else {
            // Only rely on the previous permissions
            callTeam(cellphone);
        }
    }

    private void callTeam(String cellphone) {
        Log.d(TAG, "callTeam: " + cellphone);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + cellphone));
        startActivity(intent);

    }

    private void initToggleButton() {

        ToggleButton toggleButton = findViewById(R.id.toggleButtonEdit);
        toggleButton.setChecked(false);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setForEditing(toggleButton.isChecked());
            }
        });

    }

    private void initTextChanged(int controlId) {
        EditText editText = findViewById(controlId);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!loading) {
                    Log.d(TAG, "afterTextChanged: "+ editable.toString());
                    team.setControlText(controlId, editable.toString());
                }
            }
        });
    }

    private void RebindTeam()
    {
        EditText editName = findViewById(R.id.etName);
        EditText editCity = findViewById(R.id.etCity);
        EditText editCellPhone = findViewById(R.id.editCell);
        TextView txtRating = findViewById(R.id.txtRating);

        if(team != null) {
            editName.setText(team.getName());
            editCity.setText(team.getCity());
            editCellPhone.setText(team.getCellphone());
            txtRating.setText(String.valueOf(team.getRating()));

            ImageButton imageTeam = findViewById(R.id.imageTeam);
            if(team.getPhoto() != null)
                imageTeam.setImageBitmap(team.getPhoto());

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
            mapFragment.getMapAsync(this);
        }
    }

    private void initMapTypeButtons() {
        Log.d(TAG, "initMapTypeButtons: Start");
        RadioGroup rgMapType = findViewById(R.id.radioGroupMapType1);
        rgMapType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                Log.d(TAG, "onCheckedChanged: onCheckedChanged");
                RadioButton rbNormal = findViewById(R.id.radioButtonNormal1);
                if (rbNormal.isChecked()) {
                    gMap1.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                else  {
                    gMap1.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });
    }

    private void initSaveButton() {
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TeamDataSource ds = new TeamDataSource(TeamEditActivity.this);
                //ds.open();
                if(teamId == -1)
                {
                    Log.d(TAG, "Inserting: " +team.toString());
                    //team.setId(ds.getNewId());
                    //ds.insert(team);

                    RestClient.execPostRequest(team,
                            TeamListActivity.TEAMSAPI,
                            TeamEditActivity.this,
                            new VolleyCallback() {
                                @Override
                                public void onSuccess(ArrayList<Team> result) {
                                    team.setId(result.get(0).getId());
                                    Log.d(TAG, "onSuccess: Post" + team.getId());
                                }
                            });
                }
                else {
                    Log.d(TAG, "Updating: " + team.toString());
                    RestClient.execPutRequest(team,
                            TeamListActivity.TEAMSAPI + teamId,
                            TeamEditActivity.this,
                            new VolleyCallback() {
                                @Override
                                public void onSuccess(ArrayList<Team> result) {
                                    Log.d(TAG, "onSuccess: Post" + team.getId());
                                }
                            });
                    //ds.update(team);
                }
                //ds.close();
                //FileIO.writeFile(TeamListActivity.FILENAME,
                //           TeamEditActivity.this,
                //                 TeamListActivity.createTeamArray(teams));
            }
        });

    }

    private void initRatingButton() {
        Button btnRating = findViewById(R.id.btnRating);

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                RaterDialog raterDialog = new RaterDialog(team.getRating());
                raterDialog.show(fragmentManager, "Rate Team");
            }
        });

    }

    @Override
    public void didFinishTeamRaterDialog(float rating) {
        Log.d(TAG, "didFinishTeamRaterDialog: ");
        TextView txtRating = findViewById(R.id.txtRating);
        txtRating.setText(String.valueOf(rating));
        team.setRating(rating);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        try {

            Log.d(TAG, "onMapReady: ");
            gMap1 = googleMap;

            Point point = new Point();
            WindowManager windowManager = getWindowManager();
            windowManager.getDefaultDisplay().getSize(point);

            if (team != null) {
                LatLngBounds.Builder  builder = new LatLngBounds.Builder();
                LatLng marker = new LatLng(team.getLatitude(), team.getLongitude());
                builder.include(marker);

                gMap1.addMarker(new MarkerOptions()
                        .position(marker)
                        .title(team.getName())
                        .snippet(team.getMarkerText()));

                gMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 13f));

            } else {
                Log.d(TAG, "onMapReady: No team");
            }
        }
        catch(Exception e)
        {
            Log.d(TAG, "onMapReady: " + e.getMessage());
        }
    }
}