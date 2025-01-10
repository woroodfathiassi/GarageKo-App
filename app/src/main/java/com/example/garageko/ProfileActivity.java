//package com.example.garageko;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Base64;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class ProfileActivity extends AppCompatActivity {
//
//    private ActivityResultLauncher<Intent> activityResultLauncher;
//    private CircleImageView userImage;
//    private Button btnChooseImage;
//    private Button btnSave;
//    private Button btnLogout;
//
//    private EditText editTextName;
//    private EditText editTextEmail;
//    private EditText editTextPhoneNumber;
//    private EditText editTextAddress;
//    private EditText editTextPassword;
//
//    private TextView errorName;
//    private TextView errorEmail;
//    private TextView errorPhoneNumber;
//    private TextView errorAddress;
//    private TextView errorPassword;
//
//    private Bitmap selectedImageBitmap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_profile);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        //region UI Component Initialization
//        btnChooseImage = findViewById(R.id.btn_choose_image);
//        userImage = findViewById(R.id.userImage);
//        btnSave = findViewById(R.id.btnSave);
//        btnLogout = findViewById(R.id.btnLogout);
//
//        editTextName = findViewById(R.id.editTextName);
//        editTextEmail = findViewById(R.id.editTextEmail);
//        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
//        editTextAddress = findViewById(R.id.editTextAddress);
//        editTextPassword = findViewById(R.id.editTextPassword);
//
//        errorName = findViewById(R.id.errorName);
//        errorEmail = findViewById(R.id.errorEmail);
//        errorPhoneNumber = findViewById(R.id.errorPhoneNumber);
//        errorAddress = findViewById(R.id.errorAddress);
//        errorPassword = findViewById(R.id.errorPassword);
//        //endregion
//
//        getUserById(1);
//
//        activityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK) {
//                        Intent data = result.getData();
//                        if (data != null) {
//                            Uri imageUri = data.getData();
//                            try {
//                                selectedImageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                                userImage.setImageBitmap(selectedImageBitmap);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//
////                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
////                        Uri selectedImageUri = result.getData().getData();
////                        if (selectedImageUri != null) {
////                            userImage.setImageURI(selectedImageUri);
////                        }
////                    }
//                }
//        );
//
//        // Handle the change image button click
//        btnChooseImage.setOnClickListener(view -> handkeOpenImageChooser());
//
//        // Handle the change image button click
//        btnSave.setOnClickListener(view -> handkeSaveUpdateBtn());
//
//        // Handle the change image button click
//        btnLogout.setOnClickListener(view -> handleLogoutBtn());
//
//    }
//
//    private void handkeOpenImageChooser() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        activityResultLauncher.launch(intent);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && data != null) {
//            Uri selectedImageUri = data.getData();
//            if (selectedImageUri != null) {
//                // Try to get the actual bitmap from the URI
//                try {
//                    selectedImageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));
//                    userImage.setImageBitmap(selectedImageBitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//    private void handkeSaveUpdateBtn() {
//        boolean isValid = true;
//
//        //region Validate Name
//        String name = editTextName.getText().toString().trim();
//        if (name.isEmpty()) {
//            errorName.setText("* Name is required");
//            errorName.setVisibility(TextView.VISIBLE);
//            isValid = false;
//        } else {
//            errorName.setText("");
//            errorName.setVisibility(TextView.GONE);
//        }
//        //endregion
//
//        //region Validate Email
//        String email = editTextEmail.getText().toString().trim();
//        if (email.isEmpty()) {
//            errorEmail.setText("* Email is required");
//            errorEmail.setVisibility(TextView.VISIBLE);
//            isValid = false;
//        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            errorEmail.setText("* Invalid email format");
//            errorEmail.setVisibility(TextView.VISIBLE);
//            isValid = false;
//        } else {
//            errorEmail.setText("");
//            errorEmail.setVisibility(TextView.GONE);
//        }
//        //endregion
//
//        //region Validate Phone Number
//        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
//        if (phoneNumber.isEmpty()) {
//            errorPhoneNumber.setText("* Phone number is required");
//            errorPhoneNumber.setVisibility(TextView.VISIBLE);
//            isValid = false;
//        } else if (!phoneNumber.matches("05\\d{8}")) { // Ensures phone number starts with "05" and has 10 digits
//            errorPhoneNumber.setText("* Phone number must start with '05' and be 10 digits long");
//            errorPhoneNumber.setVisibility(TextView.VISIBLE);
//            isValid = false;
//        } else {
//            errorPhoneNumber.setText("");
//            errorPhoneNumber.setVisibility(TextView.GONE);
//        }
//        //endregion
//
//        //region Validate Address
//        String address = editTextAddress.getText().toString().trim();
//        if (address.isEmpty()) {
//            errorAddress.setText("* Address is required");
//            errorAddress.setVisibility(TextView.VISIBLE);
//            isValid = false;
//        } else {
//            errorAddress.setText("");
//            errorAddress.setVisibility(TextView.GONE);
//        }
//        //endregion
//
//        //region Validate Password
//        String password = editTextPassword.getText().toString().trim();
//        if (password.isEmpty()) {
//            errorPassword.setText("* Password is required");
//            errorPassword.setVisibility(TextView.VISIBLE);
//            isValid = false;
//        } else if (password.length() < 6) {
//            errorPassword.setText("* Password must be at least 6 characters");
//            errorPassword.setVisibility(TextView.VISIBLE);
//            isValid = false;
//        } else {
//            errorPassword.setText("");
//            errorPassword.setVisibility(TextView.GONE);
//        }
//        //endregion
//
//        // If all inputs are valid, log the data
//        if (isValid) {
//            android.util.Log.d("ProfileData", "Name: " + name);
//            android.util.Log.d("ProfileData", "Email: " + email);
//            android.util.Log.d("ProfileData", "Phone Number: " + phoneNumber);
//            android.util.Log.d("ProfileData", "Address: " + address);
//            android.util.Log.d("ProfileData", "Password: " + password);
//
//            saveUserInfo(name, email, phoneNumber, address, password, "");
//        }
//    }
//
//
//    private void handleLogoutBtn() {
//
//    }
//
//    private void saveUserInfo(String name, String email, String phone, String address, String password, String image){
//        String url = "http://10.0.2.2/api/updateUserInfo.php";
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
//                        Log.d("Response", response);}
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ProfileActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.e("VolleyError", error.toString());
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("name", name);
//                params.put("email", email);
//                params.put("address", address);
//                params.put("password", password);
//                params.put("Mobile_Phone", phone);
//                Log.d("worood", String.valueOf(selectedImageBitmap));
//                if (selectedImageBitmap != null) {
//                    String encodedImage = encodeImage(selectedImageBitmap);
//                    params.put("image", encodedImage);
//                }else{
//                    Log.d("the image is null","null image value");
//                }
//                return params;
//            }
//        };
//        queue.add(stringRequest);
//    }
//
//    private String encodeImage(Bitmap bm) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] b = baos.toByteArray();
//        return Base64.encodeToString(b, Base64.DEFAULT);
//    }
//
//    private void getUserById(int userId) {
//        String url = "http://10.0.2.2/api/getUserById.php";
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                response -> {
//                    try {
//                        JSONObject userJson = new JSONObject(response);
//
//                        // Handle "User not found" message from the server
//                        if (userJson.has("message") && userJson.getString("message").equals("User not found")) {
//                            Toast.makeText(ProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
//                            return; // Important: Exit the method to prevent further processing
//                        }
//
//                        String name = userJson.getString("name");
//                        String email = userJson.getString("email");
//                        String address = userJson.getString("address");
//                        String phone = userJson.getString("Mobile_Phone");
//                        String password = userJson.getString("password"); // Keep password retrieval for now (against best practice)
//                        String imageBase64 = userJson.optString("image", null);
//
//                        editTextName.setText(name);
//                        editTextEmail.setText(email);
//                        editTextPhoneNumber.setText(phone);
//                        editTextAddress.setText(address);
//                        editTextPassword.setText(password);
//
//                        if (imageBase64 != null && !imageBase64.equals("null")) {
//                            try {
//                                byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
//                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                                userImage.setImageBitmap(decodedByte);
//                            } catch (IllegalArgumentException e) {
//                                e.printStackTrace();
//                                Toast.makeText(this, "Error decoding image", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(ProfileActivity.this, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_LONG).show(); // More detailed error message
//                        Log.e("JSONError", "JSON Parsing Error", e); // Log the exception with stack trace
//                    }
//                },
//                error -> {
//                    Toast.makeText(ProfileActivity.this, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show(); // Longer Toast
//                    Log.e("VolleyError", "Volley Error", error); // Log the exception with stack trace
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("user_id", String.valueOf(userId));
//                return params;
//            }
//        };
//        queue.add(stringRequest);
//    }
//}



package com.example.garageko;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private CircleImageView userImage;
    private Button btnChooseImage, btnSave, btnLogout;
    private EditText editTextName, editTextEmail, editTextPhoneNumber, editTextAddress, editTextPassword;
    private TextView errorName, errorEmail, errorPhoneNumber, errorAddress, errorPassword;
    private Bitmap selectedImageBitmap;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // UI Component Initialization
        initializeUIComponents();

        getUserById(1);

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

        btnChooseImage.setOnClickListener(view -> openImageChooser());
        btnSave.setOnClickListener(view -> handleSaveUpdate());
        btnLogout.setOnClickListener(view -> handleLogout());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close this activity and return to the previous one
                finish();
            }
        });
    }

    private void initializeUIComponents() {
        btnChooseImage = findViewById(R.id.btn_choose_image);
        userImage = findViewById(R.id.userImage);
        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLogout);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPassword = findViewById(R.id.editTextPassword);

        errorName = findViewById(R.id.errorName);
        errorEmail = findViewById(R.id.errorEmail);
        errorPhoneNumber = findViewById(R.id.errorPhoneNumber);
        errorAddress = findViewById(R.id.errorAddress);
        errorPassword = findViewById(R.id.errorPassword);

        backButton = findViewById(R.id.imageButtonBack);
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }

    private void setImageFromUri(Uri imageUri) {
        try {
            selectedImageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            userImage.setImageBitmap(selectedImageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSaveUpdate() {
        if (validateInputs()) {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String phone = editTextPhoneNumber.getText().toString().trim();
            String address = editTextAddress.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            saveUserInfo(name, email, phone, address, password, selectedImageBitmap != null ? encodeImage(selectedImageBitmap) : "");
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validate Name
        isValid &= validateField(editTextName, errorName, "* Name is required");

        // Validate Email
        isValid &= validateEmail();

        // Validate Phone Number
        isValid &= validatePhoneNumber();

        // Validate Address
        isValid &= validateField(editTextAddress, errorAddress, "* Address is required");

        // Validate Password
        isValid &= validatePassword();

        return isValid;
    }

    private boolean validateField(EditText field, TextView errorField, String errorMessage) {
        String value = field.getText().toString().trim();
        if (value.isEmpty()) {
            errorField.setText(errorMessage);
            errorField.setVisibility(TextView.VISIBLE);
            return false;
        } else {
            errorField.setText("");
            errorField.setVisibility(TextView.GONE);
            return true;
        }
    }

    private boolean validateEmail() {
        String email = editTextEmail.getText().toString().trim();
        if (email.isEmpty()) {
            errorEmail.setText("* Email is required");
            errorEmail.setVisibility(TextView.VISIBLE);
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorEmail.setText("* Invalid email format");
            errorEmail.setVisibility(TextView.VISIBLE);
            return false;
        } else {
            errorEmail.setText("");
            errorEmail.setVisibility(TextView.GONE);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            errorPhoneNumber.setText("* Phone number is required");
            errorPhoneNumber.setVisibility(TextView.VISIBLE);
            return false;
        } else if (!phoneNumber.matches("05\\d{8}")) {
            errorPhoneNumber.setText("* Phone number must start with '05' and be 10 digits long");
            errorPhoneNumber.setVisibility(TextView.VISIBLE);
            return false;
        } else {
            errorPhoneNumber.setText("");
            errorPhoneNumber.setVisibility(TextView.GONE);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = editTextPassword.getText().toString().trim();
        if (password.isEmpty()) {
            errorPassword.setText("* Password is required");
            errorPassword.setVisibility(TextView.VISIBLE);
            return false;
        } else if (password.length() < 6) {
            errorPassword.setText("* Password must be at least 6 characters");
            errorPassword.setVisibility(TextView.VISIBLE);
            return false;
        } else {
            errorPassword.setText("");
            errorPassword.setVisibility(TextView.GONE);
            return true;
        }
    }

    private void saveUserInfo(String name, String email, String phone, String address, String password, String image) {
        String url = String.valueOf(R.string.UPDATE_USER_INFO);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show(),
                error -> {
                    Toast.makeText(ProfileActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("address", address);
                params.put("password", password);
                params.put("Mobile_Phone", phone);
                if (!image.isEmpty()) {
                    params.put("image", image);
                }
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void getUserById(int userId) {
        String url = getString(R.string.GET_USER_BY_ID);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject userJson = new JSONObject(response);

                        if (userJson.has("message") && userJson.getString("message").equals("User not found")) {
                            Toast.makeText(ProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        editTextName.setText(userJson.getString("name"));
                        editTextEmail.setText(userJson.getString("email"));
                        editTextPhoneNumber.setText(userJson.getString("Mobile_Phone"));
                        editTextAddress.setText(userJson.getString("address"));
                        editTextPassword.setText(userJson.getString("password"));

                        String imageBase64 = userJson.optString("image", null);
                        if (imageBase64 != null && !imageBase64.equals("null")) {
                            byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            userImage.setImageBitmap(decodedByte);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ProfileActivity.this, "Error parsing JSON", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(ProfileActivity.this, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void handleLogout() {
        // Implement logout logic here
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
                userImage.setImageBitmap(selectedImageBitmap);
            }
        }
    }
}
