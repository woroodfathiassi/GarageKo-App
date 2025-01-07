package com.example.garageko;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddCar extends AppCompatActivity {
    private EditText numberEdt, modelEdt, brandEdt;
    private DrawerLayout drawerLayout;
    private Button addBtn;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Bitmap selectedImageBitmap;
    private ImageButton imageBtn, backBtn;
    private ImageView carImage;
    private TextView menuItem1;
    private TextView menuItem2;
    private ImageView menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_car);

        numberEdt = findViewById(R.id.numberEdt);
        brandEdt = findViewById(R.id.brandEdt);
        modelEdt = findViewById(R.id.modelEdt);
        addBtn = findViewById(R.id.requestBtn);
        carImage = findViewById(R.id.carImg);
        imageBtn = findViewById(R.id.imageBtn);
        backBtn = findViewById(R.id.backBtn2);
        backBtn.setOnClickListener(e->finish());
        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        drawerLayout = findViewById(R.id.drawerLayout);
        menuIcon = findViewById(R.id.menuIcon);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });

        // Set click listeners
        menuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCar.this, ProfileActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        menuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MenuClick", "Mcenu Item 2 licked");
                System.out.println("Logout!"); // Prints to logcat
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        // Register Activity Result Launcher for image picking
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        setImageFromUri(imageUri);
                    }
                }
        );

        // Handle image chooser and save button clicks
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                setImageFromUri(imageUri);
            }
        });

        addBtn.setOnClickListener(e->{
            addCar(brandEdt.getText().toString(), modelEdt.getText().toString(), numberEdt.getText().toString(), selectedImageBitmap != null ? encodeImage(selectedImageBitmap) : "");
        });

        imageBtn.setOnClickListener(view -> openImageChooser());

    }

    private void addCar(String brand, String model, String number, String image){
        if (brand.isEmpty()) {
            brandEdt.setError("Enter the brand");
            brandEdt.requestFocus();
            return;
        }
        if (model.isEmpty()) {
            modelEdt.setError("Enter the model");
            modelEdt.requestFocus();
            return;
        }
        if (number.isEmpty()) {
            numberEdt.setError("Enter the car number");
            numberEdt.requestFocus();
            return;
        }

//        String url = "http://192.168.88.9/myPHP/AddCar";
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                response -> Toast.makeText(AddCar.this, response, Toast.LENGTH_SHORT).show(),
//                error -> {
//                    Toast.makeText(AddCar.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("brand", brand);
//                params.put("model", model);
//                params.put("number", number);
//                params.put("id", String.valueOf(AMainActivity.customerId));
//                if (!image.isEmpty()) {
//                    params.put("image", image);
//                }
//                return params;
//            }
//        };
//        queue.add(stringRequest);

        String url = "http://192.168.1.251/myPHP/AddCar";

        String str = url+"?brand="+brand+"&model="+model+"&number="+number+"&id="+AMainActivity.customerId
                +"&image="+image;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, str, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Toast.makeText(AddCar.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddCar.this, "The car number is added before", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AddCar.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddCar.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }
    private void setImageFromUri(Uri imageUri) {
        try {
            selectedImageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            carImage.setImageBitmap(selectedImageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save instance state like selected image or text fields
        if (selectedImageBitmap != null) {
            outState.putParcelable("selected_image", selectedImageBitmap);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore instance state like selected image
        if (savedInstanceState.containsKey("selected_image")) {
            selectedImageBitmap = savedInstanceState.getParcelable("selected_image");
            if (selectedImageBitmap != null) {
                carImage.setImageBitmap(selectedImageBitmap);
            }
        }
    }
}
