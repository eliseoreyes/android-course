package edu.fvtc.grocerylistdb;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.ToggleButton;
import android.Manifest;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import edu.fvtc.grocerylistdb.api.RestClient;
import edu.fvtc.grocerylistdb.api.VolleyCallback;
import edu.fvtc.grocerylistdb.models.Product;

public class ProductAddActivity extends AppCompatActivity {

    public static final String TAG = ProductAddActivity.class.getName();
    int productId = -1;
    Product product = new Product();

    EditText editProduct;
    CheckBox chkOnList, chkOnCart;

    public static final int PERMISSION_REQUEST_PHONE = 102;
    public static final int PERMISSION_REQUEST_CAMERA = 103;
    public static final int CAMERA_REQUEST = 1888;
    MainActivity mainActivity = new MainActivity();

    boolean loading = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);

        editProduct = findViewById(R.id.etProduct);
        chkOnList = findViewById(R.id.chkShoppingList);
        chkOnCart = findViewById(R.id.chkCart);


        initTextChanged();
        initSaveButton();
        initCheckBoxChanged();
        initImageButton();


        loading = false;
        setForEditing(false);
        initToggleButton();
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

    private void setForEditing(boolean enabled)
    {
        editProduct.setEnabled(enabled);
        chkOnCart.setEnabled(enabled);
        chkOnList.setEnabled(enabled);

        if(enabled)
            // Set focus to this control
            editProduct.requestFocus();
        else
        {
            // Scroll to the top of the scrollview
            ScrollView scrollView = findViewById(R.id.scrollView);
            scrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    }

    private void initCheckBoxChanged(){

        chkOnList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    product.setIsOnShoppingList(true);
                }else {
                    product.setIsOnShoppingList(false);
                }
            }
        });

        chkOnCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    product.setIsInCart(true);
                }else {
                    product.setIsInCart(false);
                }
            }
        });
    }

    private void initTextChanged(){
        EditText editText = findViewById(R.id.etProduct);

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
                    Log.d(TAG, "afterTextChanged: "+editable.toString());
                    product.setProductDescription(editable.toString());
                }
            }
        });
    }

    private void initSaveButton() {
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: "+product.toString());
                product.setOwner(MainActivity.name);

                RestClient.postRequest(product, mainActivity.getAPI(), ProductAddActivity.this, new VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Product> result) {
                        Log.d(TAG, "onSuccess: "+product.toString());

                        //Go to MainActivity
                        Intent intent = new Intent(ProductAddActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }

    private void initImageButton() {
        ImageButton imageProblem = findViewById(R.id.imageProduct);
        imageProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= 23)
                {
                    // Check for the manifest permission
                    if(ContextCompat.checkSelfPermission(ProductAddActivity.this, Manifest.permission.CAMERA) != PERMISSION_GRANTED){
                        if(ActivityCompat.shouldShowRequestPermissionRationale(ProductAddActivity.this, Manifest.permission.CAMERA)){
                            Snackbar.make(findViewById(R.id.activity_main), "Product requires this permission to take a photo.",
                                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.d(TAG, "onClick: snackBar");
                                    ActivityCompat.requestPermissions(ProductAddActivity.this,
                                            new String[] {Manifest.permission.CAMERA},PERMISSION_REQUEST_PHONE);
                                }
                            }).show();
                        }
                        else {
                            Log.d(TAG, "onClick: ");
                            ActivityCompat.requestPermissions(ProductAddActivity.this,
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
        startActivityForResult(intent, CAMERA_REQUEST);
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
                ImageButton imageButton = findViewById(R.id.imageProduct);
                imageButton.setImageBitmap(scaledPhoto);
                product.setPhoto(scaledPhoto);
            }
        }
    }
    private void rebindProduct(){

        if (product != null){
            Log.d(TAG, "rebindProduct: "+product.toString());
            editProduct.setText(product.getProductDescription());
            chkOnList.setChecked(product.getIsOnShoppingList());
            chkOnCart.setChecked(product.getIsInCart());

            ImageButton imageTeam = findViewById(R.id.imageProduct);
            if(product.getPhoto() != null)
                imageTeam.setImageBitmap(product.getPhoto());
        }
    }
}